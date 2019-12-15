package be.hogent.kolveniershof.domain

import java.util.*

data class Workday(
    val id: String,
    val date: Date,
    val daycareMentors: MutableList<User>,
    val morningBusses: MutableList<BusUnit>,
    val amActivities: MutableList<ActivityUnit>,
    val lunch: LunchUnit,
    val pmActivities: MutableList<ActivityUnit>,
    val eveningBusses: MutableList<BusUnit>,
    val isHoliday: Boolean? = false,
    val comments: MutableList<Comment>
)