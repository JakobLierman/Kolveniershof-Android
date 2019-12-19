package be.hogent.kolveniershof.repository

import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import be.hogent.kolveniershof.api.KolvApi
import be.hogent.kolveniershof.database.DAO.UserDao
import be.hogent.kolveniershof.database.databaseModels.DatabaseUser
import be.hogent.kolveniershof.domain.User
import be.hogent.kolveniershof.network.NetworkUser
import com.orhanobut.logger.Logger
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import javax.security.auth.login.LoginException

class UserRepository(private val kolvApi: KolvApi, val userDao: UserDao) : BaseRepo() {

    fun getUser(email : String): User {

        // check if database is empty
        if (userDao.getRowCount() <= 0) {
            if(isConnected()){
                var tempUser : User? = null
                kolvApi.getUserByEmail(email).subscribe { user -> tempUser = NetworkUser.asDomainModel(user)}
                return tempUser!!
            }else {
                return DatabaseUser.toUser(userDao.getUSerByEmail(email).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).blockingGet())
            }
        } else {
             return DatabaseUser.toUser(userDao.getUSerByEmail(email).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).blockingGet())
        }
    }

    fun getUserById(id : String): User {

        // check if database is empty
        if (userDao.getRowCount() <= 0) {
            if(isConnected()){
                var tempUser : User? = null
                kolvApi.getUserById(id).subscribe { user -> tempUser = NetworkUser.asDomainModel(user)}
                return tempUser!!
            }else {
                return DatabaseUser.toUser(userDao.getUSerById(id).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).blockingGet())
            }
        } else {
            return DatabaseUser.toUser(userDao.getUSerById(id).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).blockingGet())
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
}