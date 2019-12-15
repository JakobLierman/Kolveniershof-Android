package be.hogent.kolveniershof.network

import android.os.Parcelable
import be.hogent.kolveniershof.database.DatabaseGroup
import be.hogent.kolveniershof.domain.Group
import be.hogent.kolveniershof.domain.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

/**
 * Group entity
 *
 * @property id
 * @property name
 * @property members
 */
/*@Parcelize
data class Group(
    @field:Json(name = "_id")
    val id: String,
    @field:Json(name  = "name")
    val name: String,
    @field:Json(name = "members")
    val members: MutableList<User>
) : Parcelable {
    override fun toString(): String {
        return name.trim().capitalize()
    }
}*/
@JsonClass(generateAdapter = true)
data class NetworkGroupContainer(
    val groups: List<NetworkGroup>
)

@JsonClass(generateAdapter = true)
data class NetworkGroup(
    @field:Json(name = "_id")
    val id: String,
    @field:Json(name  = "name")
    val name: String,
    @field:Json(name = "members")
    val members: MutableList<User>
)


fun NetworkGroupContainer.asDomainModel(): List<Group> {
    return groups.map {
        Group(
            id = it.id,
            name = it.name,
            members = it.members
        )
    }
}

fun NetworkGroupContainer.asDatabaseModel(): Array<DatabaseGroup> {
    return groups.map {
        DatabaseGroup(
            id = it.id,
            name = it.name,
            members = it.members
        )
    }.toTypedArray()
}