const val VILLAIN_NAME = "Estragon"
const val NEW_SECTION = "================"
fun printPlayerLevel(heroName: String, level: Int) {
    val title = determineTitleLevel(level)
    println("$title $heroName's level: $level")
}

fun tavernInteraction(heroName: String) {
    val drink1 = "Mead"
    val drink2 = "Wine"
    val drink3 = "Beer"
    val moneyAmount = 50
    val tavernName = "Horn of Unicorn"

    println(NEW_SECTION)
    println("$heroName sees a tavern with a name '$tavernName' and decides to walk into it.")
    println("The host asks: Do you need a stable?")
    println("$heroName answers: No, I don't have a steed. But I've got a $moneyAmount coins, and I'd like to buy some drinks!")
    println("The host replies: Sure thing, we can serve you $drink1, $drink2 and $drink3. What would you like to order?")
}

fun magicMirror(heroName: String) {
    val reversedName = heroName.reversed()
    println("Upon waking up, $heroName finds out there is a magic mirror on the shelf.")
    println("$heroName takes it and looks into it, it speaks: '$reversedName!'")
}

fun getQuestIf(
    heroName: String,
    level: Int,
    hasBefriendedBarbarians: Boolean,
    hasAngeredBarbarians: Boolean,
    playerClass: String
) {
    println(NEW_SECTION)
    val quest: String = if (level == 1) {
        "Meet mr. Bubbles in the land of the soft things."
    } else if (level in 2..5) {
        val canTalkToBarbarians = !hasAngeredBarbarians && (hasBefriendedBarbarians || playerClass == "barbarian")
        if (canTalkToBarbarians) {
            "Convince the barbarian lords to call off the invasion."
        } else {
            "Save the town from the barbarian invasions."
        }
    } else if (level == 6) {
        "Locate the enchanted sword."
    } else if (level == 7) {
        "Recover the long-lost artifact of creation."
    } else if (level == 8) {
        "Defeat $VILLAIN_NAME, bringer of deaths and eater of worlds."
    } else {
        "There are no quests right now."
    }
    println(quest)
    println("The time passes...")
    println("$heroName returns from her quest.")
}

fun getQuestWhen(
    heroName: String,
    level: Int,
    hasBefriendedBarbarians: Boolean,
    hasAngeredBarbarians: Boolean,
    playerClass: String
) {
    println(NEW_SECTION)
    val quest: String = when (level) {
        1 -> "Meet mr. Bubbles in the land of the soft things."
        in 2..5 -> {
            val canTalkToBarbarians = !hasAngeredBarbarians && (hasBefriendedBarbarians || playerClass == "barbarian")
            if (canTalkToBarbarians) {
                "Convince the barbarian lords to call off the invasion."
            } else {
                "Save the town from the barbarian invasions."
            }
        }
        6 -> "Locate the enchanted sword."
        7 -> "Recover the long-lost artifact of creation."
        8 -> "Defeat $VILLAIN_NAME, bringer of deaths and eater of worlds."
        else -> "There are no quests right now."
    }
    println(quest)
    println("The time passes...")
    println("$heroName returns from her quest.")
}

fun determineTitleLevel(level: Int): String {
    return when (level) {
        1 -> "Apprentice"
        in 2..8 -> "Level $level Warrior"
        9 -> "Vanquisher of $VILLAIN_NAME"
        else -> "Distinguished Knight"
    }
}

fun determineTitleExp(experience: Int): String {
    val playerTitle: String = when (val playerLevel = experience / 100 + 1) {
        1 -> "Apprentice"
        in 2..8 -> "Level $playerLevel Warrior"
        9 -> "Vanquisher of $VILLAIN_NAME"
        else -> "Distinguished Knight"
    }
    return playerTitle
}

fun main(args: Array<String>) {
    val heroName = "Madrigal"
    var playerLevel = 8
    println("The hero announces her presence in this world. Hero name: $heroName")
    printPlayerLevel(heroName, playerLevel)

    tavernInteraction(heroName)
    magicMirror(heroName)

    val friendsWithBarbarians = false
    val playerClass = "barbarian"
    val hasAngeredBarbarians = true
    getQuestIf(heroName, playerLevel, friendsWithBarbarians, hasAngeredBarbarians, playerClass)
    playerLevel++
    printPlayerLevel(heroName, playerLevel)

    getQuestWhen(heroName, playerLevel, friendsWithBarbarians, hasAngeredBarbarians, playerClass)
    playerLevel++
    printPlayerLevel(heroName, playerLevel)
}
