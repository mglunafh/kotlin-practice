package part16

lateinit var player: Player

fun main() {
    player = Player("Madrigal", "Slamville")
    Game.play()
}

fun narrate(message: String, modifier: (String) -> String = { msg -> msg }) = println(modifier(message))
val makeMagenta = { message: String -> "\u001B[35m$message\u001B[0m" }
val makeRed = { message: String -> "\u001B[31m$message\u001B[0m" }
val makeYellow = { message: String -> "\u001B[33m$message\u001B[0m" }
