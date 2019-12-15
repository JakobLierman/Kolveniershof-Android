package be.hogent.kolveniershof.domain

data class ActivityUnit(
    val id: String,
    val activity: Activity,
    val mentors: MutableList<User>,
    val clients: MutableList<User>


){
    override fun toString(): String {
        return activity.toString()
    }
    fun getImageName(): String {
        return activity.icon
    }}