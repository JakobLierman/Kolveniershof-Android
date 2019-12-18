package be.hogent.kolveniershof.network

import be.hogent.kolveniershof.database.databaseModels.DatabaseLunchUnit
import be.hogent.kolveniershof.domain.LunchUnit
import be.hogent.kolveniershof.domain.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * LunchUnit entity
 *
 * @property id
 * @property lunch
 * @property mentors
 * @property clients
 */
/*@Parcelize
data class LunchUnit(
    @field:Json(name = "_id")
    val id: String,
    @field:Json(name  = "lunch")
    val lunch: String,
    @field:Json(name = "mentors")
    val mentors: MutableList<User>,
    @field:Json(name = "clients")
    val clients: MutableList<User>
) : Parcelable {
    override fun toString(): String {
        return lunch.trim().capitalize()
    }
}*/
@JsonClass(generateAdapter = true)
data class NetworkLunchUnitContainer(
    val lunchUnits: List<NetworkLunchUnit>
)




@JsonClass(generateAdapter = true)
data class NetworkLunchUnit(
    @field:Json(name = "_id")
    val id: String,
    @field:Json(name  = "lunch")
    val lunch: String,
    @field:Json(name = "mentors")
    val mentors: MutableList<User>,
    @field:Json(name = "clients")
    val clients: MutableList<User>
)


fun NetworkLunchUnitContainer.asDomainModel(): List<LunchUnit> {
    return lunchUnits.map {
        LunchUnit(
            id = it.id,
            lunch = it.lunch
        )
    }
}

