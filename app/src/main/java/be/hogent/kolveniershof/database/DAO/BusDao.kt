package be.hogent.kolveniershof.database.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import be.hogent.kolveniershof.database.databaseModels.DatabaseBus
import be.hogent.kolveniershof.database.databaseModels.DatabaseBusUnit
import io.reactivex.Single

@Dao
interface BusDao : BaseDAO<DatabaseBus>{
    @Query("SELECT * FROM bus_table")
    fun getAllBusses(): List<DatabaseBus>

    @Query("SELECT * FROM bus_table WHERE bus_id =:id")
    fun getBusById(id: String): DatabaseBus

}