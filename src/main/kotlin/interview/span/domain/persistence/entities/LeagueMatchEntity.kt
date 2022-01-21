package interview.span.domain.persistence.entities

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.UpdateTimestamp
import java.time.ZonedDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "league_matches")
class LeagueMatchEntity(

    @Column(name = "host_id", updatable = true, nullable = false)
    val hostId: Long,

    @Column(name = "host_name", updatable = true, nullable = false)
    val hostName: String,

    @Column(name = "host_score", updatable = true, nullable = false)
    val hostScore: Int,

    @Column(name = "opposition_id", updatable = true, nullable = false)
    val oppositionId: Long,

    @Column(name = "opposition_name", updatable = true, nullable = false)
    val oppositionName: String,

    @Column(name = "opposition_score", updatable = true, nullable = false)
    val oppositionScore: Int,
) {

    private constructor() : this(0L, "", 0, 1L, "", 0)

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id", updatable = false, nullable = false)
    private val id: Long = 0

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, columnDefinition = "DATETIME")
    private val updatedAt: ZonedDateTime = getCurrentDateTime()

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, columnDefinition = "DATETIME")
    private val createdAt: ZonedDateTime = getCurrentDateTime()

    private fun getCurrentDateTime(): ZonedDateTime {
        return ZonedDateTime.now()
    }

    fun getId(): Long {
        return id
    }
}
