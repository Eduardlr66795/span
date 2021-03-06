package interview.span.infrastructure.processor

import interview.span.application.objects.MatchResultDao
import interview.span.application.objects.TeamDetailDao
import interview.span.infrastructure.match.MatchImpl
import interview.span.infrastructure.persistence.Repository
import interview.span.utils.config.ApplicationConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import java.io.File

@Component
class Processor @Autowired constructor(
    private val applicationConfig: ApplicationConfig,
    private val repository: Repository,
    private val matchImpl: MatchImpl
) {

    /**
     * The logic in this method is used to process the file given to us by the user
     * @param fileName - The name of the file given to us by the user
     */
    fun processStandingResultsRequest(fileName: String): ResponseEntity<Any> {
        File(fileName).bufferedReader().readLines().map { lineItem ->
            matchImpl.processMatchResultEntry(
                createMatchResultEntry(lineItem)
            )
        }

        val standingResultsList = matchImpl.publishTeamStandingResults()

        processCleanup()

        return ResponseEntity.ok(standingResultsList)
    }

    private fun createMatchResultEntry(fileLineEntry: String): MatchResultDao {
        val hostAndOppositionDetailSplit = fileLineEntry.split(applicationConfig.getFileDelimiter())
        return getMatchResultDao(
            hostDetail = hostAndOppositionDetailSplit.first(),
            oppositionDetail = hostAndOppositionDetailSplit.last()
        )
    }

    private fun getMatchResultDao(hostDetail: String, oppositionDetail: String): MatchResultDao {
        val hostTeamDetail = getTeamDetail(hostDetail.trim().split("\\s+".toRegex()))
        val oppositionTeamDetail = getTeamDetail(oppositionDetail.trim().split("\\s+".toRegex()))

        return MatchResultDao(
            hostTeamName = hostTeamDetail.teamName,
            hostTeamScore = hostTeamDetail.score,
            oppositionTeamName = oppositionTeamDetail.teamName,
            oppositionTeamScore = oppositionTeamDetail.score
        )
    }

    private fun getTeamDetail(teamDetail: List<String>): TeamDetailDao {
        return if (teamDetail.size > 2) {
            TeamDetailDao(
                teamName = teamDetail.subList(0, teamDetail.size - 1).joinToString(),
                score = teamDetail.last().toInt()
            )
        } else {
            TeamDetailDao(
                teamName = teamDetail.first(),
                score = teamDetail.last().toInt()
            )
        }
    }

    private fun processCleanup() {
        repository.cleanup()
    }
}
