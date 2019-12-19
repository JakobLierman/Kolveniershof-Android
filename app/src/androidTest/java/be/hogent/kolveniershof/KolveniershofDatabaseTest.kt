package be.hogent.kolveniershof

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import be.hogent.kolveniershof.database.*
import be.hogent.kolveniershof.database.DAO.*
import be.hogent.kolveniershof.database.databaseModels.DatabaseActivity
import be.hogent.kolveniershof.database.databaseModels.DatabaseActivityUnit
import be.hogent.kolveniershof.database.databaseModels.DatabaseUser
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class KolveniershofDatabaseTest {
    private lateinit var db: KolveniershofDatabase
    private lateinit var userDao: UserDao
    private lateinit var activityDao: ActivityDao
    private lateinit var activityUnitDao: ActivityUnitDao
    private lateinit var activityUnitDAO: ActivityUnitDao
    private lateinit var activityUnitUserJOINDao: ActivityUnitUserJOINDao
    private lateinit var workdayDao: WorkdayDao


    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, KolveniershofDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        userDao = db.UserDao()
        activityDao = db.ActivityDao()
        activityUnitUserJOINDao = db.ActivityUnitUserJOINDao()
        activityUnitDAO = db.ActivityUnitDao()
        workdayDao = db.WorkdayDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    //USER TESTS
    @Test
    @Throws(Exception::class)
    fun insertAndGetUser() {
        val user = DatabaseUser(userId = "1")
        userDao.insertItem(user)
        val dbuser = userDao.getAllUsers().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).blockingGet().last()
        Assert.assertEquals(user.userId, dbuser.userId)
    }

    @Test
    @Throws(Exception::class)
    fun insertManyUsers() {
        val user1 = DatabaseUser(userId = "2")
        val user2 = DatabaseUser(userId = "3")
        userDao.insertItem(user1)
        userDao.insertItem(user2)
        Assert.assertEquals(userDao.getRowCount(), 2)
    }

    @Test
    @Throws(Exception::class)
    fun deleteUser() {
        val user1 = DatabaseUser(userId = "4")
        val user2 = DatabaseUser(userId = "5")
        userDao.insertItem(user1)
        userDao.insertItem(user2)
        userDao.delete(user1)
        Assert.assertEquals(userDao.getRowCount(), 1)
    }

    //ACTIVITY TESTS

    @Test
    @Throws(Exception::class)
    fun insertAndGetActivity() {
        val activity = DatabaseActivity(activityId = "1")
        activityDao.insertItem(activity)
        val dbactivity = activityDao.getAllActivities().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).blockingGet().last()
        Assert.assertEquals(activity.activityId, dbactivity.activityId)
    }

    @Test
    @Throws(Exception::class)
    fun insertManyActivities() {
        val activity1 = DatabaseActivity(activityId = "2")
        val activity2 = DatabaseActivity(activityId = "3")
        activityDao.insertItem(activity1)
        activityDao.insertItem(activity2)
        Assert.assertEquals(activityDao.getRowCount(), 2)
    }

    @Test
    @Throws(Exception::class)
    fun deleteActivity() {
        val activity1 = DatabaseActivity(activityId = "4")
        val activity2 = DatabaseActivity(activityId = "5")
        activityDao.insertItem(activity1)
        activityDao.insertItem(activity2)
        activityDao.delete(activity1)
        Assert.assertEquals(activityDao.getRowCount(), 1)
    }

    //ACTIVITYUNIT TESTS

    @Test
    @Throws(Exception::class)
    fun insertAndGetActivityUnit() {
        val activityUnit = DatabaseActivityUnit(id = "1")
        activityUnitDao.insertItem(activityUnit)
        val dbactivityUnit = activityUnitDao.getAllActivityUnits().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).blockingGet().last()
        Assert.assertEquals(activityUnit.id, dbactivityUnit.id)
    }

    @Test
    @Throws(Exception::class)
    fun insertManyActivityUnits() {
        val activityUnit1 = DatabaseActivityUnit(id = "2")
        val activityUnit2 = DatabaseActivityUnit(id = "3")
        activityUnitDao.insertItem(activityUnit1)
        activityUnitDao.insertItem(activityUnit2)
        Assert.assertEquals(activityUnitDao.getRowCount(), 2)
    }

    @Test
    @Throws(Exception::class)
    fun deleteActivityUnit() {
        val activityUnit1 = DatabaseActivityUnit(id = "4")
        val activityUnit2 = DatabaseActivityUnit(id = "5")
        activityUnitDao.insertItem(activityUnit1)
        activityUnitDao.insertItem(activityUnit2)
        activityUnitDao.delete(activityUnit1)
        Assert.assertEquals(activityUnitDao.getRowCount(), 1)
    }

}