package be.hogent.kolveniershof.domain

import java.util.*

data class User(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String? = null,
    val isAdmin: Boolean = false,
    val birthday: Date,
    val absentDates: MutableList<Date> = mutableListOf(),
    val imgUrl: String? = null,
    val token: String? = null
)