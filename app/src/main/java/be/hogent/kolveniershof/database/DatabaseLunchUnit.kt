package be.hogent.kolveniershof.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import be.hogent.kolveniershof.domain.LunchUnit
import be.hogent.kolveniershof.domain.User


@Entity(tableName = "lunchUnit_table", foreignKeys = arrayOf(
    ForeignKey(
        entity = DatabaseWorkday::class,
        parentColumns = arrayOf("lunchUnit_id"),
        childColumns = arrayOf("workday_id"))
)
)
data class DatabaseLunchUnit constructor(
    @ColumnInfo(name = "lunchUnit_id")
    var id: String = "",
    @ColumnInfo(name = "lunchUnit_lunch")
    var lunch: String = "",
    @ColumnInfo(name = "lunchUnit_mentors")
    var mentors: MutableList<User>,
    @ColumnInfo(name = "lunchUnit_clients")
    var clients: MutableList<User>
)

fun List<DatabaseLunchUnit>.asDomainModel(): List<LunchUnit> {
    return map {
        LunchUnit(
            id = it.id,
            lunch = it.lunch,
            mentors = it.mentors,
            clients = it.clients
        )
    }
}