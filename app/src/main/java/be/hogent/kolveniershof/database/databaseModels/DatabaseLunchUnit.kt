package be.hogent.kolveniershof.database.databaseModels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import be.hogent.kolveniershof.domain.LunchUnit
import be.hogent.kolveniershof.domain.User


@Entity(tableName = "lunchUnit_table", foreignKeys = arrayOf(
    ForeignKey(
        entity = DatabaseWorkday::class,
        parentColumns = arrayOf("workday_id"),
        childColumns = arrayOf("workday_id"))
)
)
data class DatabaseLunchUnit constructor(
    @ColumnInfo(name = "lunchUnit_id")
    @PrimaryKey(autoGenerate = false)
    var id: String = "",
    @ColumnInfo(name = "workday_id", index = true)
    var workdayId: String = "",
    @ColumnInfo(name = "lunch")
    var luch: String
)