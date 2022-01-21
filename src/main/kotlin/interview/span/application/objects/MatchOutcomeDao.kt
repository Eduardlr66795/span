package interview.span.application.objects

import interview.span.domain.persistence.entities.TeamEntity

data class MatchOutcomeDao(
    val hostTeam: TeamEntity,
    val hostScore: Int,
    val oppositionTeam: TeamEntity,
    val oppositionScore: Int,
)
