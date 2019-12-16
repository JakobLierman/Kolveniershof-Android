package be.hogent.kolveniershof.repository

import android.content.Context
import android.net.ConnectivityManager
import be.hogent.kolveniershof.api.KolvApi
import be.hogent.kolveniershof.database.DAO.UserDao
import be.hogent.kolveniershof.database.databaseModels.DatabaseUser
import be.hogent.kolveniershof.domain.User
import be.hogent.kolveniershof.network.NetworkUser
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserRepository(private val kolvApi: KolvApi, val userDao: UserDao) {

    //TODO: Injection

    lateinit var context: Context

    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        return networkInfo != null && networkInfo.isConnected
    }

    fun getUser(email : String): User {

        // check if database is empty
        if (userDao.getRowCount() <= 0) {
            if(isConnected()){
                var tempUser : User? = null
                kolvApi.getUserByEmail(email).subscribe { user -> tempUser = NetworkUser.asDomainModel(user)}
                return tempUser!!
            }else {
                return DatabaseUser.toUser(userDao.getUSerByEmail(email).value!!)
            }
        } else {
             return DatabaseUser.toUser(userDao.getUSerByEmail(email).value!!)
        }
    }
}