package be.hogent.kolveniershof.domain

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ActivityUnit( //NetworkActivityUnit
    @field:Json(name = "_id")
    val id: String,
    @field:Json(name  = "activity")
    val activity: Activity,
    @field:Json(name = "mentors")
    val mentors: MutableList<User>
){
    override fun toString(): String {
        return activity.toString()
    }
    fun getImageName(): String {
        return activity.icon
    }
}
