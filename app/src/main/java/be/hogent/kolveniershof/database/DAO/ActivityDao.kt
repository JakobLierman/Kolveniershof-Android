package be.hogent.kolveniershof.database.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import be.hogent.kolveniershof.database.databaseModels.DatabaseActivity

@Dao
interface ActivityDao : BaseDAO<DatabaseActivity>{
    @Query("SELECT * FROM bus_table")
    fun getAllActivities(): LiveData<List<DatabaseActivity>>

    @Query("SELECT * FROM bus_table WHERE bus_id =:id")
    fun getActivityById(id: String): LiveData<DatabaseActivity>

}