package be.hogent.kolveniershof.domain

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class BusUnit(
    @field:Json(name = "_id")
    val id: String,
    @field:Json(name  = "bus")
    val bus: Bus,
    @field:Json(name = "mentors")
    val mentors: MutableList<User>
){
    override fun toString(): String {
        return bus.toString()
    }
}