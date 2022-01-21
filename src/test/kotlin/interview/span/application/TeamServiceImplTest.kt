package interview.span.application

import interview.span.application.service.team.TeamService
import interview.span.domain.persistence.entities.TeamEntity
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TeamServiceImplTest {

    @Autowired
    lateinit var teamService: TeamService

    @Test
    fun createTeamEntryTest() {
        val teamEntity = teamService.createTeamEntry("testTeamNameEntry")

        // ASSERT: Team name matches
        Assertions.assertEquals("testTeamNameEntry", teamEntity.getName())
    }

    @Test
    fun createTeamStandingEntryTest() {
        val teamStandingEntity = teamService.createTeamStandingEntry(mockTeamEntity("testTeamNameEntry"))

        // ASSERT: Team name matches
        Assertions.assertEquals("testTeamNameEntry", teamStandingEntity.getTeamName())

        // ASSERT: Default of "0" points assigned to new team standing entity
        Assertions.assertEquals(0, teamStandingEntity.getPoints())

        // ASSERT: Default of "0" position assigned to new team standing entity
        Assertions.assertEquals(0, teamStandingEntity.getPosition())
    }

    private fun mockTeamEntity(teamName: String): TeamEntity {
        return TeamEntity(
            name = teamName
        )
    }
}
