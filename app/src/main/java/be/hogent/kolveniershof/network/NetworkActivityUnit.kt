package be.hogent.kolveniershof.network

import be.hogent.kolveniershof.database.databaseModels.DatabaseActivityUnit
import be.hogent.kolveniershof.domain.Activity
import be.hogent.kolveniershof.domain.ActivityUnit
import be.hogent.kolveniershof.domain.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * ActivityUnit entity
 *
 * @property id
 * @property activity
 * @property mentors
 * @property clients
 */
/*@Parcelize
data class ActivityUnit(
    @field:Json(name = "_id")
    val id: String,
    @field:Json(name  = "activity")
    val activity: Activity,
    @field:Json(name = "mentors")
    val mentors: MutableList<User>,
    @field:Json(name = "clients")
    val clients: MutableList<User>
) : Parcelable {
    override fun toString(): String {
        return activity.toString()
    }
    fun getImageName(): String {
        return activity.icon
    }
}*/

@JsonClass(generateAdapter = true)
data class NetworkActivityUnitContainer(
    val activityUnits: List<NetworkActivityUnit>
)

@JsonClass(generateAdapter = true)
data class NetworkActivityUnit( //NetworkActivityUnit
    @field:Json(name = "_id")
    val id: String,
    @field:Json(name  = "activity")
    val activity: Activity,
    @field:Json(name = "mentors")
    val mentors: MutableList<User>,
    @field:Json(name = "clients")
    val clients: MutableList<User>
)

fun NetworkActivityUnitContainer.asDomainModel(): List<ActivityUnit> {
    return activityUnits.map {
        ActivityUnit(
            id = it.id,
            activity = it.activity,
            mentors = it.mentors
        )
    }
}

