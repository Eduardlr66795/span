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
@Table(name = "league_match_outcomes")
class LeagueMatchOutcomeEntity(
    @Column(name = "match_id", updatable = true, nullable = false)
    private val matchId: Long,

    @Column(name = "teamId", updatable = true, nullable = false)
    private val teamId: Long,

    @Column(name = "team_name", updatable = true, nullable = false)
    private val teamName: String,

    @Column(name = "outcome", updatable = true, nullable = false)
    private val outcome: String,

    @Column(name = "points", updatable = true, nullable = false)
    private val points: Int
) {

    private constructor() : this(0L, 0L, "", "", 0)

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

    fun getMatchId(): Long {
        return matchId
    }

    fun getTeamId(): Long{
        return teamId
    }

    fun getTeamName(): String{
        return teamName
    }

    fun getOutcome(): String{
        return outcome
    }

    fun getPoints(): Int {
        return points
    }
}
