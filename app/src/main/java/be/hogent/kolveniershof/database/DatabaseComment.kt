package be.hogent.kolveniershof.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import be.hogent.kolveniershof.domain.Comment
import be.hogent.kolveniershof.domain.User


@Entity(tableName = "comment_table", foreignKeys = [
    ForeignKey(
        entity = DatabaseWorkday::class,
        parentColumns = arrayOf("comment_id"),
        childColumns = arrayOf("workday_id"))
    , ForeignKey(
        entity = DatabaseUser::class,
        parentColumns = arrayOf("comment_id"),
        childColumns = arrayOf("user_id"))
])
data class DatabaseComment(
    @ColumnInfo(name = "comment_id")
    var id: String = "",
    @ColumnInfo(name = "comment_comment")
    var comment: String = "",
    @ColumnInfo(name = "comment_user")
    var user: User
)

fun List<DatabaseComment>.asDomainModel(): List<Comment> {
    return map {
        Comment(
            id = it.id,
            comment = it.comment,
            user = it.user
        )
    }
}