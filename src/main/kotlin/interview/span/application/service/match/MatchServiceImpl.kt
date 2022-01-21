package interview.span.application.service.match

import interview.span.application.objects.MatchOutcomeDao
import interview.span.application.objects.MatchPointDao
import interview.span.application.enums.MatchOutcomeEnum
import interview.span.domain.persistence.entities.TeamEntity
import interview.span.infrastructure.persistence.Repository
import interview.span.utils.config.ApplicationConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import interview.span.domain.persistence.entities.LeagueMatchEntity as Match
import interview.span.domain.persistence.entities.LeagueMatchOutcomeEntity as MatchPointAllocation

@Component
class MatchServiceImpl @Autowired constructor(
    private val repository: Repository
): MatchService {

    override fun createMatchEntry(matchOutcomeDao: MatchOutcomeDao): Match {
        return Match(
            hostId = matchOutcomeDao.hostTeam.getId(),
            hostName = matchOutcomeDao.hostTeam.getName(),
            hostScore = matchOutcomeDao.hostScore,
            oppositionId = matchOutcomeDao.oppositionTeam.getId(),
            oppositionName = matchOutcomeDao.oppositionTeam.getName(),
            oppositionScore = matchOutcomeDao.oppositionScore
        )
    }

    override fun createPointAllocationEntity(matchId: Long, team: TeamEntity, matchPoint: MatchPointDao): MatchPointAllocation {
        return MatchPointAllocation(
            matchId = matchId,
            teamId = team.getId(),
            teamName = team.getName(),
            outcome = matchPoint.outcome,
            points = matchPoint.points
        )
    }

    override fun createMatchPointAllocationEntry(matchOutcomeDao: MatchOutcomeDao, matchId: Long): Array<MatchPointAllocation> {
        val hostTeam = matchOutcomeDao.hostTeam
        val hostScore = matchOutcomeDao.hostScore

        val oppositionTeam = matchOutcomeDao.oppositionTeam
        val oppositionScore = matchOutcomeDao.oppositionScore

        // Case 01: Winner: HostTeam - Looser: OppositionTeam
        if (hostScore > oppositionScore) {
            return arrayOf(
                createPointAllocationEntity(
                    matchId = matchId, team = hostTeam, matchPoint = createPointDao(MatchOutcomeEnum.WIN)
                ),
                createPointAllocationEntity(
                    matchId = matchId, team = oppositionTeam, matchPoint = createPointDao(MatchOutcomeEnum.LOSS)
                )
            )
        }

        // Case 02: Winner: OppositionTeam - Looser: HostTeam
        if (oppositionScore > hostScore) {
            return arrayOf(
                createPointAllocationEntity(
                    matchId = matchId, team = hostTeam, matchPoint = createPointDao(MatchOutcomeEnum.LOSS)
                ),
                createPointAllocationEntity(
                    matchId = matchId, team = oppositionTeam, matchPoint = createPointDao(MatchOutcomeEnum.WIN)
                )
            )
        }

        // Case 03: Draw
        return arrayOf(
            createPointAllocationEntity(
                matchId = matchId, team = hostTeam, matchPoint = createPointDao(MatchOutcomeEnum.DRAW)
            ),
            createPointAllocationEntity(
                matchId = matchId, team = oppositionTeam, matchPoint = createPointDao(MatchOutcomeEnum.DRAW)
            )
        )
    }

    protected fun createPointDao(matchOutcomeEnum: MatchOutcomeEnum): MatchPointDao {
        return MatchPointDao(matchOutcomeEnum.name, repository.findPointConfigEntity(matchOutcomeEnum).getPoints())
    }
}
