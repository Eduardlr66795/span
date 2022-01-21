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
@Table(name = "teams")
class TeamEntity(

    @Column(name = "name", updatable = true, nullable = false)
    private val name: String,
    ) {

    private constructor() : this("")

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

    fun getName(): String {
        return name
    }
}