package be.hogent.kolveniershof.network

import be.hogent.kolveniershof.database.databaseModels.DatabaseBus
import be.hogent.kolveniershof.domain.Bus
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Bus entity
 *
 * @property id
 * @property name
 * @property color
 * @property iconUrl
 */
/*@Parcelize
data class Bus(
    @field:Json(name = "_id")
    val id: String,
    @field:Json(name  = "name")
    val name: String,
    @field:Json(name = "color")
    val color: String,
    @field:Json(name = "icon")
    val iconUrl: String
) : Parcelable {
    override fun toString(): String {
        return name.trim().capitalize()
    }
}*/

@JsonClass(generateAdapter = true)
data class NetworkBusContainer(
    val busses: List<NetworkBus>
)

@JsonClass(generateAdapter = true)
data class NetworkBus(
    @field:Json(name = "_id")
    val id: String,
    @field:Json(name  = "name")
    val name: String,
    @field:Json(name = "color")
    val color: String,
    @field:Json(name = "icon")
    val iconUrl: String
)

fun NetworkBusContainer.asDomainModel(): List<Bus> {
    return busses.map {
        Bus(
            id = it.id,
            name = it.name,
            color = it.color,
            iconUrl = it.iconUrl
        )
    }
}



