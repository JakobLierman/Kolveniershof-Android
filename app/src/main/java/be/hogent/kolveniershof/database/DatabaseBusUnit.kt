package be.hogent.kolveniershof.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import be.hogent.kolveniershof.domain.Bus
import be.hogent.kolveniershof.domain.BusUnit
import be.hogent.kolveniershof.domain.User


@Entity(tableName = "busUnit_table", foreignKeys = [(
        ForeignKey(
            entity = DatabaseBus::class,
            parentColumns = arrayOf("busUnit_id"),
            childColumns = arrayOf("bus_id"))
        ), ForeignKey(
    entity = DatabaseWorkday::class,
    parentColumns = arrayOf("busUnit_id"),
    childColumns = arrayOf("workday_id")
)])
data class DatabaseBusUnit constructor(
    @ColumnInfo(name = "busUnit_id")
    var id: String = "",
    @ColumnInfo(name = "busUnit_bus")
    var bus: Bus,
    @ColumnInfo(name = "busUnit_mentors")
    var mentors: MutableList<User>,
    @ColumnInfo(name = "busUnit_clients")
    var clients: MutableList<User>
)

fun List<DatabaseBusUnit>.asDomainModel(): List<BusUnit> {
    return map {
        BusUnit(
            id = it.id,
            bus = it.bus,
            mentors = it.mentors,
            clients = it.clients
        )
    }
}