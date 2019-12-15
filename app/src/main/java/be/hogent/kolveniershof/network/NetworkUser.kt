package be.hogent.kolveniershof.network

import android.os.Parcelable
import be.hogent.kolveniershof.database.DatabaseUser
import be.hogent.kolveniershof.domain.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * User entity
 *
 * @property id
 * @property firstName
 * @property lastName
 * @property email
 * @property isAdmin
 * @property birthday
 * @property absentDates
 * @property imgUrl
 * @property token
 */
/*@Parcelize
data class User(
    @field:Json(name = "_id")
    val id: String,
    @field:Json(name = "firstName")
    val firstName: String,
    @field:Json(name = "lastName")
    val lastName: String,
    @field:Json(name = "email")
    val email: String? = null,
    @field:Json(name = "admin")
    val isAdmin: Boolean = false,
    @field:Json(name = "birthday")
    val birthday: Date,
    @field:Json(name = "absentDates")
    val absentDates: MutableList<Date> = mutableListOf(),
    @field:Json(name = "picture")
    val imgUrl: String? = null,
    @field:Json(name = "token")
    val token: String? = null
) : Parcelable {
    override fun toString(): String {
        return "$firstName $lastName"
    }
}*/
@JsonClass(generateAdapter = true)
data class NetworkUserContainer(
    val users: List<NetworkUser>
)

@JsonClass(generateAdapter = true)
data class NetworkUser(
    @field:Json(name = "_id")
    val id: String,
    @field:Json(name = "firstName")
    val firstName: String,
    @field:Json(name = "lastName")
    val lastName: String,
    @field:Json(name = "email")
    val email: String? = null,
    @field:Json(name = "admin")
    val isAdmin: Boolean = false,
    @field:Json(name = "birthday")
    val birthday: Date,
    @field:Json(name = "absentDates")
    val absentDates: MutableList<Date> = mutableListOf(),
    @field:Json(name = "picture")
    val imgUrl: String? = null,
    @field:Json(name = "token")
    val token: String? = null
)


fun NetworkUserContainer.asDomainModel(): List<User> {
    return users.map {
        User(
            id = it.id,
            firstName = it.firstName,
            lastName = it.lastName,
            email = it.email,
            isAdmin = it.isAdmin,
            birthday = it.birthday,
            absentDates = it.absentDates,
            imgUrl = it.imgUrl,
            token = it.token
        )
    }
}


fun NetworkUserContainer.asDatabseModel(): Array<DatabaseUser> {
    return users.map {
        DatabaseUser(
            userId = it.id,
            firstName = it.firstName,
            lastName = it.lastName,
            email = it.email,
            isAdmin = it.isAdmin,
            birthday = it.birthday,
            absentDates = it.absentDates,
            imgUrl = it.imgUrl,
            token = it.token
        )
    }.toTypedArray()
}