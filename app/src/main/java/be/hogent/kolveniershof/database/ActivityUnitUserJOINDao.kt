package be.hogent.kolveniershof.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface ActivityUnitUserJOINDao {

    @Query("SELECT * FROM user_table INNER JOIN activityUnitUserJoin ON user_table.user_id = userIdJOIN WHERE activityUnitIdJOIN =:activityId")
    fun getUserFromActivities(activityId: String): LiveData<MutableList<DatabaseUser>>
}