package be.hogent.kolveniershof.database.databaseModels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import be.hogent.kolveniershof.domain.*
import java.util.*

@Entity(tableName = "workday_table")
data class DatabaseWorkday constructor(
    @ColumnInfo(name = "workday_id")
    @PrimaryKey(autoGenerate = false)
    var id: String = "",
    @ColumnInfo(name = "workday_date")
    var date: String,

    //TODO: Many to many user - workday
    /*@ColumnInfo(name = "workday_name")
    var daycareMentors: MutableList<User>,*/

    @ColumnInfo(name = "workday_isHoliday")
    var isHoliday: Boolean? = false
)
