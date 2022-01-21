package interview.span.infrastructure.persistence.repositories

import interview.span.domain.persistence.entities.TeamStandingEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface TeamStandingRepository : CrudRepository<TeamStandingEntity, Long> {

    @Query("FROM TeamStandingEntity inv WHERE teamId = :teamId")
    fun findByTeamId(
        @Param("teamId") teamId: Long
    ): Optional<TeamStandingEntity>

    @Query("SELECT DISTINCT points FROM TeamStandingEntity ORDER BY points DESC")
    fun findTeamStandingEntriesOrderByPointsDes(): List<Int>

    @Query("FROM TeamStandingEntity WHERE points in :points")
    fun findByPoints(
        @Param("points") points: List<Int>
    ): List<TeamStandingEntity>
    //
    @Query("FROM TeamStandingEntity WHERE points = :points")
    fun findByPoints(
        @Param("points") points: Int
    ): List<TeamStandingEntity>
}
