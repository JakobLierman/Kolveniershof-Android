package be.hogent.kolveniershof.repository

import android.content.Context
import android.net.ConnectivityManager

abstract class BaseRepo(val context: Context) {



    /**
     * Check whether the phone has internet connectivity
     */
    protected fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        return networkInfo != null && networkInfo.isConnected
    }
}