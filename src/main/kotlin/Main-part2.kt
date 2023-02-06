fun main(args: Array<String>) {
    val heroName = "Madrigal"
    println("$heroName announces her presence to this world.")
    print("What level is $heroName? ")

    val playerLevel = getLevelFromInputElvis()
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
    val quest: String? = obtainQuest(level)
    val message = quest?.replace(VILLAIN_NAME, "*".repeat(VILLAIN_NAME.length))?.let { censoredQuest ->
        """
        |$heroName approaches the bounty board. It reads:
        |   "$censoredQuest"
        """.trimMargin()
    } ?: "$heroName approaches the bounty board, but it's blank."
    println(message)
}


private fun obtainQuest(level: Int): String? = when (level) {
    1 -> "Meet mr. Bubbles in the land of the soft things."
    in 2..5 -> "Save the town from the barbarian invasions."
    6 -> "Locate the enchanted sword."
    7 -> "Recover the long-lost artifact of creation."
    8 -> "Defeat $VILLAIN_NAME, bringer of deaths and eater of worlds."
    else -> null
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

private fun printPlayerLevel(heroName: String, level: Int) {
    val title = determineTitleLevel(level)
    println("$title $heroName's level: $level")
}

private fun determineTitleLevel(level: Int): String {
    return when (level) {
        1 -> "Apprentice"
        in 2..8 -> "Level $level Warrior"
        9 -> "Vanquisher of $VILLAIN_NAME"
        else -> "Distinguished Knight"
    }
}
