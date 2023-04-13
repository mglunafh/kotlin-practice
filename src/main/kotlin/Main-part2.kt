import java.lang.Exception
import java.lang.IllegalArgumentException

fun main(args: Array<String>) {
    val heroName = "Madrigal"
    println("$heroName announces her presence to this world.")
    print("What level is $heroName? ")

    val playerLevel = getLevelCorrectly()
    printPlayerLevel(heroName, playerLevel)

    readBountyBoardLet(heroName, playerLevel)
}

private fun readBountyBoardIf(heroName: String, level: Int) {
    val quest: String? = obtainQuest(level)
    if (quest == null) {
        return
    }
    // smart-cast here: String? --> String
    val censoredQuest = quest.replace(VILLAIN_NAME, "*".repeat(VILLAIN_NAME.length))

    println(
        """
        |$heroName approaches the bounty board. It reads:
        |   "$censoredQuest"
        """.trimMargin()
    )
}

private fun readBountyBoardShortCircuit(heroName: String, level: Int) {
    val quest: String = obtainQuest(level) ?: return

    val censoredQuest = quest.replace(VILLAIN_NAME, "*".repeat(VILLAIN_NAME.length))
    println(
        """
        |$heroName approaches the bounty board. It reads:
        |   "$censoredQuest"
        """.trimMargin()
    )
}


private fun readBountyBoardSafeCall(heroName: String, level: Int) {
    val quest: String? = obtainQuest(level)
    val censoredQuest: String? = quest?.replace(VILLAIN_NAME, "*".repeat(VILLAIN_NAME.length))

    if (censoredQuest != null) {
        println(
            """
        |$heroName approaches the bounty board. It reads:
        |   "$censoredQuest"
        """.trimMargin()
        )
    }
}

private fun readBountyBoardLet(heroName: String, level: Int) {
    val someMessage: String = try {
        val quest: String? = obtainQuestPrecondFunctions(level)
        val message = quest?.replace(VILLAIN_NAME, "*".repeat(VILLAIN_NAME.length))?.let { censoredQuest ->
            """
            |$heroName approaches the bounty board. It reads:
            |   "$censoredQuest"
            """.trimMargin()
        } ?: "$heroName approaches the bounty board, but it's blank."
        message
    } catch (e: Exception) {
        "$heroName cannot read what's on the bounty board."
    }
    println(someMessage)
}


private fun obtainQuest(level: Int): String? {
    if (level <= 0) {
        throw IllegalArgumentException("Level must not be a positive number, got $level")
    }
    return when (level) {
        1 -> "Meet mr. Bubbles in the land of the soft things."
        in 2..5 -> "Save the town from the barbarian invasions."
        6 -> "Locate the enchanted sword."
        7 -> "Recover the long-lost artifact of creation."
        8 -> "Defeat $VILLAIN_NAME, bringer of deaths and eater of worlds."
        else -> null
    }
}

private fun obtainQuestPrecondFunctions(level: Int): String? {
    require(level > 0) {
        "Level must not be a positive number, got $level"
    }
    return when (level) {
        1 -> "Meet mr. Bubbles in the land of the soft things."
        in 2..5 -> "Save the town from the barbarian invasions."
        6 -> "Locate the enchanted sword."
        7 -> "Recover the long-lost artifact of creation."
        8 -> "Defeat $VILLAIN_NAME, bringer of deaths and eater of worlds."
        else -> null
    }
}

private fun getLevelFromInputRegex(): Int {
    val input = readln()
    return if (input.matches("""\d+""".toRegex())) {
        input.toInt()
    } else {
        1
    }
}

private fun getLevelFromInputElvis(): Int {
    return readlnOrNull()?.toIntOrNull() ?: 1
}

private fun getLevelCorrectly(): Int {
    var input = readln()
    do try {
        return input.toInt()
    } catch (e: Exception) {
        print("Could not understand the level of hero. Please, enter the level again: ")
        input = readln()
    } while (true)
}

private fun printPlayerLevel(heroName: String, level: Int) {
    val title = determineTitleLevel(level)
    println("$title $heroName's level: $level")
}

private fun determineTitleLevel(level: Int): String = when (level) {
    1 -> "Apprentice"
    in 2..8 -> "Level $level Warrior"
    9 -> "Vanquisher of $VILLAIN_NAME"
    else -> "Distinguished Knight"
}
