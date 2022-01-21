package interview.span.infrastructure.standing

import interview.span.application.objects.TeamStandingsDao
import interview.span.infrastructure.persistence.Repository
import interview.span.infrastructure.standing.events.UpdateTeamStandingEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Component
class TeamStandingEventListener(
    private val repository: Repository
) : ApplicationListener<UpdateTeamStandingEvent> {

    override fun onApplicationEvent(teamStandingsEvent: UpdateTeamStandingEvent) {
        updateTeamStandingPoints(
            teamStandingsEvent.getUpdateTeamStandingsDao()
        )
    }

    private fun updateTeamStandingPoints(teamStandingsDao: TeamStandingsDao) {
        val teamStandingEntity = repository.findTeamStandingEntity(teamStandingsDao.teamId)
        teamStandingEntity.setPoints(teamStandingsDao.points)

        repository.persist(teamStandingEntity)
    }
}
