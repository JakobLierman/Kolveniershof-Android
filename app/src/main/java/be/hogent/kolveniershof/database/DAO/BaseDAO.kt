package be.hogent.kolveniershof.database.DAO

import androidx.room.Insert
import androidx.room.OnConflictStrategy

interface BaseDAO<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(items: List<T>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item: T)
}