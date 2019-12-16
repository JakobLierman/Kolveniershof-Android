package be.hogent.kolveniershof.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import be.hogent.kolveniershof.database.DAO.ActivityUnitDao
import be.hogent.kolveniershof.database.DAO.ActivityUnitUserJOINDao
import be.hogent.kolveniershof.database.DAO.UserDao
import be.hogent.kolveniershof.database.DAO.WorkdayDao
import be.hogent.kolveniershof.database.databaseModels.*

//import javax.inject.Inject


@Database(entities = [DatabaseActivity::class, DatabaseActivityUnit::class, DatabaseBus::class,
    DatabaseBusUnit::class, DatabaseLunchUnit::class, DatabaseUser::class,
    DatabaseWorkday::class, DatabaseActivityUnitUserJOIN::class],
    version = 1, exportSchema = true)
abstract class KolveniershofDatabase  : RoomDatabase() {
    abstract fun ActivityUnitDao() : ActivityUnitDao
    abstract fun ActivityUnitUserJOINDao() : ActivityUnitUserJOINDao
    abstract fun UserDao() : UserDao
    abstract fun WorkdayDao() : WorkdayDao

    companion object {
        @Volatile
        private var INSTANCE: KolveniershofDatabase? = null

        fun getInstance(context: Context): KolveniershofDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    KolveniershofDatabase::class.java,
                    "kolveniershofdb"
                ).build()
            }

            return INSTANCE!!

        }
    }
}
































