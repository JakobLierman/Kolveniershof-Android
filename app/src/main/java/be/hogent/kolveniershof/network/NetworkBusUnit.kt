package be.hogent.kolveniershof.network

import android.os.Parcelable
import be.hogent.kolveniershof.database.DatabaseBusUnit
import be.hogent.kolveniershof.domain.Bus
import be.hogent.kolveniershof.domain.BusUnit
import be.hogent.kolveniershof.domain.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

/**
 * BusUnit entity
 *
 * @property id
 * @property bus
 * @property mentors
 * @property clients
 */
/*@Parcelize
data class BusUnit(
    @field:Json(name = "_id")
    val id: String,
    @field:Json(name  = "bus")
    val bus: Bus,
    @field:Json(name = "mentors")
    val mentors: MutableList<User>,
    @field:Json(name = "clients")
    val clients: MutableList<User>
) : Parcelable {
    override fun toString(): String {
        return bus.toString()
    }
}*/


@JsonClass(generateAdapter = true)
data class NetworkBusUnitContainer(
    val busUnits: List<NetworkBusUnit>
)

@JsonClass(generateAdapter = true)
data class NetworkBusUnit(
    @field:Json(name = "_id")
    val id: String,
    @field:Json(name  = "bus")
    val bus: Bus,
    @field:Json(name = "mentors")
    val mentors: MutableList<User>,
    @field:Json(name = "clients")
    val clients: MutableList<User>
)

fun NetworkBusUnitContainer.asDomainModel(): List<BusUnit> {
    return busUnits.map {
        BusUnit(
            id = it.id,
            bus = it.bus,
            mentors = it.mentors,
            clients = it.clients
        )
    }
}

fun NetworkBusUnitContainer.asDatabaseModel(): Array<DatabaseBusUnit> {
    return busUnits.map {
        DatabaseBusUnit(
            id = it.id,
            bus = it.bus,
            mentors = it.mentors,
            clients = it.clients
        )
    }.toTypedArray()
}




















