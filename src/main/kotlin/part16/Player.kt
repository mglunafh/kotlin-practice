package part16

class Player(val name: String, val homeTown: String, var hp: Int = 100) {

    private val prophecy by lazy {
        narrate("$name embarks on a arduous quest to locate a fortune teller", makeRed)
        Thread.sleep(1000)
        narrate("The fortune teller bestows a prophecy upon $name", makeRed)
        val prophecyText = listOf(
            "form an unlikely bond between two belligerent factions",
            "take the possession of otherworldly blade",
            "bring the gift of the creation back to the world",
            "best the world-eater"
        ).random()

        "An intrepid hero from $homeTown shall some day $prophecyText"
    }

    val fullName: String = "$name, ${createTitle()}"

    private fun createTitle(): String {
        return when {
            name.all { it.isUpperCase() } -> "The Outstanding"
            name.all { it.isDigit() } -> "The Identifiable"
            name.none { it.isLetter() } -> "The Witness Protection Member"
            name.lowercase().reversed() == name.lowercase() -> "The Palindrome-Wielder"
            name.length > 15 -> "The Long-winded"
            name.count {it.lowercase() in "aeiou" } > 4 -> "The Master Of Vowels"
            else -> "The Renowned Hero"
        }
    }

    fun prophesize() {
        val playerProphecy = prophecy
        part16.narrate("$name thinks about their future")
        part16.narrate("A fortune teller told $name: $playerProphecy")
    }

    fun narrate(message: String) {
        narrate(message, heroSpeech)
    }

    fun castFireball(numFireballs: Int = 2) {
        narrate("I summon a glass of Fireball into existence (x$numFireballs)!")
    }

    private val heroSpeech: (String) -> String = { "\u001B[34;1m -- $it\u001B[0m" }

}
