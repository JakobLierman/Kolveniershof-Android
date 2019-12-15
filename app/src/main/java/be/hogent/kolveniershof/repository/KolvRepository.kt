package be.hogent.kolveniershof.repository

import androidx.lifecycle.LiveData
import be.hogent.kolveniershof.api.KolvApi

class KolvRepository(private val kolvApi: KolvApi/*, private val kolvDao: KolvDao*/) {
/*
    val userLogin: LiveData<List<User>> =
        Transformations.map(kolvDao.getAllUsers()) {
            it.asDomainModel()
        }

    //--Workdays---------------------------------------------------------------

    val workdays: LiveData<List<Workday>> =
        Transformations.map(kolvDao.getAllWorkdays()) {
            it.asDomainModel()
        }

    suspend fun refreshWorkdays(authToken: String) {
        withContext(Dispatchers.IO) {
            val workdays = NetworkModule.provideRetrofitInterface().create(KolvApi::class.java)
                .getWorkdays(authToken).await()
            kolvDao.insertAllWorkdays(*workdays.asDatabaseModel())
        }
    }

    //--WorkdaysByDateByUser---------------------------------------------------------------

    fun getWorkdayByDateByUser(dateString: String): LiveData<WorkdayByDateByUser> {
        val arrayDate = dateString.split("_")
        /*dd_MM_yyyy*/
        val date = Date(arrayDate[2].toInt(), arrayDate[1].toInt(), arrayDate[0].toInt())
        return Transformations.map(kolvDao.getWorkdayByDateByUser(date)) {
            it.asDomainModel()
        }
    }

    /*=var workdayByDateByUser: LiveData<WorkdayByDateByUser> =
        Transformations.map(database.kolveniershofDatabaseDao.getWorkdayByDateByUser(date)) {
            it.asDomainModel()
        }*/

    suspend fun refreshWorkdayByDateByUser(authToken: String, dateString: String, id: String) {
        withContext(Dispatchers.IO) {
            val workdayByDateByUser = NetworkModule.provideRetrofitInterface()
                .create(KolvApi::class.java).getWorkdayByDateByUser(authToken, dateString, id)
                .await()
            kolvDao.insertWorkdayByDateByUser(workdayByDateByUser.asDatabaseModel())
        }
    }

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: KolveniershofRepository? = null

        fun getInstance(kolvDao: KolveniershofDatabaseDao) =
            instance ?: synchronized(this) {
                instance ?: KolveniershofRepository(kolvDao).also { instance = it }
            }
    }
}*/


}