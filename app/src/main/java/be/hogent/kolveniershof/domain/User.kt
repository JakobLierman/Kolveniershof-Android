package be.hogent.kolveniershof.domain

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
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
    @field:Json(name = "picture")
    val imgUrl: String? = null,
    @field:Json(name = "token")
    val token: String? = null
) {
    override fun toString(): String {
        return "$firstName $lastName"
    }
}