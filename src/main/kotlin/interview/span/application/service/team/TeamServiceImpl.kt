package interview.span.application.service.team

import interview.span.domain.persistence.entities.TeamEntity
import interview.span.domain.persistence.entities.TeamStandingEntity
import interview.span.utils.config.ApplicationConstants
import org.springframework.stereotype.Component

@Component
class TeamServiceImpl: TeamService {

    override fun createTeamEntry(teamName: String): TeamEntity {
        return TeamEntity(
            name = teamName
        )
    }

    override fun createTeamStandingEntry(teamEntity: TeamEntity): TeamStandingEntity {
        return TeamStandingEntity(
            teamId = teamEntity.getId(),
            teamName = teamEntity.getName(),
            points = ApplicationConstants.DEFAULT_POINTS,
            position = ApplicationConstants.DEFAULT_POSITION
        )
    }
}
