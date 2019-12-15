package be.hogent.kolveniershof.domain

data class BusUnit(
    val id: String,
    val bus: Bus,
    val mentors: MutableList<User>,
    val clients: MutableList<User>
)