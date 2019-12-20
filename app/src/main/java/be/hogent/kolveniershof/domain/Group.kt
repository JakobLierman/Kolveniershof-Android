package be.hogent.kolveniershof.domain

import android.annotation.SuppressLint
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Group(
    @field:Json(name = "_id")
    val id: String,
    @field:Json(name  = "name")
    val name: String,
    @field:Json(name = "members")
    val members: MutableList<User>
){
    @SuppressLint("DefaultLocale")
    override fun toString(): String {
        return name.trim().capitalize()
    }
}