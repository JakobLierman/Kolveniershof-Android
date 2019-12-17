package be.hogent.kolveniershof.repository

import androidx.lifecycle.LiveData
import be.hogent.kolveniershof.api.KolvApi
import be.hogent.kolveniershof.database.DAO.BusUnitDao
import be.hogent.kolveniershof.database.DAO.UserDao
import be.hogent.kolveniershof.database.DAO.WorkdayDao
import be.hogent.kolveniershof.database.DAO.WorkdayUserJOINDao
import be.hogent.kolveniershof.database.databaseModels.DatabaseBus
import be.hogent.kolveniershof.database.databaseModels.DatabaseUser
import be.hogent.kolveniershof.database.databaseModels.DatabaseWorkday
import be.hogent.kolveniershof.domain.User
import be.hogent.kolveniershof.domain.Workday
import be.hogent.kolveniershof.network.NetworkUser
import com.orhanobut.logger.Logger
import retrofit2.HttpException
import java.util.*
import javax.security.auth.login.LoginException

class WorkdayRepository(private val kolvApi: KolvApi, val userDao: UserDao, val workdayDao: WorkdayDao, val workdayUserJOINDao: WorkdayUserJOINDao, val busUnitDao : BusUnitDao) : BaseRepo() {

    fun getUser(email: String): User {

        // check if database is empty
        if (userDao.getRowCount() <= 0) {
            if (isConnected()) {
                var tempUser: User? = null
                kolvApi.getUserByEmail(email)
                    .subscribe { user -> tempUser = NetworkUser.asDomainModel(user) }
                return tempUser!!
            } else {
                return DatabaseUser.toUser(userDao.getUSerByEmail(email).value!!)
            }
        } else {
            return DatabaseUser.toUser(userDao.getUSerByEmail(email).value!!)
        }
    }

    fun login(email: String, password: String): User {
        try {
            return NetworkUser.asDomainModel(kolvApi.login(email, password)
                .doOnError { error -> onRetrieveError(error) }
                .blockingGet())

        } catch (e: Exception) {
            throw LoginException((e as HttpException).response()!!.errorBody()!!.string())
        }
    }

    private fun onRetrieveError(error: Throwable) {
        Logger.e(error.message!!)
    }

    private fun databaseWorkdayToWrokday(dbWorkday : DatabaseWorkday) : Workday {
        val daycareMentors =  workdayUserJOINDao.getUsersFromWorkday(dbWorkday.id).value!!.map { user -> DatabaseUser.toUser(user) }.toMutableList()
        val workdayBusses = busUnitDao.getBusUnitsFromWorkday(dbWorkday.id).value!!.map { bus -> DatabaseBus}


        return Workday(
            id = dbWorkday.id,
            date = Date(dbWorkday.date),
            daycareMentors = daycareMentors,
            morningBusses = busUnitDao.
            )



    }
}