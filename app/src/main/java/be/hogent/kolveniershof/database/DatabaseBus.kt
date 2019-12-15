package be.hogent.kolveniershof.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import be.hogent.kolveniershof.domain.Bus

@Entity(tableName = "bus_table")
data class DatabaseBus constructor(
    @ColumnInfo(name = "bus_id")
    var id: String = "",
    @ColumnInfo(name = "bus_name")
    var name: String = "",
    @ColumnInfo(name = "bus_color")
    var color: String = "",
    @ColumnInfo(name = "bus_iconUrl")
    var iconUrl: String = ""
)

fun List<DatabaseBus>.asDomainModel(): List<Bus> {
    return map {
        Bus(
            id = it.id,
            name = it.name,
            color = it.color,
            iconUrl = it.iconUrl
        )
    }
}