package be.hogent.kolveniershof.domain

import android.annotation.SuppressLint
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Bus(
    @field:Json(name = "_id")
    val id: String,
    @field:Json(name  = "name")
    val name: String,
    @field:Json(name = "color")
    val color: String
){
    @SuppressLint("DefaultLocale")
    override fun toString(): String {
        return name.trim().capitalize()
    }
}