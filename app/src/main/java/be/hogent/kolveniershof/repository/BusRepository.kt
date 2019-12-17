package be.hogent.kolveniershof.repository

import be.hogent.kolveniershof.api.KolvApi
import be.hogent.kolveniershof.database.DAO.BusUnitDao

class BusRepository (val kolvApi: KolvApi, val busUnitDao: BusUnitDao) : BaseRepo() {

}