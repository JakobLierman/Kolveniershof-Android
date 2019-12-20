package be.hogent.kolveniershof.domain

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Comment(
    @field:Json(name = "_id")
    val id: String,
    @field:Json(name = "comment")
    val comment: String,
    @field:Json(name = "user")
    val user: User?
){
    override fun toString(): String {
        return comment.trim()
    }
}