package be.hogent.kolveniershof.domain

import android.annotation.SuppressLint

data class Group(
    val id: String,
    val name: String,
    val members: MutableList<User>
) {
    @SuppressLint("DefaultLocale")
    override fun toString(): String {
        return name.trim().capitalize()
    }
}