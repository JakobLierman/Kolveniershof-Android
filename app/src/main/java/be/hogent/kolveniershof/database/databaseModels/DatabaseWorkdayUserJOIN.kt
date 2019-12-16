package be.hogent.kolveniershof.database.databaseModels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "WorkdayUserJoin", primaryKeys = arrayOf("workdayIdJOIN", "userIdJOIN"), foreignKeys = [(
        ForeignKey(
            entity = DatabaseWorkday::class,
            parentColumns = arrayOf("workday_id"),
            childColumns = arrayOf("workdayIdJOIN"))
        ),
    ForeignKey(
        entity = DatabaseUser::class,
        parentColumns = arrayOf("user_id"),
        childColumns = arrayOf("userIdJOIN")
    )])
data class DatabaseWorkdayUserJOIN constructor(


    val workdayIdJOIN: String,

    @ColumnInfo(index = true)
    val userIdJOIN: String
)