package be.hogent.kolveniershof.domain


data class Group(
    val id: String,
    val name: String,
    val members: MutableList<User>
)