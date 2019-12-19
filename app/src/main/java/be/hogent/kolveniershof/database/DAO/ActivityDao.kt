package be.hogent.kolveniershof.database.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import be.hogent.kolveniershof.database.databaseModels.DatabaseActivity
import io.reactivex.Single

@Dao
interface ActivityDao : BaseDAO<DatabaseActivity>{
    @Query("SELECT * FROM activity_table")
    fun getAllActivities(): Single<List<DatabaseActivity>>

    @Query("SELECT * FROM activity_table WHERE activity_id =:id")
    fun getActivityById(id: String): Single<DatabaseActivity>

}