package be.hogent.kolveniershof.database.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.hogent.kolveniershof.database.databaseModels.DatabaseActivityUnit
import be.hogent.kolveniershof.database.databaseModels.DatabaseBusUnit
import io.reactivex.Single

@Dao
interface BusUnitDao : BaseDAO<DatabaseBusUnit>{
    @Query("SELECT * FROM busUnit_table")
    fun getAllBusUnits(): List<DatabaseBusUnit>

    @Query("SELECT * FROM busUnit_table WHERE busUnit_id =:id")
    fun getBusUnitById(id: String): DatabaseBusUnit

    @Query("SELECT * FROM busUnit_table WHERE workday_id =:id")
    fun getBusUnitsFromWorkday(id: String) : List<DatabaseBusUnit>
}