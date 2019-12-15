package be.hogent.kolveniershof.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import be.hogent.kolveniershof.domain.User
import java.util.*

@Entity(tableName = "user_table")
data class DatabaseUser constructor(
    @ColumnInfo(name = "user_id")
    var userId: String = "",
    @ColumnInfo(name = "user_firstName")
    var firstName: String = "",
    @ColumnInfo(name = "user_lastName")
    var lastName: String = "",
    @ColumnInfo(name = "user_email")
    var email: String? = "",
    @ColumnInfo(name = "user_isAdmin")
    var isAdmin: Boolean = false,
    @ColumnInfo(name = "user_birthday")
    var birthday: Date,
    @ColumnInfo(name = "user_absentdates")
    var absentDates: MutableList<Date> = mutableListOf(),
    @ColumnInfo(name = "user_imageUrl")
    var imgUrl: String? = null,
    @ColumnInfo(name = "user_token")
    var token: String? = null
)

fun List<DatabaseUser>.asDomainModel(): List<User> {
    return map {
        User(
            id = it.userId,
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