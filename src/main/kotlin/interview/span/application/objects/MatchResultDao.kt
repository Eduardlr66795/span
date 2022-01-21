package interview.span.application.objects

data class MatchResultDao(
    val hostTeamName: String,
    val hostTeamScore: Int,
    val oppositionTeamName: String,
    val oppositionTeamScore: Int
)
