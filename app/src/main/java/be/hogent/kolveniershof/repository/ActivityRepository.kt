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

class ActivityRepository (val kolvApi: KolvApi, val activityUnitDao: ActivityUnitDao, val activityDao: ActivityDao, val activityUnitUserJOINDao: ActivityUnitUserJOINDao) : BaseRepo() {

    fun databaseActivityUnitToActivityUnit (dbActivity: DatabaseActivityUnit) : ActivityUnit {

        val mentors = activityUnitUserJOINDao.getUsersFromActivity(dbActivity.id).value!!.map { user ->  DatabaseUser.toUser(user)}.filter { user -> user.isAdmin == true }.toMutableList()
        val clients = activityUnitUserJOINDao.getUsersFromActivity(dbActivity.id).value!!.map { user ->  DatabaseUser.toUser(user)}.filter { user -> user.isAdmin == false }.toMutableList()
        return ActivityUnit(
            id = dbActivity.id,
            activity = getActivityById(dbActivity.activityId),
            mentors = mentors,
            clients = clients
        )
    }

    fun getActivityById (id: String) : Activity {
        return DatabaseActivity.toActivity(activityDao.getActivityById(id).value!!)
    }

    fun getAmActivitiesFromWorkday(workdayId: String) : MutableList<ActivityUnit> {
        return activityUnitDao.getAmActivitiesFromWorkday(workdayId).value!!.map { activity -> databaseActivityUnitToActivityUnit(activity) }.toMutableList()
    }

    fun getPmActivitiesFromWorkday(workdayId: String) : MutableList<ActivityUnit> {
        return activityUnitDao.getAmActivitiesFromWorkday(workdayId).value!!.map { activity -> databaseActivityUnitToActivityUnit(activity) }.toMutableList()
    }

}