package be.hogent.kolveniershof.network

import be.hogent.kolveniershof.database.databaseModels.DatabaseUser
import be.hogent.kolveniershof.domain.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
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
) {

    companion object {

        fun asDomainModel(user : NetworkUser): User {
            return User(
                    id = user.id,
                    firstName = user.firstName,
                    lastName = user.lastName,
                    email = user.email,
                    isAdmin = user.isAdmin,
                    birthday = user.birthday,
                    absentDates = user.absentDates,
                    imgUrl = user.imgUrl,
                    token = user.token
                )
            }
        }
    }


