package be.hogent.kolveniershof.network

import android.os.Parcelable
import be.hogent.kolveniershof.database.DatabaseComment
import be.hogent.kolveniershof.domain.Comment
import be.hogent.kolveniershof.domain.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

/*@Parcelize
data class Comment(
    @field:Json(name = "_id")
    val id: String,
    @field:Json(name = "comment")
    val comment: String,
    @field:Json(name = "user")
    val user: User
) : Parcelable {
    override fun toString(): String {
        return comment.trim()
    }
}*/

@JsonClass(generateAdapter = true)
data class NetworkCommentContainer(
    val comments: List<NetworkComment>
)

@JsonClass(generateAdapter = true)
data class NetworkComment(
    @field:Json(name = "_id")
    val id: String,
    @field:Json(name = "comment")
    val comment: String,
    @field:Json(name = "user")
    val user: User
)

fun NetworkCommentContainer.asDomainModel(): List<Comment> {
    return comments.map {
        Comment(
            id = it.id,
            comment = it.comment,
            user = it.user
        )
    }
}

fun NetworkCommentContainer.asDatabaseModel(): Array<DatabaseComment> {
    return comments.map {
        DatabaseComment(
            id = it.id,
            comment = it.comment,
            user = it.user
        )
    }.toTypedArray()
}










