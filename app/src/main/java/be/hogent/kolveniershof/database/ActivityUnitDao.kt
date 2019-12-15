package be.hogent.kolveniershof.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ActivityUnitDao {
    @Query("SELECT * FROM activityUnit_table")
    fun getAllActivityUnits(): LiveData<List<DatabaseActivityUnit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllActivityUnits(vararg activityUnits: DatabaseActivityUnit)

    @Query("SELECT * FROM activityUnit_table WHERE activityUnit_id =:id")
    fun getActivityById(id: String): LiveData<DatabaseActivityUnit>
}