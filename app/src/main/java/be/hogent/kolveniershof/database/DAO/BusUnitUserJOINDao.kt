package be.hogent.kolveniershof.database.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import be.hogent.kolveniershof.database.databaseModels.DatabaseActivityUnit
import be.hogent.kolveniershof.database.databaseModels.DatabaseActivityUnitUserJOIN
import be.hogent.kolveniershof.database.databaseModels.DatabaseBusUnitUserJOIN
import be.hogent.kolveniershof.database.databaseModels.DatabaseUser

@Dao
interface BusUnitUserJOINDao : BaseDAO<DatabaseBusUnitUserJOIN>{

    @Query("""
        SELECT * FROM user_table 
        INNER JOIN busUnitUserJoin 
        ON user_table.user_id = busUnitUserJoin.userIdJOIN 
        WHERE busUnitUserJoin.busUnitIdJOIN =:busUnit_Id
        """)
    fun getUsersFromBus(busUnit_Id: String): LiveData<MutableList<DatabaseUser>>

    @Query("""
        SELECT * FROM busUnit_table 
        INNER JOIN busUnitUserJoin 
        ON busUnit_table.busUnit_id = busUnitUserJoin.busUnitIdJOIN 
        WHERE busUnitUserJoin.userIdJOIN =:user_id
        """)
    fun getBussesFromUser(user_id: String): LiveData<MutableList<DatabaseActivityUnit>>
}