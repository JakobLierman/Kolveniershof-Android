package be.hogent.kolveniershof.domain


data class Comment(
    val id: String,
    val comment: String,
    val user: User
)