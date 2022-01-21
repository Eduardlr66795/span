package interview.span.application.service.team

import interview.span.domain.persistence.entities.TeamEntity
import interview.span.domain.persistence.entities.TeamStandingEntity

interface TeamService {

    /**
     * The logic in this method is used to create (not persist) a new TeamEntity
     */
    fun createTeamEntry(teamName: String): TeamEntity

    /**
     * The logic in this method is used to create (not persist) a new TeamStandingEntity
     */
    fun createTeamStandingEntry(teamEntity: TeamEntity): TeamStandingEntity
}
