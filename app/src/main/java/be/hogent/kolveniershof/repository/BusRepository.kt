package be.hogent.kolveniershof.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import be.hogent.kolveniershof.api.KolvApi
import be.hogent.kolveniershof.database.DAO.BusDao
import be.hogent.kolveniershof.database.DAO.BusUnitDao
import be.hogent.kolveniershof.database.DAO.BusUnitUserJOINDao
import be.hogent.kolveniershof.database.databaseModels.DatabaseBus
import be.hogent.kolveniershof.database.databaseModels.DatabaseBusUnit
import be.hogent.kolveniershof.database.databaseModels.DatabaseUser
import be.hogent.kolveniershof.domain.Bus
import be.hogent.kolveniershof.domain.BusUnit
import be.hogent.kolveniershof.network.NetworkBus
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class BusRepository (val kolvApi: KolvApi, val busUnitDao: BusUnitDao, val busDao: BusDao, val userRepository: UserRepository, val busUnitUserJOINDao: BusUnitUserJOINDao, val c: Context) : BaseRepo(c) {

    //TODO: uitzoeken of best instance van userRepo of userDao

    fun databaseBusUnitToBusUnit(dbBusUnit : DatabaseBusUnit) : BusUnit {

        val mentors = busUnitUserJOINDao.getMentorsFromBus(dbBusUnit.id).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).map { users ->  run {
            val userss = users.map{ user -> DatabaseUser.toUser(user) }
            userss
        } }.blockingGet().toMutableList()

        return BusUnit(
            id = dbBusUnit.id,
            bus= getBusById(dbBusUnit.busId),
            mentors=  mentors
        )
    }

    fun getBusById(id: String) : Bus {

        return databaseBusToBus(busDao.getBusById(id))
    }
    fun addBusses(busses: MutableList<BusUnit>, workdayId:String, isMorning: Boolean){
        val dbBusses = busses.map { bus -> busUnitToDatabaseUnit(bus,workdayId, isMorning) }
        busUnitDao.insertItems(dbBusses)
    }
    private fun busUnitToDatabaseUnit(unit : BusUnit,workdayId : String, isMorning: Boolean): DatabaseBusUnit{
        val dbBusUnit = DatabaseBusUnit(
            id = unit.id,
            busId = unit.bus.id,
            workdayId = workdayId,
            isAfternoon = !isMorning
        )

        addBus(unit.bus)
        Log.i("busses", dbBusUnit.toString())
        return dbBusUnit
    }
    private fun addBus(bus:Bus){
        Log.i("busses", bus.id.toString())
        busDao.insertItem(busToDatabaseBus(bus))
    }

    private fun busToDatabaseBus(bus: Bus): DatabaseBus{
        return DatabaseBus(
            id = bus.id,
            name = bus.name,
            color = bus.color
        )
    }
    fun databaseBusToBus(dbBus : DatabaseBus) : Bus {
        return  Bus(
            id = dbBus.id,
            name= dbBus.name,
            color = dbBus.color
        )
    }
    fun getBusUnitFromWorkday(dbWorkdayId:String, isAfternoon:Boolean): List<DatabaseBusUnit> {
       val test =  busUnitDao.getBusUnitsFromWorkday(dbWorkdayId)
        return test
    }
}