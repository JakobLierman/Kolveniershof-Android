package be.hogent.kolveniershof.util

import android.content.SharedPreferences

enum class SharedPreferencesEnum(val string: String) {
        PREFNAME("USER_CREDENTIALS"),
        ID("ID"),
        EMAIL("EMAIL"),
        FIRSTNAME("FIRSTNAME"),
        LASTNAME("LASTNAME"),
        IMGURL("IMGURL"),
        ADMIN("ADMIN"),
        TOKEN("TOKEN"),
        ISLOGGEDIN("ISLOGGEDIN")
    }

class SharedPreferences constructor(private val mSharedPreferences: SharedPreferences) {

    fun putInt(enum: SharedPreferencesEnum, data: Int) {
        mSharedPreferences.edit().putInt(enum.string, data).apply()
    }

    fun getInt(enum: SharedPreferencesEnum): Int {
        return mSharedPreferences.getInt(enum.string, 0)
    }

    fun putString(enum: SharedPreferencesEnum, data: String?) {
        mSharedPreferences.edit().putString(enum.string, data).apply()
    }

    fun getString(enum: SharedPreferencesEnum): String? {
        return mSharedPreferences.getString(enum.string, "")
    }

    fun putBoolean(enum: SharedPreferencesEnum, data: Boolean) {
        mSharedPreferences.edit().putBoolean(enum.string, data).apply()
    }

    fun getBoolean(enum: SharedPreferencesEnum): Boolean {
        return mSharedPreferences.getBoolean(enum.string, false)
    }

}