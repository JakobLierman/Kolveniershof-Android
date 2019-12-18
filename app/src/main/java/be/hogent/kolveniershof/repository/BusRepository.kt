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

class BusRepository (val kolvApi: KolvApi, val busUnitDao: BusUnitDao, val busDao: BusDao, val userRepository: UserRepository, val busUnitUserJOINDao: BusUnitUserJOINDao) : BaseRepo() {

    //TODO: uitzoeken of best instance van userRepo of userDao

    fun databaseBusUnitToBusUnit(dbBusUnit : DatabaseBusUnit) : BusUnit {

        val mentors = busUnitUserJOINDao.getMentorsFromBus(dbBusUnit.id).value!!.map { user ->  DatabaseUser.toUser(user)}.toMutableList()
        val clients = busUnitUserJOINDao.getClientsFromBus(dbBusUnit.id).value!!.map { user ->  DatabaseUser.toUser(user)}.toMutableList()

        return BusUnit(
            id = dbBusUnit.id,
            bus= getBusById(dbBusUnit.busId),
            mentors=  mentors
        )
    }

    fun getBusById(id: String) : Bus {
        return databaseBusToBus(busDao.getBusById(id).value!!)
    }

    fun databaseBusToBus(dbBus : DatabaseBus) : Bus {
        return  Bus(
            id = dbBus.id,
            name= dbBus.name,
            color = dbBus.color
        )
    }

}