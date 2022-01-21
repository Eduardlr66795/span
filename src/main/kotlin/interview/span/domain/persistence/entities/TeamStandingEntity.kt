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
@Table(name = "team_standings")
class TeamStandingEntity(
    @Column(name = "team_id", updatable = false, nullable = false)
    private val teamId: Long,

    @Column(name = "team_name", updatable = false, nullable = false)
    private val teamName: String,

    @Column(name = "points", updatable = true, nullable = false)
    private var points: Int,

    @Column(name = "position", updatable = true, nullable = false)
    private var position: Int
) {

    private constructor() : this(0L, "", 0, 0)

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

    fun getTeamName(): String {
        return teamName
    }

    fun getPoints(): Int {
        return points
    }

    fun setPoints(points: Int) {
        this.points += points
    }

    fun setPosition(position: Int) {
        this.position = position
    }

    fun getPosition(): Int {
        return position
    }
}
