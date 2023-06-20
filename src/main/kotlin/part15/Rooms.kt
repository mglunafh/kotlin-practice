package part15

// we need to specifically state that class is open for inheritance
open class Room(val name: String) {

    protected open val status = "Calm"

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
