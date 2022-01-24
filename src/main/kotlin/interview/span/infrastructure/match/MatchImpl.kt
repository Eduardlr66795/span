package interview.span.infrastructure.match

import interview.span.application.objects.MatchOutcomeDao
import interview.span.application.objects.MatchResultDao
import interview.span.application.objects.TeamStandingsDao
import interview.span.application.service.match.MatchService
import interview.span.application.service.team.TeamService
import interview.span.domain.persistence.entities.LeagueMatchEntity
import interview.span.domain.persistence.entities.LeagueMatchOutcomeEntity
import interview.span.domain.persistence.entities.TeamEntity
import interview.span.domain.persistence.entities.TeamStandingEntity
import interview.span.infrastructure.match.mapper.MatchMapper
import interview.span.infrastructure.persistence.Repository
import interview.span.infrastructure.standing.events.UpdateTeamStandingEvent
import interview.span.utils.logging.objects.LogEntry
import interview.span.utils.logging.Logger
import interview.span.utils.logging.tags.LogTags
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class MatchImpl @Autowired constructor(
    private var eventPublisher: ApplicationEventPublisher,
    private val matchService: MatchService,
    private val teamService: TeamService,
    private val repository: Repository
) : MatchMapper() {

    fun processMatchResultEntry(matchResultEntry: MatchResultDao) {
        processMatchResultEntry(
            host = findOrCreateTeam(matchResultEntry.hostTeamName),
            opposition = findOrCreateTeam(matchResultEntry.oppositionTeamName),
            matchResultEntry = matchResultEntry
        )
    }

    fun publishTeamStandingResults(): List<Any> {
        return publishTeamStandingResults(
            repository.findTopScoresDescending()
        )
    }

    // --------------------
    // PRIVATE METHODS
    // --------------------

    /**
     * The logic in this method is used to publish the results once all of the match entries have been populated.
     * The implementation was created specifically so that the standings / results could be published in two ways.
     *
     * 1. Once all of the match entries have been processed (faster since we only have to iterate over the list once)
     * 2. After each of match result entry has been processed.
     *
     */
    private fun publishTeamStandingResults(teamPointsDescending: List<Int>): List<String> {
        // Find the top scores in descendingOrder
        var currPosition = 1
        val teamStandingResults = mutableListOf<String>()

        for (points in teamPointsDescending) {
            val topTeamStandingEntityList = repository.findByPoints(points)

            if (topTeamStandingEntityList.size == 1) {
                val position = currPosition++
                teamStandingResults.add(
                    updateAndDisplayTeamStandingResults(position, topTeamStandingEntityList.first())
                )
            } else {
                for (topTeamStandingEntity in topTeamStandingEntityList) {
                    teamStandingResults.add(
                        updateAndDisplayTeamStandingResults(currPosition, topTeamStandingEntity)
                    )
                }
                currPosition += topTeamStandingEntityList.size
            }
        }

        return teamStandingResults

        // // val teamStandingResults = listOf<String>()
        // return teamPointsDescending.map { points ->
        //     val topTeamStandingEntityList = repository.findByPoints(points)
        //     if (topTeamStandingEntityList.size == 1) {
        //         val position = currPosition++
        //         teamStandingResults.plus(
        //             updateAndDisplayTeamStandingResults(position, topTeamStandingEntityList.first())
        //         )
        //     } else {
        //         for (topTeamStandingEntity in topTeamStandingEntityList) {
        //             teamStandingResults.plus(
        //                 updateAndDisplayTeamStandingResults(currPosition, topTeamStandingEntity)
        //             )
        //         }
        //         currPosition += topTeamStandingEntityList.size
        //     }
        // }
    }

    /**
     * The logic in this method is used to create and persist two (2) new MatchPointAllocationEntries in the database.
     * In doing so the database will keep track of the outcome of the game using the matchEntity entry in the database.
     *
     * @param matchOutcome - Mapped object that contains the details that we used to create the matchEntity
     * @param leagueMatch - The matchEntity that was created using the matchOutcomeDao
     */
    private fun processMatchPointAllocationEntries(matchOutcome: MatchOutcomeDao, leagueMatch: LeagueMatchEntity) {
        val matchPointEntries = matchService.createMatchPointAllocationEntry(matchOutcome, leagueMatch.getId())
        for (entry in matchPointEntries) {
            publishTeamStandingsEvent(
                repository.persist(entry)
            )
        }
    }

    /**
     * The logic in this method is used to create & persis a new MatchEntity into the database
     * @param matchOutcome - Mapped internal object that contains data required to create a new MatchEntity
     * @return LeagueMatchEntity - Entity that was persisted into the database
     */
    private fun createMatchEntry(matchOutcome: MatchOutcomeDao): LeagueMatchEntity {
        return repository.persist(
            matchService.createMatchEntry(
                matchOutcome
            )
        )
    }

    /**
     * This method is used to create or extract a TeamEntity from the database.
     */
    private fun findOrCreateTeam(teamName: String): TeamEntity {
        val teamEntityOptional = repository.findTeamEntity(teamName)
        return if (teamEntityOptional.isPresent) {
            teamEntityOptional.get()
        } else {
            createTeamAndTeamStandingEntries(teamName)
        }
    }

    /**
     * This method is used to create & persist a new TeamEntity into the database.
     */
    private fun createTeamAndTeamStandingEntries(teamName: String): TeamEntity {
        // Create the TeamEntity
        val teamEntity = repository.persist(
            teamService.createTeamEntry(teamName)
        )

        // Create the TeamStandingEntity
        repository.persist(
            teamService.createTeamStandingEntry(teamEntity)
        )

        return teamEntity
    }

    /**
     * This method is used to display (print) the team standing result
     */
    private fun updateAndDisplayTeamStandingResults(
        position: Int,
        teamStandingEntity: TeamStandingEntity
    ): String {
        teamStandingEntity.setPosition(position)
        repository.persist(teamStandingEntity)

        val result = ("$position. ${teamStandingEntity.getTeamName()}, ${teamStandingEntity.getPoints()} pts")

        Logger.info(
            LogEntry(
                LogTags.PUBLISH_TEAM_STANDING_RESULT,
                mapOf(
                    "position" to position,
                    "teamName" to teamStandingEntity.getTeamName(),
                    "points" to "${teamStandingEntity.getPoints()} pts"
                )
            )
        )

        return result
    }

    private fun processMatchResultEntry(host: TeamEntity, opposition: TeamEntity, matchResultEntry: MatchResultDao) {
        val matchOutcomeDao = mapMatchOutcomeDao(host, opposition, matchResultEntry)

        processMatchPointAllocationEntries(
            matchOutcome = matchOutcomeDao,
            leagueMatch = createMatchEntry(matchOutcomeDao)
        )
    }

    // --------------------
    // PROTECTED METHODS
    // --------------------

    protected fun publishTeamStandingsEvent(leagueMatchOutcomeEntity: LeagueMatchOutcomeEntity) {
        eventPublisher.publishEvent(
            UpdateTeamStandingEvent(
                TeamStandingsDao(
                    teamId = leagueMatchOutcomeEntity.getTeamId(),
                    teamName = leagueMatchOutcomeEntity.getTeamName(),
                    points = leagueMatchOutcomeEntity.getPoints()
                )
            )
        )
    }
}
