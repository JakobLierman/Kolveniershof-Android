package be.hogent.kolveniershof.domain

import android.annotation.SuppressLint
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class LunchUnit(
    @field:Json(name = "_id")
    val id: String,
    @field:Json(name  = "lunch")
    val lunch: String
){
    @SuppressLint("DefaultLocale")
    override fun toString(): String {
        return lunch.trim().capitalize()
    }
}