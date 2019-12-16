package be.hogent.kolveniershof.database.databaseModels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "busUnitUserJoin", primaryKeys = arrayOf("busUnitIdJOIN", "userIdJOIN"), foreignKeys = [(
        ForeignKey(
            entity = DatabaseBusUnit::class,
            parentColumns = arrayOf("busUnit_id"),
            childColumns = arrayOf("busUnitIdJOIN"))
        ),
    ForeignKey(
        entity = DatabaseUser::class,
        parentColumns = arrayOf("user_id"),
        childColumns = arrayOf("userIdJOIN")
    )])
data class DatabaseBusUnitUserJOIN constructor(


    val busUnitIdJOIN: String,

    @ColumnInfo(index = true)
    val userIdJOIN: String
)