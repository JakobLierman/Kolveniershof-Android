package be.hogent.kolveniershof.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import be.hogent.kolveniershof.domain.Group
import be.hogent.kolveniershof.domain.User


@Entity(tableName = "group_table")
data class DatabaseGroup constructor(
    @ColumnInfo(name = "group_id")
    var id: String = "",
    @ColumnInfo(name = "group_name")
    var name: String = "",
    @ColumnInfo(name = "group_members")
    var members: MutableList<User>
)

fun List<DatabaseGroup>.asDomainModel(): List<Group> {
    return map {
        Group(
            id = it.id,
            name = it.name,
            members = it.members
        )
    }
}