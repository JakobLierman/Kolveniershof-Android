package be.hogent.kolveniershof.database.databaseModels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import be.hogent.kolveniershof.domain.User
import org.joda.time.DateTime
import java.util.*

@Entity(tableName = "user_table")
data class DatabaseUser constructor(
    @ColumnInfo(name = "user_id")
    @PrimaryKey(autoGenerate = false)
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
    var birthday: String = "",

    //TODO: absent Dates
    /*@ColumnInfo(name = "user_absentdates")
    var absentDates: MutableList<String> = mutableListOf(),*/

    @ColumnInfo(name = "user_imageUrl")
    var imgUrl: String? = null,
    @ColumnInfo(name = "user_token")
    var token: String? = null
){
    companion object {

        fun toUser(dbuser: DatabaseUser): User {
            val user = User(dbuser.userId, dbuser.firstName, dbuser.lastName, dbuser.email, dbuser.isAdmin, Date(dbuser.birthday), mutableListOf(), dbuser.imgUrl, dbuser.token)
            return user
        }
    }
}

