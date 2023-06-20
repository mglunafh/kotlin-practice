package part15

fun main() {
    narrate("Welcome to NyetHack!")
    var currentRoom = Room("The Foyer")
    val player = Player("Madrigal", "Slamville")
    val playerProphecy = player.prophecy
    narrate("${player.name} enters ${currentRoom.name} and greets everyone here!")
    currentRoom.enter(player)
    player.narrate("Hello everyone!")
    currentRoom = TownSquare()
    currentRoom.enter(player)
    currentRoom = Tavern()
    currentRoom.enter(player)

    player.castFireball(3)
    narrate("${player.name} thinks about their future")
    narrate("A fortune teller told ${player.name}: $playerProphecy")
}

fun narrate(message: String, modifier: (String) -> String = { msg -> msg }) = println(modifier(message))
val makeMagenta = { message: String -> "\u001B[35m$message\u001B[0m" }
val makeRed = { message: String -> "\u001B[31m$message\u001B[0m" }

fun getTypeOfObject(any: Any): String {
    val hardCast: String = any as String    // ClassCastException if not a String
    val softCast = any as? String           // null if not a String

    return if (any is Player) {
        // smart cast
        any.name
    } else if (any is Room) {
        // smart cast
        any.name
    } else {
        "unidentified"
    }
}