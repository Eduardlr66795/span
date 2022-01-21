package interview.span.infrastructure.persistence

import interview.span.application.enums.MatchOutcomeEnum
import interview.span.domain.persistence.entities.LeagueMatchEntity
import interview.span.domain.persistence.entities.LeagueMatchOutcomeEntity
import interview.span.domain.persistence.entities.PointConfigurationEntity
import interview.span.domain.persistence.entities.TeamEntity
import interview.span.domain.persistence.entities.TeamStandingEntity
import interview.span.infrastructure.persistence.repositories.LeagueMatchOutcomeRepository
import interview.span.infrastructure.persistence.repositories.LeagueMatchRepository
import interview.span.infrastructure.persistence.repositories.PointConfigurationRepository
import interview.span.infrastructure.persistence.repositories.TeamRepository
import interview.span.infrastructure.persistence.repositories.TeamStandingRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.Optional

@Component
class Repository @Autowired constructor(
    private val pointConfigurationRepository: PointConfigurationRepository,
    private val leagueMatchOutcomeRepository: LeagueMatchOutcomeRepository,
    private val teamStandingRepository: TeamStandingRepository,
    private val leagueMatchRepository: LeagueMatchRepository,
    private val teamRepository: TeamRepository
) {

    // -----------------------------------------------------------------
    // PERSIST: The following methods are for persisting data
    // -----------------------------------------------------------------

    fun persist(teamEntity: TeamEntity): TeamEntity {
        return teamRepository.save(teamEntity)
    }

    fun persist(leagueMatchOutcomeEntity: LeagueMatchOutcomeEntity): LeagueMatchOutcomeEntity {
        return leagueMatchOutcomeRepository.save(leagueMatchOutcomeEntity)
    }

    fun persist(leagueMatchEntity: LeagueMatchEntity): LeagueMatchEntity {
        return leagueMatchRepository.save(leagueMatchEntity)
    }

    fun persist(teamStandingEntity: TeamStandingEntity): TeamStandingEntity {
        return teamStandingRepository.save(teamStandingEntity)
    }

    // -----------------------------------------------------------------
    // EXTRACT: The following methods are for extracting data
    // -----------------------------------------------------------------

    fun findTeamEntity(teamName: String): Optional<TeamEntity> {
        return teamRepository.findByName(teamName)
    }

    fun findTeamStandingEntity(teamId: Long): TeamStandingEntity {
        val optionalTeamStandingEntity = teamStandingRepository.findByTeamId(teamId)

        if (optionalTeamStandingEntity.isPresent) {
            return optionalTeamStandingEntity.get()
        } else {
            throw Exception("findTeamStandingEntity")
        }
    }

    fun findTopScoresDescending(): List<Int> {
        return teamStandingRepository.findTeamStandingEntriesOrderByPointsDes()
    }

    fun findByPoints(points: Int): List<TeamStandingEntity> {
        return teamStandingRepository.findByPoints(points)
    }

    fun findPointConfigEntity(matchOutcomeEnum: MatchOutcomeEnum): PointConfigurationEntity {
        return pointConfigurationRepository.findByOutcome(matchOutcomeEnum.name).get()
    }

    fun cleanup() {
        leagueMatchOutcomeRepository.deleteAll()
        teamStandingRepository.deleteAll()
        leagueMatchRepository.deleteAll()
        teamRepository.deleteAll()
    }
}
