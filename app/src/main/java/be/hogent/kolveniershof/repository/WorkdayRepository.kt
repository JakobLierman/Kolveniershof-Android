package be.hogent.kolveniershof.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import be.hogent.kolveniershof.api.KolvApi
import be.hogent.kolveniershof.database.DAO.*
import be.hogent.kolveniershof.database.databaseModels.DatabaseLunchUnit
import be.hogent.kolveniershof.database.databaseModels.DatabaseUser
import be.hogent.kolveniershof.database.databaseModels.DatabaseWorkday
import be.hogent.kolveniershof.domain.Comment
import be.hogent.kolveniershof.domain.Workday
import be.hogent.kolveniershof.network.NetworkWorkday
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class WorkdayRepository(private val kolvApi: KolvApi, val userDao: UserDao, val workdayDao: WorkdayDao, val workdayUserJOINDao: WorkdayUserJOINDao, val busUnitDao : BusUnitDao, val busRepository: BusRepository, val activityRepository: ActivityRepository, val lunchUnitDao: LunchUnitDao) : BaseRepo() {

    fun getWorkdays(authToken:String): LiveData<MutableList<Workday>> {
        //Check if empty, if true --> check if connected and get directly from API
        if( workdayDao.getRowCount() <= 0 && isConnected()){
            val workdaysList = ArrayList<Workday>()
             kolvApi.getWorkdays(authToken).subscribe{
                it.forEach {
                   workdaysList.add(networkWorkdayToWorkday(it))
                }
            }
            workdayDao.insertItems(workdaysList.map { wd ->  workdayToDatabaseWorkday(wd)})
        }
        return Transformations.map(
            workdayDao.getAllWorkdays(),
            {list -> list.map { l -> databaseWorkdayToWorkday(l) }.toMutableList()}
        )
    }

    fun getWorkdayById(authToken: String,id: String): LiveData<Workday> {
        val wd = workdayDao.getWorkdayById(id)
        if(wd.value == null && isConnected()){
            val netWorkday =  kolvApi.getWorkdayById(authToken,id).blockingFirst()
            val liveDataWorkday = MutableLiveData<Workday>()
            liveDataWorkday.value = networkWorkdayToWorkday(netWorkday)
            return liveDataWorkday
        }
        return Transformations.map(wd, {dbWorkday -> databaseWorkdayToWorkday(dbWorkday)})
    }

    private fun databaseWorkdayToWorkday(dbWorkday : DatabaseWorkday) : Workday {
        val daycareMentors =  workdayUserJOINDao.getUsersFromWorkday(dbWorkday.id).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).blockingGet().map { user -> DatabaseUser.toUser(user) }.toMutableList()
        val morningBusses = busUnitDao.getBusUnitsFromWorkday(dbWorkday.id).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).blockingGet().filter { bus -> bus.isAfternoon == false } .map { bus -> busRepository.databaseBusUnitToBusUnit(bus)}.toMutableList()
        val eveningBusses = busUnitDao.getBusUnitsFromWorkday(dbWorkday.id).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).blockingGet().filter { bus -> bus.isAfternoon == true } .map { bus -> busRepository.databaseBusUnitToBusUnit(bus)}.toMutableList()
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
            lunch = DatabaseLunchUnit.databaseLunchUnitToLunchUnit(lunchUnitDao.getLunchFromWorkday(dbWorkday.id).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).blockingGet()),
            pmActivities = pmActivities,
            isHoliday = dbWorkday.isHoliday,
            comments = mutableListOf<Comment>(),
            dayActivities = dayActivities

            )
    }

    private fun workdayToDatabaseWorkday(workday : Workday) : DatabaseWorkday {

        val dbWorkday =  DatabaseWorkday(
            id = workday.id,
            date = workday.date.toString(),
            isHoliday = workday.isHoliday
        )

        activityRepository.addAmActivities(workday.amActivities, workday.id)
        




        return dbWorkday
    }

    private fun networkWorkdayToWorkday(netWorkday : NetworkWorkday) : Workday {

        return Workday(
            id = netWorkday.id,
            date = netWorkday.date,
            daycareMentors = netWorkday.daycareMentors,
            morningBusses = netWorkday.morningBusses,
            eveningBusses = netWorkday.eveningBusses,
            amActivities = netWorkday.amActivities,
            lunch = netWorkday.lunch,
            pmActivities = netWorkday.pmActivities,
            isHoliday = netWorkday.isHoliday,
            comments = netWorkday.comments,
            dayActivities = netWorkday.dayActivities
        )
    }
}