package interview.span.application.service.team

import interview.span.domain.persistence.entities.TeamEntity
import interview.span.domain.persistence.entities.TeamStandingEntity
import interview.span.utils.config.ApplicationConstants
import interview.span.utils.logging.objects.LogEntry
import interview.span.utils.logging.Logger
import interview.span.utils.logging.tags.LogTags
import org.springframework.stereotype.Component

@Component
class TeamServiceImpl : TeamService {

    override fun createTeamEntry(teamName: String): TeamEntity {
        Logger.info(LogEntry(LogTags.CREATE_TEAM_ENTITY))

        return  TeamEntity(
            name = teamName
        )
    }

    override fun createTeamStandingEntry(teamEntity: TeamEntity): TeamStandingEntity {
        Logger.info(LogEntry(LogTags.CREATE_TEAM_STANDING_ENTITY))

        return TeamStandingEntity(
            teamId = teamEntity.getId(),
            teamName = teamEntity.getName(),
            points = ApplicationConstants.DEFAULT_POINTS,
            position = ApplicationConstants.DEFAULT_POSITION
        )
    }
}
