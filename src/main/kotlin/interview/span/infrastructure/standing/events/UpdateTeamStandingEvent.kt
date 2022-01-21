package interview.span.infrastructure.standing.events

import interview.span.application.objects.TeamStandingsDao
import org.springframework.context.ApplicationEvent

class UpdateTeamStandingEvent(teamStandingsDao: TeamStandingsDao) : ApplicationEvent(teamStandingsDao) {

    private var teamStandingsDao: TeamStandingsDao

    init {
        this.teamStandingsDao = teamStandingsDao
    }

    fun getUpdateTeamStandingsDao(): TeamStandingsDao {
        return teamStandingsDao
    }
}
