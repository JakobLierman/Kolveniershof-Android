package be.hogent.kolveniershof.database.DAO

import androidx.lifecycle.LiveData
import androidx.room.*
import be.hogent.kolveniershof.database.databaseModels.DatabaseUser

@Dao
interface UserDao : BaseDAO<DatabaseUser>{
    @Query("SELECT * FROM user_table")
    fun getAllUsers(): LiveData<List<DatabaseUser>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllUsers(vararg users: DatabaseUser)

    @Query("SELECT * FROM user_table WHERE user_id =:id")
    fun getUSerById(id: String): LiveData<DatabaseUser>

    @Query("SELECT * FROM user_table WHERE user_email =:mail")
    fun getUSerByEmail(mail: String): LiveData<DatabaseUser>

    @Delete
    fun delete(user : DatabaseUser)

    @Query("select count(*) from user_table")
    fun getRowCount(): Int

}