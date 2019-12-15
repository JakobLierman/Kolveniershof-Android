package be.hogent.kolveniershof.domain

data class LunchUnit(
    val id: String,
    val lunch: String,
    val mentors: MutableList<User>,
    val clients: MutableList<User>
)