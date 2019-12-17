package be.hogent.kolveniershof.repository

import be.hogent.kolveniershof.api.KolvApi
import be.hogent.kolveniershof.database.DAO.ActivityUnitDao

class ActivityRepository (val kolvApi: KolvApi, val activityUnitDao: ActivityUnitDao) : BaseRepo() {
}