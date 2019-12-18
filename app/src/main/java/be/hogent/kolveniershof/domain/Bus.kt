package be.hogent.kolveniershof.domain

import android.annotation.SuppressLint

data class Bus(
    val id: String,
    val name: String,
    val color: String
) {
    @SuppressLint("DefaultLocale")
    override fun toString(): String {
        return name.trim().capitalize()
    }
}