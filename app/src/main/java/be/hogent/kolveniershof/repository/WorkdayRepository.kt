package be.hogent.kolveniershof.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import be.hogent.kolveniershof.api.KolvApi
import be.hogent.kolveniershof.database.DAO.*
import be.hogent.kolveniershof.database.databaseModels.DatabaseLunchUnit
import be.hogent.kolveniershof.database.databaseModels.DatabaseUser
import be.hogent.kolveniershof.database.databaseModels.DatabaseWorkday
import be.hogent.kolveniershof.domain.Comment
import be.hogent.kolveniershof.domain.Workday
import java.util.*

class WorkdayRepository(private val kolvApi: KolvApi, val userDao: UserDao, val workdayDao: WorkdayDao, val workdayUserJOINDao: WorkdayUserJOINDao, val busUnitDao : BusUnitDao, val busRepository: BusRepository, val activityRepository: ActivityRepository, val lunchUnitDao: LunchUnitDao) : BaseRepo() {

    fun getWorkdays(): LiveData<MutableList<Workday>> {

        val wds = workdayDao.getAllWorkdays()
        return Transformations.map(
            wds,
            {list -> list.map { l -> databaseWorkdayToWorkday(l) }.toMutableList()}
        )
    }

    fun getWorkdayById(id: String): LiveData<Workday> {
        val wd = workdayDao.getWorkdayById(id)
        return Transformations.map(wd, {dbWorkday -> databaseWorkdayToWorkday(dbWorkday)})
    }

    private fun databaseWorkdayToWorkday(dbWorkday : DatabaseWorkday) : Workday {
        val daycareMentors =  workdayUserJOINDao.getUsersFromWorkday(dbWorkday.id).value!!.map { user -> DatabaseUser.toUser(user) }.toMutableList()
        val morningBusses = busUnitDao.getBusUnitsFromWorkday(dbWorkday.id).value!!.filter { bus -> bus.isAfternoon == false } .map { bus -> busRepository.databaseBusUnitToBusUnit(bus)}.toMutableList()
        val eveningBusses = busUnitDao.getBusUnitsFromWorkday(dbWorkday.id).value!!.filter { bus -> bus.isAfternoon == true } .map { bus -> busRepository.databaseBusUnitToBusUnit(bus)}.toMutableList()
        val amActivities = activityRepository.getAmActivitiesFromWorkday(dbWorkday.id)
        val pmActivities = activityRepository.getPmActivitiesFromWorkday(dbWorkday.id)
        val dayActivities = activityRepository.getDayActivitiesFromWorkday(dbWorkday.id)

        return Workday(
            id = dbWorkday.id,
            date = Date(dbWorkday.date),
            daycareMentors = daycareMentors,
            morningBusses = morningBusses,
            eveningBusses = eveningBusses,
            amActivities = amActivities,
            lunch = DatabaseLunchUnit.databaseLunchUnitToLunchUnit(lunchUnitDao.getLunchFromWorkday(dbWorkday.id).value!!),
            pmActivities = pmActivities,
            isHoliday = dbWorkday.isHoliday,
            comments = mutableListOf<Comment>(),
            dayActivities = dayActivities

            )
    }
}