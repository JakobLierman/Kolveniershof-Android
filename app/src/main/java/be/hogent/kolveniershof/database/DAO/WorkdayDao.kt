package be.hogent.kolveniershof.database.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.hogent.kolveniershof.database.databaseModels.DatabaseWorkday
import java.util.*

@Dao
interface WorkdayDao {
    @Query("SELECT * FROM workday_table ORDER BY workday_date DESC")
    fun getAllWorkdays(): LiveData<List<DatabaseWorkday>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllWorkdays(vararg workdays: DatabaseWorkday)

    @Query("SELECT * FROM workday_table WHERE workday_id =:id")
    fun getWorkdayById(id: String): LiveData<DatabaseWorkday>

    @Query("SELECT * FROM workday_table WHERE workday_date =:date")
    fun getByWorkdateByDate(date: String): LiveData<DatabaseWorkday>


}