package interview.span.infrastructure.match.mapper

import interview.span.application.objects.MatchOutcomeDao
import interview.span.application.objects.MatchResultDao
import interview.span.domain.persistence.entities.TeamEntity

open class MatchMapper {

    fun mapMatchOutcomeDao(
        hostTeamEntity: TeamEntity,
        oppositionTeamEntity: TeamEntity,
        matchResultEntry: MatchResultDao
    ): MatchOutcomeDao {
        return MatchOutcomeDao(
            hostTeam = hostTeamEntity,
            hostScore = matchResultEntry.hostTeamScore,
            oppositionTeam = oppositionTeamEntity,
            oppositionScore = matchResultEntry.oppositionTeamScore
        )
    }
}
