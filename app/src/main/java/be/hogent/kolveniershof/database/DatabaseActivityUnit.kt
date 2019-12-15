package be.hogent.kolveniershof.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import be.hogent.kolveniershof.domain.Activity
import be.hogent.kolveniershof.domain.ActivityUnit
import be.hogent.kolveniershof.domain.User

@Entity(tableName = "activityUnit_table", foreignKeys = [(
        ForeignKey(
            entity = DatabaseActivity::class,
            parentColumns = arrayOf("activityUnit_id"),
            childColumns = arrayOf("activity_id"))
        ), ForeignKey(
    entity = DatabaseWorkday::class,
    parentColumns = arrayOf("activityUnit_id"),
    childColumns = arrayOf("workday_id")
), ForeignKey(
    entity = DatabaseUser::class,
    parentColumns = arrayOf("activityUnit_id"),
    childColumns = arrayOf("user_id")
), ForeignKey(
    entity = DatabaseUser::class,
    parentColumns = arrayOf("activityUnit_id"),
    childColumns = arrayOf("user_id")
)])
data class DatabaseActivityUnit constructor(
    @ColumnInfo(name = "activityUnit_id")
    val id: String,
    @ColumnInfo(name = "activityUnit_activity")
    val activity: Activity
)

fun List<DatabaseActivityUnit>.asDomainModel(): List<ActivityUnit> {
    return map {
        ActivityUnit(
            id = it.id,
            activity = it.activity
        )
    }
}
