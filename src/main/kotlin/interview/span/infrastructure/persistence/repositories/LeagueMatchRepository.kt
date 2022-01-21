package interview.span.infrastructure.persistence.repositories

import interview.span.domain.persistence.entities.LeagueMatchEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface LeagueMatchRepository: CrudRepository<LeagueMatchEntity, Long>
