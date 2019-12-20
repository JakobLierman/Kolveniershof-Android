package be.hogent.kolveniershof.domain

import android.annotation.SuppressLint
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Activity( //NetworkActivity
    @field:Json(name = "_id")
    val id: String,
    @field:Json(name  = "name")
    val name: String,
    @field:Json(name = "icon")
    val icon: String
){
    @SuppressLint("DefaultLocale")
    override fun toString(): String {
        return name.trim().capitalize()
    }
}
