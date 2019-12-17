package be.hogent.kolveniershof.database.databaseModels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import be.hogent.kolveniershof.domain.Activity

@Entity(tableName = "activity_table")
data class DatabaseActivity constructor(
    @ColumnInfo(name = "activity_id")
    @PrimaryKey(autoGenerate = false)
    var activityId: String = "",
    @ColumnInfo(name = "activity_name")
    var name: String = "",
    @ColumnInfo(name = "activity_icon")
    var icon: String = ""
){
    companion object {
        fun toActivity(dbActivity: DatabaseActivity): Activity {
            return Activity(
                    id = dbActivity.activityId,
                    name = dbActivity.name,
                    icon = dbActivity.icon
                )
        }
    }
}

