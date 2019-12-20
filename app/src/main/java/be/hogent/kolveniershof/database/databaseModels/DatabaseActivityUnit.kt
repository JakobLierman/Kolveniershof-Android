package be.hogent.kolveniershof.database.databaseModels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import be.hogent.kolveniershof.domain.Activity
import be.hogent.kolveniershof.domain.ActivityUnit

@Entity(tableName = "activityUnit_table", foreignKeys = [(
        ForeignKey(
            entity = DatabaseActivity::class,
            parentColumns = arrayOf("activity_id"),
            childColumns = arrayOf("activity_id"))
        ), (ForeignKey(
    entity = DatabaseWorkday::class,
    parentColumns = arrayOf("workday_id"),
    childColumns = arrayOf("workday_id"))
)])
data class DatabaseActivityUnit constructor(
    @ColumnInfo(name = "activityUnit_id")
    @PrimaryKey(autoGenerate = false)
    val id: String = "",
    @ColumnInfo(name = "workday_id", index = true)
    val workdayId: String = "",
    @ColumnInfo(name = "activity_id", index = true)
    val activityId: String = "",
    val isAm: Boolean = false,
    val isPm: Boolean = false,
    val isDay: Boolean = false
)
