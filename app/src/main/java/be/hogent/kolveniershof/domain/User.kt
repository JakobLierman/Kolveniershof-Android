package be.hogent.kolveniershof.domain

data class User(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String? = null,
    val isAdmin: Boolean = false,
    val imgUrl: String? = null,
    val token: String? = null
) {
    override fun toString(): String {
        return "$firstName $lastName"
    }
}