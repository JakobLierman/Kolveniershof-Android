package be.hogent.kolveniershof.database

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "activityUnitUserJoin", primaryKeys = arrayOf("activityUnitId", "userId"), foreignKeys = [(
        ForeignKey(
            entity = DatabaseActivityUnit::class,
            parentColumns = arrayOf("activityUnit_id"),
            childColumns = arrayOf("activityUnitIdJOIN"))
        ), ForeignKey(
    entity = DatabaseUser::class,
    parentColumns = arrayOf("user_id"),
    childColumns = arrayOf("userIdJOIN")
)])
data class DatabaseActivityUnitUserJOIN constructor(
    val activityUnitIdJOIN: String,
    val userIdJOIN: String
)