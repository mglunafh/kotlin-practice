package part15

import part16.Player

class Player(val name: String, val homeTown: String, var hp: Int = 100, val isImmortal: Boolean = false) {

    companion object {
        private const val SAVE_FILE_NAME = "player.dat"

        fun fromSaveFile(saveFile: String = SAVE_FILE_NAME): Player {
            return Player("John", "Johnsville", hp = 100)
        }
    }

    val prophecy by lazy {
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

    fun narrate(message: String) {
        narrate(message, heroSpeech)
    }

    fun castFireball(numFireballs: Int = 2) {
        narrate("I summon a glass of Fireball into existence (x$numFireballs)!")
    }

    private val heroSpeech: (String) -> String = { "\u001B[34;1m -- $it\u001B[0m" }
}
