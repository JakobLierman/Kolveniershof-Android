package be.hogent.kolveniershof.network

import android.os.Parcelable
import be.hogent.kolveniershof.database.DatabaseActivity
import be.hogent.kolveniershof.domain.Activity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

/**
 * Activity entity
 *
 * @property id
 * @property name
 * @property icon
 */
/*@Parcelize
data class Activity( //NetworkActivity
    @field:Json(name = "_id")
    val id: String,
    @field:Json(name  = "name")
    val name: String,
    @field:Json(name = "icon")
    val icon: String
) : Parcelable {
    override fun toString(): String {
        return name.trim().capitalize()
    }
}*/


@JsonClass(generateAdapter = true)
data class NetworkActivityContainer(
    val activities: List<NetworkActivity>
)

@JsonClass(generateAdapter = true)
data class NetworkActivity( //NetworkActivity
    @field:Json(name = "_id")
    val id: String,
    @field:Json(name  = "name")
    val name: String,
    @field:Json(name = "icon")
    val icon: String
)

fun NetworkActivityContainer.asDomainModel(): List<Activity> {
    return activities.map {
        Activity(
            id = it.id,
            name = it.name,
            icon = it.icon
        )
    }
}

fun NetworkActivityContainer.asDatabaseModel(): Array<DatabaseActivity> {
    return activities.map {
        DatabaseActivity(
            activityId = it.id,
            name = it.name,
            icon = it.icon
        )
    }.toTypedArray()
}