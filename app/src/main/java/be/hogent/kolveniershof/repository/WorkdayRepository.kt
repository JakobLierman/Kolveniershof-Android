package be.hogent.kolveniershof.repository

import androidx.lifecycle.LiveData
import be.hogent.kolveniershof.api.KolvApi
import be.hogent.kolveniershof.database.DAO.*
import be.hogent.kolveniershof.database.databaseModels.DatabaseBus
import be.hogent.kolveniershof.database.databaseModels.DatabaseLunchUnit
import be.hogent.kolveniershof.database.databaseModels.DatabaseUser
import be.hogent.kolveniershof.database.databaseModels.DatabaseWorkday
import be.hogent.kolveniershof.domain.Comment
import be.hogent.kolveniershof.domain.LunchUnit
import be.hogent.kolveniershof.domain.User
import be.hogent.kolveniershof.domain.Workday
import be.hogent.kolveniershof.network.NetworkUser
import com.orhanobut.logger.Logger
import retrofit2.HttpException
import java.util.*
import javax.security.auth.login.LoginException

class WorkdayRepository(private val kolvApi: KolvApi, val userDao: UserDao, val workdayDao: WorkdayDao, val workdayUserJOINDao: WorkdayUserJOINDao, val busUnitDao : BusUnitDao, val busRepository: BusRepository, val activityRepository: ActivityRepository, val lunchUnitDao: LunchUnitDao) : BaseRepo() {

    fun getWorkdays(): MutableList<Workday> {
        return workdayDao.getAllWorkdays().value!!.map { wd -> databaseWorkdayToWrokday(wd) }.toMutableList()
    }

    private fun databaseWorkdayToWrokday(dbWorkday : DatabaseWorkday) : Workday {
        val daycareMentors =  workdayUserJOINDao.getUsersFromWorkday(dbWorkday.id).value!!.map { user -> DatabaseUser.toUser(user) }.toMutableList()
        val morningBusses = busUnitDao.getBusUnitsFromWorkday(dbWorkday.id).value!!.filter { bus -> bus.isAfternoon == false } .map { bus -> busRepository.databaseBusUnitToBusUnit(bus)}.toMutableList()
        val eveningBusses = busUnitDao.getBusUnitsFromWorkday(dbWorkday.id).value!!.filter { bus -> bus.isAfternoon == true } .map { bus -> busRepository.databaseBusUnitToBusUnit(bus)}.toMutableList()
        val amActivities = activityRepository.getAmActivitiesFromWorkday(dbWorkday.id)
        val pmActivities = activityRepository.getPmActivitiesFromWorkday(dbWorkday.id)

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
            comments = mutableListOf<Comment>()

            )



    }
}