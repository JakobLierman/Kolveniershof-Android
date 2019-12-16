package be.hogent.kolveniershof.database.databaseModels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "activityUnitUserJoin", primaryKeys = arrayOf("activityUnitIdJOIN", "userIdJOIN"), foreignKeys = [(
        ForeignKey(
            entity = DatabaseActivityUnit::class,
            parentColumns = arrayOf("activityUnit_id"),
            childColumns = arrayOf("activityUnitIdJOIN"))
        ),
        ForeignKey(
            entity = DatabaseUser::class,
            parentColumns = arrayOf("user_id"),
            childColumns = arrayOf("userIdJOIN")
)])
data class DatabaseActivityUnitUserJOIN constructor(


    val activityUnitIdJOIN: String,

    @ColumnInfo(index = true)
    val userIdJOIN: String
)