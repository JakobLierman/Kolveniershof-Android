package be.hogent.kolveniershof.database.databaseModels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import be.hogent.kolveniershof.domain.Bus
import be.hogent.kolveniershof.domain.BusUnit
import be.hogent.kolveniershof.domain.User


@Entity(tableName = "busUnit_table", foreignKeys = [(
        ForeignKey(
            entity = DatabaseBus::class,
            parentColumns = arrayOf("bus_id"),
            childColumns = arrayOf("bus_id"))
        ), ForeignKey(
    entity = DatabaseWorkday::class,
    parentColumns = arrayOf("workday_id"),
    childColumns = arrayOf("workday_id")
)])
data class DatabaseBusUnit constructor(
    @ColumnInfo(name = "busUnit_id")
    @PrimaryKey(autoGenerate = false)
    var id: String = "",
    @ColumnInfo(name = "bus_id", index = true)
    var busId: String,
    @ColumnInfo(name = "workday_id", index = true)
    var workdayId: String
)
