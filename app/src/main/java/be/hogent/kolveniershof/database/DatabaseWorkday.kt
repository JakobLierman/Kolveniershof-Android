package be.hogent.kolveniershof.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import be.hogent.kolveniershof.domain.*
import java.util.*

@Entity(tableName = "workday_table")
data class DatabaseWorkday constructor(
    @ColumnInfo(name = "workday_id")
    var id: String = "",
    @ColumnInfo(name = "workday_date")
    var date: Date,
    @ColumnInfo(name = "workday_name")
    var daycareMentors: MutableList<User>,
    @ColumnInfo(name = "workday_morningBusses")
    var morningBusses: MutableList<BusUnit>,
    @ColumnInfo(name = "workday_amActivities")
    var amActivities: MutableList<ActivityUnit>,
    @ColumnInfo(name = "workday_lunch")
    var lunch: LunchUnit,
    @ColumnInfo(name = "workday_pmActivities")
    var pmActivities: MutableList<ActivityUnit>,
    @ColumnInfo(name = "workday_eveningBusses")
    var eveningBusses: MutableList<BusUnit>,
    @ColumnInfo(name = "workday_isHoliday")
    var isHoliday: Boolean? = false,
    @ColumnInfo(name = "workday_comments")
    var comments: MutableList<Comment>
)

fun List<DatabaseWorkday>.asDomainModel(): List<Workday> {
    return map {
        Workday(
            id = it.id,
            date = it.date,
            daycareMentors = it.daycareMentors,
            morningBusses = it.morningBusses,
            amActivities = it.amActivities,
            lunch = it.lunch,
            pmActivities = it.pmActivities,
            eveningBusses = it.eveningBusses,
            isHoliday = it.isHoliday,
            comments = it.comments
        )
    }
}