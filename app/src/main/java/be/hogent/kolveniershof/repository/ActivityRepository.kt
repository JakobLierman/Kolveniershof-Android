package be.hogent.kolveniershof.repository

import be.hogent.kolveniershof.api.KolvApi
import be.hogent.kolveniershof.database.DAO.ActivityDao
import be.hogent.kolveniershof.database.DAO.ActivityUnitDao
import be.hogent.kolveniershof.database.DAO.ActivityUnitUserJOINDao
import be.hogent.kolveniershof.database.databaseModels.DatabaseActivity
import be.hogent.kolveniershof.database.databaseModels.DatabaseActivityUnit
import be.hogent.kolveniershof.database.databaseModels.DatabaseUser
import be.hogent.kolveniershof.domain.Activity
import be.hogent.kolveniershof.domain.ActivityUnit
import be.hogent.kolveniershof.network.NetworkActivity
import be.hogent.kolveniershof.network.NetworkActivityUnit
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ActivityRepository (val kolvApi: KolvApi, val activityUnitDao: ActivityUnitDao, val activityDao: ActivityDao, val activityUnitUserJOINDao: ActivityUnitUserJOINDao) : BaseRepo() {

    fun databaseActivityUnitToActivityUnit (dbActivity: DatabaseActivityUnit) : ActivityUnit {

        val mentors = activityUnitUserJOINDao.getUsersFromActivity(dbActivity.id).observeOn(
            AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).blockingGet().map { user ->  DatabaseUser.toUser(user)}.filter { user -> user.isAdmin == true }.toMutableList()
        val clients = activityUnitUserJOINDao.getUsersFromActivity(dbActivity.id).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).blockingGet().map { user ->  DatabaseUser.toUser(user)}.filter { user -> user.isAdmin == false }.toMutableList()
        return ActivityUnit(
            id = dbActivity.id,
            activity = getActivityById(dbActivity.activityId),
            mentors = mentors
        )
    }

   fun getActivityById (id: String) : Activity {
        return DatabaseActivity.toActivity(activityDao.getActivityById(id).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).blockingGet())
    }

    fun getAmActivitiesFromWorkday(workdayId: String) : MutableList<ActivityUnit> {

        return activityUnitDao.getAmActivitiesFromWorkday(workdayId).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).blockingGet().map { activity -> databaseActivityUnitToActivityUnit(activity) }.toMutableList()
    }

    fun getPmActivitiesFromWorkday(workdayId: String) : MutableList<ActivityUnit> {
        return activityUnitDao.getPmActivitiesFromWorkday(workdayId).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).blockingGet().map { activity -> databaseActivityUnitToActivityUnit(activity) }.toMutableList()
    }

    fun getDayActivitiesFromWorkday(workdayId: String) : MutableList<ActivityUnit> {
        return activityUnitDao.getDayActivitiesFromWorkday(workdayId).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).blockingGet().map { activity -> databaseActivityUnitToActivityUnit(activity) }.toMutableList()
    }

    fun addAmActivities(activities : MutableList<ActivityUnit>, wdId: String) {
        val dbActivities = activities.map { a ->  activityUnitToDatabaseActivityUnit(a, wdId) }
        activityUnitDao.insertItems(dbActivities)
    }

    fun activityUnitToDatabaseActivityUnit (unit : ActivityUnit, wdId: String) : DatabaseActivityUnit {

        val dbActivityUnit = DatabaseActivityUnit(
            id = unit.id,
            workdayId = wdId,
            activityId = unit.activity.id,
            isAm = true,
            isPm = false,
            isDay = false
        )
        addActivity(unit.activity)

        return dbActivityUnit
    }

    fun addActivity(activity: Activity) {
        activityDao.insertItem(activityToDatabaseActivity(activity))
    }

    fun activityToDatabaseActivity(activity: Activity) : DatabaseActivity {
        return DatabaseActivity(
            activityId = activity.id,
            name = activity.name,
            icon = activity.icon
        )
    }

}