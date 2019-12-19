package be.hogent.kolveniershof.database.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import be.hogent.kolveniershof.database.databaseModels.*
import io.reactivex.Single

@Dao
interface WorkdayUserJOINDao : BaseDAO<DatabaseWorkdayUserJOIN> {

    @Query(
        """
        SELECT * FROM user_table 
        INNER JOIN workdayUserJoin 
        ON user_table.user_id = workdayUserJoin.userIdJOIN 
        WHERE workdayUserJoin.workdayIdJOIN =:workday_Id
        """
    )
    fun getUsersFromWorkday(workday_Id: String): Single<List<DatabaseUser>>

    @Query(
        """
        SELECT * FROM workday_table 
        INNER JOIN workdayUserJoin 
        ON workday_table.workday_id = workdayUserJoin.workdayIdJOIN 
        WHERE workdayUserJoin.userIdJOIN =:user_id
        """
    )
    fun getWorkdaysFromUser(user_id: String): Single<List<DatabaseWorkday>>
}