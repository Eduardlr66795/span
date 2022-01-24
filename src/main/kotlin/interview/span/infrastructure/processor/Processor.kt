package interview.span.infrastructure.processor

import interview.span.application.objects.MatchResultDao
import interview.span.application.objects.TeamDetailDao
import interview.span.infrastructure.match.MatchImpl
import interview.span.infrastructure.persistence.Repository
import interview.span.utils.config.ApplicationConfig
import interview.span.utils.logging.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.io.File

@Component
class Processor @Autowired constructor(
    private val applicationConfig: ApplicationConfig,
    private val repository: Repository,
    private val matchImpl: MatchImpl
) {

    fun processStandingResultsRequest() {
        // processStandingResultsRequest(
        //     "/Users/eduard/Desktop/test_file.txt"
        // )

        processRequest()

        Logger.info()

        processCleanup()
    }

    fun processRequest() {
        val testList = listOf(
            MatchResultDao("Lions", 3, "Snakes", 3),
            MatchResultDao("Tarantulas", 1, "FC Awesome", 0),
            MatchResultDao("Lions", 1, "FC Awesome", 1),
            MatchResultDao("Tarantulas", 3, "Snakes", 1),
            MatchResultDao("Lions", 4, "Grouches", 0)
        )

        Logger.info()

        for (lineItem in testList) {
            matchImpl.processMatchResultEntry(lineItem)
        }
    }

    /**
     * The logic in this method is used to process the file given to us by the user
     * @param fileName - The name of the file given to us by the user
     */
    private fun processStandingResultsRequest(fileName: String) {
        File(fileName).bufferedReader().readLines().map { lineItem ->
            matchImpl.processMatchResultEntry(
                createMatchResultEntry(lineItem)
            )
        }

        matchImpl.publishTeamStandingResults()
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
