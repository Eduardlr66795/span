package interview.span.infrastructure.persistence.repositories

import interview.span.domain.persistence.entities.LeagueMatchOutcomeEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface LeagueMatchOutcomeRepository : CrudRepository<LeagueMatchOutcomeEntity, Long>
