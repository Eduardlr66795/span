package interview.span.application

import interview.span.application.objects.MatchOutcomeDao
import interview.span.application.objects.MatchPointDao
import interview.span.application.service.match.MatchServiceImpl
import interview.span.domain.persistence.entities.TeamEntity
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class MatchServiceImplTest {

    @Autowired
    lateinit var matchService: MatchServiceImpl

    @Test
    fun testCreateMatchEntityMapping() {
        val mockMatchOutcomeDao = mockMatchOutcomeDao()
        val matchEntityResult = matchService.createMatchEntry(mockMatchOutcomeDao)

        // ASSERT: hostTeam properties match
        Assertions.assertEquals(matchEntityResult.hostName, mockMatchOutcomeDao.hostTeam.getName())
        Assertions.assertEquals(matchEntityResult.hostScore, 3)

        // ASSERT: oppositionTeam properties match
        Assertions.assertEquals(matchEntityResult.oppositionName, mockMatchOutcomeDao.oppositionTeam.getName())
        Assertions.assertEquals(matchEntityResult.oppositionScore, 1)
    }

    @Test
    fun testCreatePointAllocationEntityMapping() {
        val mockTeam = mockTeamEntity("mockTeamName")
        val mockMatchPointDao = MatchPointDao("WIN", 3)
        val matchEntityResult = matchService.createPointAllocationEntity(22L, mockTeam, mockMatchPointDao)

        // ASSERT: MatchId match
        Assertions.assertEquals(22L, matchEntityResult.getMatchId())

        // ASSERT: MatchId match
        Assertions.assertEquals(mockTeam.getId(), matchEntityResult.getTeamId())

        // ASSERT: TeamName match
        Assertions.assertEquals(mockTeam.getName(), matchEntityResult.getTeamName())

        // ASSERT: Outcome match
        Assertions.assertEquals("WIN", matchEntityResult.getOutcome())

        // ASSERT: Points match
        Assertions.assertEquals(3, matchEntityResult.getPoints())
    }

    private fun mockMatchOutcomeDao(): MatchOutcomeDao {
        return MatchOutcomeDao(
            hostTeam = mockTeamEntity("hostTeam"),
            hostScore = 3,
            oppositionTeam = mockTeamEntity("oppositionTeam"),
            oppositionScore = 1
        )
    }

    private fun mockTeamEntity(teamName: String): TeamEntity {
        return TeamEntity(
            name = teamName
        )
    }
}
