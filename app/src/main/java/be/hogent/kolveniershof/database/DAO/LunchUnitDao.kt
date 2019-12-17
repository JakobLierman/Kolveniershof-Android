package be.hogent.kolveniershof.database.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import be.hogent.kolveniershof.database.databaseModels.DatabaseLunchUnit

@Dao
interface LunchUnitDao : BaseDAO<DatabaseLunchUnit>{
    @Query("SELECT * FROM lunchUnit_table")
    fun getAllLunches(): LiveData<List<DatabaseLunchUnit>>

    @Query("SELECT * FROM lunchUnit_table WHERE lunchUnit_id =:id")
    fun getLunchById(id: String): LiveData<DatabaseLunchUnit>

    @Query("SELECT * FROM lunchUnit_table WHERE workday_id =:id")
    fun getLunchFromWorkday(id: String): LiveData<DatabaseLunchUnit>

}