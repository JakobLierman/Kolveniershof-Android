package be.hogent.kolveniershof.domain

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class Workday(
    @field:Json(name = "_id")
    val id: String,
    @field:Json(name = "date")
    val date: Date,
    @field:Json(name = "daycareMentors")
    val daycareMentors: MutableList<User>,
    @field:Json(name = "morningBusses")
    val morningBusses: MutableList<BusUnit>,
    @field:Json(name  = "amActivities")
    val amActivities: MutableList<ActivityUnit>,
    @field:Json(name = "lunch")
    val lunch: LunchUnit?,
    @field:Json(name = "pmActivities")
    val pmActivities: MutableList<ActivityUnit>,
    @field:Json(name = "dayActivities")
    val dayActivities: MutableList<ActivityUnit>,
    @field:Json(name = "eveningBusses")
    val eveningBusses: MutableList<BusUnit>,
    @field:Json(name = "holiday")
    val isHoliday: Boolean? = false,
    @field:Json(name = "comments")
    val comments: MutableList<Comment>
)