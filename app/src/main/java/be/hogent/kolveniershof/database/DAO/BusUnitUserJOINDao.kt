package be.hogent.kolveniershof.database.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import be.hogent.kolveniershof.database.databaseModels.DatabaseActivityUnit
import be.hogent.kolveniershof.database.databaseModels.DatabaseActivityUnitUserJOIN
import be.hogent.kolveniershof.database.databaseModels.DatabaseBusUnitUserJOIN
import be.hogent.kolveniershof.database.databaseModels.DatabaseUser
import io.reactivex.Single

@Dao
interface BusUnitUserJOINDao : BaseDAO<DatabaseBusUnitUserJOIN>{

    @Query("""
        SELECT * FROM user_table 
        INNER JOIN busUnitUserJoin 
        ON user_table.user_id = busUnitUserJoin.userIdJOIN 
        WHERE busUnitUserJoin.busUnitIdJOIN =:busUnit_Id AND user_isAdmin = 1
        """)
    fun getMentorsFromBus(busUnit_Id: String): Single<List<DatabaseUser>>

    @Query("""
        SELECT * FROM user_table 
        INNER JOIN busUnitUserJoin 
        ON user_table.user_id = busUnitUserJoin.userIdJOIN 
        WHERE busUnitUserJoin.busUnitIdJOIN =:busUnit_Id AND user_isAdmin = 0
        """)
    fun getClientsFromBus(busUnit_Id: String): Single<List<DatabaseUser>>

}