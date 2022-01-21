package interview.span.application.service.match

import interview.span.application.objects.MatchOutcomeDao
import interview.span.application.objects.MatchPointDao
import interview.span.domain.persistence.entities.LeagueMatchEntity
import interview.span.domain.persistence.entities.LeagueMatchOutcomeEntity
import interview.span.domain.persistence.entities.TeamEntity

interface MatchService {

    /**
     * This method is used to create (not persist) a new LeagueMatchEntity
     */
    fun createMatchEntry(matchOutcomeDao: MatchOutcomeDao): LeagueMatchEntity

    /**
     * This method is used to create (not persist) a new LeagueMatchOutcomeEntity
     */
    fun createPointAllocationEntity(
        matchId: Long,
        team: TeamEntity,
        matchPoint: MatchPointDao
    ): LeagueMatchOutcomeEntity

    /**
     * This method is used to create an array of two MatchPointAllocationEntries. One for the host team and one for the opposition team
     */
    fun createMatchPointAllocationEntry(matchOutcomeDao: MatchOutcomeDao, matchId: Long): Array<LeagueMatchOutcomeEntity>
}
