package part16

open class Room(
    val name: String,
    protected open val status: String = "Calm"
) {

    fun description() = name

    // and we need to specify what method can be overridden
    open fun enter(player: Player) {
        narrate("There is nothing to do here.")
    }
}

class TownSquare: Room("The Town Square") {
    override val status = "Buzzing"

    private val bellSound = "GWONG"

    override fun enter(player: Player) {
        narrate("The villages rally and cheer as the ${player.name} enters!")
        ringBell()
    }

    private fun ringBell() {
        narrate("The bell tower announces the hero's presence: $bellSound")
    }
}
