package interview.span.infrastructure.persistence.repositories

import interview.span.domain.persistence.entities.PointConfigurationEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface PointConfigurationRepository : CrudRepository<PointConfigurationEntity, Long> {

    @Query("FROM PointConfigurationEntity inv WHERE outcome = :outcome")
    fun findByOutcome(
        @Param("outcome") outcome: String
    ): Optional<PointConfigurationEntity>
}
