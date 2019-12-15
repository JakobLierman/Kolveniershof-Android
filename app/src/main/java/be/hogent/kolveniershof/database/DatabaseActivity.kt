package be.hogent.kolveniershof.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import be.hogent.kolveniershof.domain.Activity

@Entity(tableName = "activity_table")
data class DatabaseActivity constructor(
    @ColumnInfo(name = "activity_id")
    var activityId: String = "",
    @ColumnInfo(name = "activity_name")
    var name: String = "",
    @ColumnInfo(name = "activity_icon")
    var icon: String = ""
)

fun List<DatabaseActivity>.asDomainModel(): List<Activity> {
    return map {
        Activity(
            id = it.activityId,
            name = it.name,
            icon = it.icon
        )
    }
}