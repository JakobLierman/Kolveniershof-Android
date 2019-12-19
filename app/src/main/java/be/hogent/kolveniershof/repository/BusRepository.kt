package be.hogent.kolveniershof.repository

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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class BusRepository (val kolvApi: KolvApi, val busUnitDao: BusUnitDao, val busDao: BusDao, val userRepository: UserRepository, val busUnitUserJOINDao: BusUnitUserJOINDao) : BaseRepo() {

    //TODO: uitzoeken of best instance van userRepo of userDao

    fun databaseBusUnitToBusUnit(dbBusUnit : DatabaseBusUnit) : BusUnit {

        val mentors = busUnitUserJOINDao.getMentorsFromBus(dbBusUnit.id).observeOn(AndroidSchedulers.mainThread()).subscribeOn(
            Schedulers.io()).blockingGet().map { user ->  DatabaseUser.toUser(user)}.toMutableList()
        val clients = busUnitUserJOINDao.getClientsFromBus(dbBusUnit.id).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).blockingGet().map { user ->  DatabaseUser.toUser(user)}.toMutableList()

        return BusUnit(
            id = dbBusUnit.id,
            bus= getBusById(dbBusUnit.busId),
            mentors=  mentors
        )
    }

    fun getBusById(id: String) : Bus {
        if(isConnected()){
            var tempBus : Bus? = null
            kolvApi.getBusById(id).subscribe{
                    bus -> tempBus = NetworkBus.asDomainModel(bus)
            }
            return tempBus!!
        } else
        return databaseBusToBus(busDao.getBusById(id).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).blockingGet())
    }
    fun addBusses(busses: MutableList<BusUnit>, workdayId:String){
        val dbBusses = busses.map { bus -> busUnitToDatabaseUnit(bus,workdayId) }
    }
    private fun busUnitToDatabaseUnit(unit : BusUnit,workdayId : String): DatabaseBusUnit{
        val dbBusUnit = DatabaseBusUnit(
            id = unit.id,
            busId = unit.bus.id,
            workdayId = workdayId,
            isAfternoon = true
        )
        addBus(unit.bus)
        return dbBusUnit
    }
    private fun addBus(bus:Bus){
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
    fun getBusUnitFromWorkday(dbWorkdayId:String, isAfternoon:Boolean): MutableList<BusUnit>{
       return busUnitDao.getBusUnitsFromWorkday(dbWorkdayId).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).blockingGet().filter { bus -> bus.isAfternoon == isAfternoon } .map { bus -> databaseBusUnitToBusUnit(bus)}.toMutableList()
    }
}