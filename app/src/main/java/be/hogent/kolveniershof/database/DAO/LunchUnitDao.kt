package be.hogent.kolveniershof.database.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import be.hogent.kolveniershof.database.databaseModels.DatabaseLunchUnit
import io.reactivex.Single

@Dao
interface LunchUnitDao : BaseDAO<DatabaseLunchUnit>{
    @Query("SELECT * FROM lunchUnit_table")
    fun getAllLunches(): Single<List<DatabaseLunchUnit>>

    @Query("SELECT * FROM lunchUnit_table WHERE lunchUnit_id =:id")
    fun getLunchById(id: String): Single<DatabaseLunchUnit>

    @Query("SELECT * FROM lunchUnit_table WHERE workday_id =:id")
    fun getLunchFromWorkday(id: String): Single<DatabaseLunchUnit>

}