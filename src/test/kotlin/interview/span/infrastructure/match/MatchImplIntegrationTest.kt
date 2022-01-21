package interview.span.infrastructure.match

import interview.span.application.objects.MatchResultDao
import interview.span.application.service.match.MatchService
import interview.span.application.service.match.MatchServiceImpl
import interview.span.application.service.team.TeamService
import interview.span.domain.persistence.entities.PointConfigurationEntity
import interview.span.infrastructure.persistence.Repository
import interview.span.infrastructure.persistence.repositories.PointConfigurationRepository
import org.aspectj.lang.annotation.Before
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.ApplicationEventPublisher
import org.springframework.test.context.event.annotation.BeforeTestClass
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class MatchImplIntegrationTest {

    @Autowired
    lateinit var matchImpl: MatchImpl

    @Autowired
    lateinit var pointConfigurationRepository: PointConfigurationRepository

    @Autowired
    lateinit var repository: Repository

    @BeforeEach
    fun test() {
        pointConfigurationRepository.save(
            PointConfigurationEntity("LOSS", 0)
        )

        pointConfigurationRepository.save(
            PointConfigurationEntity("WIN", 3)
        )

        pointConfigurationRepository.save(
            PointConfigurationEntity("DRAW", 1)
        )
    }

    @Test
    fun testResultsForOneMatchResultEntry() {
        // pointConfigurationRepository.save(
        //     PointConfigurationEntity("LOSS", 0)
        // )
        //
        // pointConfigurationRepository.save(
        //     PointConfigurationEntity("WIN", 3)
        // )
        //
        // pointConfigurationRepository.save(
        //     PointConfigurationEntity("DRAW", 1)
        // )

        // ASSERT: Lions & Snakes team entries do not exist in the database
        Assertions.assertTrue(repository.findTeamEntity("Lions").isEmpty)
        Assertions.assertTrue(repository.findTeamEntity("Snakes").isEmpty)

        val testMatchResultDao = MatchResultDao("Lions", 3, "Snakes", 1)
        matchImpl.processMatchResultEntry(testMatchResultDao)
        matchImpl.publishTeamStandingResults()

        val lionsTeamEntityOptional = repository.findTeamEntity("Lions")
        val snakesTeamEntityOptional = repository.findTeamEntity("Snakes")

        // ASSERT: Lions & Snakes team entries exist in the database
        Assertions.assertTrue(lionsTeamEntityOptional.isPresent)
        Assertions.assertTrue(snakesTeamEntityOptional.isPresent)

        val lionsTeamStandingEntity = repository.findTeamStandingEntity(lionsTeamEntityOptional.get().getId())
        val snakesTeamStandingEntity = repository.findTeamStandingEntity(snakesTeamEntityOptional.get().getId())

        // ASSERT: Team standing entities for lions and snakes exist
        Assertions.assertNotNull(lionsTeamStandingEntity)
        Assertions.assertNotNull(snakesTeamStandingEntity)

        // ASSERT: Lions team standing entity properties are correct after match entry processed
        Assertions.assertEquals(3, lionsTeamStandingEntity.getPoints())
        Assertions.assertEquals(1, lionsTeamStandingEntity.getPosition())

        // ASSERT: Lions team standing entity properties are correct after match entry processed
        Assertions.assertEquals(0, snakesTeamStandingEntity.getPoints())
        Assertions.assertEquals(2, snakesTeamStandingEntity.getPosition())
    }

    @Test
    fun testResultsForTwoMatchResultEntry() {
        // ASSERT: Lions & Snakes team entries do not exist in the database
        Assertions.assertTrue(repository.findTeamEntity("Lions").isEmpty)
        Assertions.assertTrue(repository.findTeamEntity("Snakes").isEmpty)

        val matchResultDaoList = listOf(
            MatchResultDao("Lions", 3, "Snakes", 1),
                MatchResultDao("Lions", 1, "Sharks", 1)
        )

        for(entry in matchResultDaoList) {
            matchImpl.processMatchResultEntry(entry)
        }

        matchImpl.publishTeamStandingResults()

        val lionsTeamEntityOptional = repository.findTeamEntity("Lions")
        val snakesTeamEntityOptional = repository.findTeamEntity("Snakes")
        val sharksTeamEntityOptional = repository.findTeamEntity("Sharks")

        // ASSERT: Lions & Snakes &Sharks team entries exist in the database
        Assertions.assertTrue(lionsTeamEntityOptional.isPresent)
        Assertions.assertTrue(snakesTeamEntityOptional.isPresent)
        Assertions.assertTrue(sharksTeamEntityOptional.isPresent)

        val lionsTeamStandingEntity = repository.findTeamStandingEntity(lionsTeamEntityOptional.get().getId())
        val snakesTeamStandingEntity = repository.findTeamStandingEntity(snakesTeamEntityOptional.get().getId())
        val sharksTeamStandingEntity = repository.findTeamStandingEntity(sharksTeamEntityOptional.get().getId())

        // ASSERT: Team standing entities for lions and snakes exist
        Assertions.assertNotNull(lionsTeamStandingEntity)
        Assertions.assertNotNull(snakesTeamStandingEntity)
        Assertions.assertNotNull(sharksTeamStandingEntity)

        // ASSERT: Lions team standing entity properties are correct after match entry processed
        Assertions.assertEquals(4, lionsTeamStandingEntity.getPoints())
        Assertions.assertEquals(1, lionsTeamStandingEntity.getPosition())

        // ASSERT: Sharks team standing entity properties are correct after match entry processed
        Assertions.assertEquals(1, sharksTeamStandingEntity.getPoints())
        Assertions.assertEquals(2, sharksTeamStandingEntity.getPosition())

        // ASSERT: Snakes team standing entity properties are correct after match entry processed
        Assertions.assertEquals(0, snakesTeamStandingEntity.getPoints())
        Assertions.assertEquals(3, snakesTeamStandingEntity.getPosition())
    }
}
