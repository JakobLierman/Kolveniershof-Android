package be.hogent.kolveniershof.database.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.hogent.kolveniershof.database.databaseModels.DatabaseWorkday
import io.reactivex.Single
import java.util.*

@Dao
interface WorkdayDao : BaseDAO<DatabaseWorkday>{
    @Query("SELECT * FROM workday_table ORDER BY workday_date DESC")
    fun getAllWorkdays(): List<DatabaseWorkday>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllWorkdays(vararg workdays: DatabaseWorkday)

    @Query("SELECT * FROM workday_table WHERE workday_id =:id")
    fun getWorkdayById(id: String): LiveData<DatabaseWorkday>

    @Query("SELECT * FROM workday_table WHERE workday_date =:date")
    fun getByWorkdateByDate(date: String): LiveData<DatabaseWorkday>

    @Query("select count(*) from workday_table")
    fun getRowCount(): Int

    @Query("SELECT * FROM workday_table JOIN WorkdayUserJoin ON workday_id JOIN user_table ON user_id WHERE workday_date =:date AND user_id =:userId")
    fun getWorkdayByDateByUser(date: String, userId: String):LiveData<DatabaseWorkday>

}