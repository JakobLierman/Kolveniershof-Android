/*package be.hogent.kolveniershof.database
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.util.*
@Dao
interface KolveniershofDatabaseDao {
    //--Activity---------------------------------------------------------------
    @Query("SELECT * FROM activity_table")
    fun getAllActivities(): LiveData<List<DatabaseActivity>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllActivities(vararg activities: DatabaseActivity)
    //--ActivityUnit---------------------------------------------------------------
    //--Bus---------------------------------------------------------------
    @Query("SELECT * FROM bus_table")
    fun getAllBuses(): LiveData<List<DatabaseBus>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllBuses(vararg buses: DatabaseBus)
    //--BusUnit---------------------------------------------------------------
    @Query("SELECT * FROM busUnit_table")
    fun getAllBusUnits(): LiveData<List<DatabaseBus>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllBusUnits(vararg busUnits: DatabaseBusUnit)
    //--Comment---------------------------------------------------------------
    @Query("SELECT * FROM comment_table")
    fun getAllComments(): LiveData<List<DatabaseComment>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllComments(vararg comments: DatabaseComment)
    //--Group---------------------------------------------------------------
    @Query("SELECT * FROM group_table")
    fun getAllGroups(): LiveData<List<DatabaseGroup>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllGroups(vararg groups: DatabaseGroup)
    //--Lunch---------------------------------------------------------------
    @Query("SELECT * FROM lunchUnit_table")
    fun getAllLunchUnits(): LiveData<List<DatabaseLunchUnit>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllLunchUnits(vararg lunches: DatabaseLunchUnit)
    //--User---------------------------------------------------------------
    //--Workdays---------------------------------------------------------------
    //--WorkdayById---------------------------------------------------------------
/*
    @Query("SELECT * FROM workdayById_table where workdayById_id == :id")
    fun getWorkdayById(id: string): LiveData<DatabaseWorkdayById>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllWorkdaysById(vararg workdaysById: DatabaseWorkdayById)
*/
    //--WorkdayByDateByUser---------------------------------------------------------------

    @Query("SELECT 1 FROM workday_table where workday_date == :date limit 1")
    fun getWorkdayByDateByUser(date: Date/*, token: String*/): LiveData<DatabaseWorkdayByDateByUser>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWorkdayByDateByUser(vararg workdaysByDateByUser: DatabaseWorkdayByDateByUser)



}

*/
