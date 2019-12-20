package be.hogent.kolveniershof.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import be.hogent.kolveniershof.api.KolvApi
import be.hogent.kolveniershof.database.DAO.LunchUnitDao
import be.hogent.kolveniershof.database.DAO.WorkdayDao
import be.hogent.kolveniershof.database.DAO.WorkdayUserJOINDao
import be.hogent.kolveniershof.database.databaseModels.DatabaseLunchUnit
import be.hogent.kolveniershof.database.databaseModels.DatabaseWorkday
import be.hogent.kolveniershof.domain.Comment
import be.hogent.kolveniershof.domain.Workday
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.sql.Date
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class WorkdayRepository(private val kolvApi: KolvApi, val workdayDao: WorkdayDao, val workdayUserJOINDao: WorkdayUserJOINDao, val busRepository: BusRepository, val activityRepository: ActivityRepository, val lunchUnitDao: LunchUnitDao, val c: Context) : BaseRepo(c) {



    fun getWorkdays(authToken:String): List<DatabaseWorkday> {
        checkDatabaseWorkdays(authToken)
        return workdayDao.getAllWorkdays()
    }

    fun getWorkdayById(authToken: String,id: String): LiveData<Workday> {
        checkDatabaseWorkdays(authToken)
        return Transformations.map(workdayDao.getWorkdayById(id), {dbWorkday -> databaseWorkdayToWorkday(dbWorkday)})
    }

    fun getWorkdayByDateByUser(authToken: String, date: String, userId: String):LiveData<Workday>{
        checkDatabaseWorkdays(authToken)
        val time = DateTimeFormat.forPattern("dd_MM_yyyy").withLocale(Locale.getDefault())

        Log.i("datum", DateTime.parse(date, time).toString())
        val dateFormatted = time.parseDateTime(date).toDate().toString()

        return Transformations.map(workdayDao.getByWorkdateByDate(dateFormatted)) { dbWorkday : DatabaseWorkday? -> run {
            if (dbWorkday == null) {
                null
            }else {
                databaseWorkdayToWorkday(dbWorkday)
            }
        }}
    }

    private fun checkDatabaseWorkdays(authToken: String){
        //Check if empty, if true --> check if connected and get directly from API

        val workdaysList : List<Workday>
        if( workdayDao.getRowCount() <= 0 && isConnected()){
            workdaysList = kolvApi.getWorkdays(authToken).subscribeOn(Schedulers.io()).blockingSingle()
           workdaysList.forEach { wd -> saveWorkdayToDatabase(wd) }
        }
    }

    private fun databaseWorkdayToWorkday(dbWorkday : DatabaseWorkday) : Workday {
        val morningBusses = busRepository.getBusUnitFromWorkday(dbWorkday.id, false).map{ bus -> busRepository.databaseBusUnitToBusUnit(bus)}.toMutableList()
        val eveningBusses = busRepository.getBusUnitFromWorkday(dbWorkday.id, true).map{ bus -> busRepository.databaseBusUnitToBusUnit(bus)}.toMutableList()
        val amActivities = activityRepository.getAmActivitiesFromWorkday(dbWorkday.id).map{ activity -> activityRepository.databaseActivityUnitToActivityUnit(activity)}.toMutableList()
        val pmActivities = activityRepository.getPmActivitiesFromWorkday(dbWorkday.id).map{ activity -> activityRepository.databaseActivityUnitToActivityUnit(activity)}.toMutableList()
        val dayActivities = activityRepository.getDayActivitiesFromWorkday(dbWorkday.id).map{ activity -> activityRepository.databaseActivityUnitToActivityUnit(activity)}.toMutableList()



        return Workday(
            id = dbWorkday.id,
            date = Date.valueOf(dbWorkday.date),
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

    private fun saveWorkdayToDatabase(workday : Workday) : DatabaseWorkday {

        val dbWorkday =  DatabaseWorkday(
            id = workday.id,
            date = workday.date.toString(),
            isHoliday = workday.isHoliday
        )


        workdayDao.insertItem(dbWorkday)

        activityRepository.addAmActivities(workday.amActivities, workday.id)
        activityRepository.addPmActivities(workday.pmActivities,workday.id)
        activityRepository.addDayActivities(workday.dayActivities,workday.id)
        busRepository.addBusses(workday.morningBusses,workday.id, true)
        busRepository.addBusses(workday.eveningBusses,workday.id, false)
        if(workday.lunch != null){
        lunchUnitDao.insertItem(DatabaseLunchUnit(
            id = workday.lunch.id,
            workdayId = workday.id,
            luch = workday.lunch.lunch
            ))
        }





        return dbWorkday
    }
}