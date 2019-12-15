package be.hogent.kolveniershof.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM user_table")
    fun getAllUsers(): LiveData<List<DatabaseUser>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllUsers(vararg users: DatabaseUser)

    @Query("SELECT * FROM user_table WHERE user_id =:id")
    fun getUSerById(id: String): LiveData<DatabaseUser>
}