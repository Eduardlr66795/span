package interview.span.infrastructure.persistence.repositories

import interview.span.domain.persistence.entities.TeamEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface TeamRepository: CrudRepository<TeamEntity, Long> {

    fun findByName(name: String): Optional<TeamEntity>
}
