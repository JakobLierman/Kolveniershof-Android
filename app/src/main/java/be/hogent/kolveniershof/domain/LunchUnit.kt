package be.hogent.kolveniershof.domain

import android.annotation.SuppressLint

data class LunchUnit(
    val id: String,
    val lunch: String
) {
    @SuppressLint("DefaultLocale")
    override fun toString(): String {
        return lunch.trim().capitalize()
    }
}