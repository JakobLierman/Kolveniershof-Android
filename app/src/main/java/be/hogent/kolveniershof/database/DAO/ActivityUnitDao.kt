package be.hogent.kolveniershof.database.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.hogent.kolveniershof.database.databaseModels.DatabaseActivityUnit

@Dao
interface ActivityUnitDao : BaseDAO<DatabaseActivityUnit>{
    @Query("SELECT * FROM activityUnit_table")
    fun getAllActivityUnits(): LiveData<List<DatabaseActivityUnit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllActivityUnits(vararg activityUnits: DatabaseActivityUnit)

    @Query("SELECT * FROM activityUnit_table WHERE activityUnit_id =:id")
    fun getActivityById(id: String): LiveData<DatabaseActivityUnit>

    @Query("SELECT * FROM activityUnit_table WHERE workday_id =:workdayId AND isAm == 1")
    fun getAmActivitiesFromWorkday(workdayId: String) : LiveData<MutableList<DatabaseActivityUnit>>

    @Query("SELECT * FROM activityUnit_table WHERE workday_id =:workdayId AND isAm == 0")
    fun getPmActivitiesFromWorkday(workdayId: String) : LiveData<MutableList<DatabaseActivityUnit>>
}
