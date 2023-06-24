package part16

object Game {

    private val worldMap = listOf(
        listOf(TownSquare(), Tavern(), Room("Back Room")),
        listOf(Room("A Long Corridor"), Room("A Generic Room")),
        listOf(Room("The Dungeon"))
    )

    private var currentRoom: Room = worldMap[0][0]
    private var currentPosition = Coordinate(0, 0)
    private var finishGame = false

    init {
        narrate("Welcome, adventurer")
        narrate("${player.name} has ${player.hp} health points")
    }

    fun play() {
        while (true) {
            narrate("${player.fullName}, of ${player.homeTown}, is in ${currentRoom.description()}")
            currentRoom.enter(player)

            print("> Enter your command: ")
            val input = readln()
            GameInput(input).processCommand()

            if (finishGame) {
                println("Finishing the game. Have a nice day!")
                return
            }
        }
    }

    fun move(direction: Direction) {
        val newPosition = direction.updateCoordinate(currentPosition)
        val newRoom = worldMap.getOrNull(newPosition.y) ?.getOrNull(newPosition.x)

        if (newRoom != null) {
            narrate("The hero moves ${direction.name}")
            currentRoom = newRoom
            currentPosition = newPosition
        } else {
            narrate("You cannot move ${direction.name}")
        }
    }

    private class GameInput(arg: String?) {
        private val input = arg?: ""
        val argList = input.split(" ")
        val command = argList[0].lowercase()
        val argument = argList.getOrElse(1) { "" }.lowercase()

        fun processCommand() {
            when(command) {
                "move" -> {
                    val direction = Direction.values().firstOrNull { it.name.equals(argument, ignoreCase = true) }
                    if (direction != null) {
                        move(direction)
                    } else {
                        narrate("I don't know what direction '$argument' is", makeYellow)
                    }
                }

                in listOf("q", "quit", "exit") -> finishGame = true

                else -> narrate("I'm not sure what you're trying to do", makeYellow)
            }
        }
    }
}
