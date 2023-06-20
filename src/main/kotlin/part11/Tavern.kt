package part11

import java.io.File

private const val TAVERN_MASTER = "Taernyl"
private const val TAVERN_NAME = "$TAVERN_MASTER's Folly"
private val menuData = File("data/tavern-menu-data.txt").readText().split("\n").map { it.split(",") }
private val resourceData = object {}.javaClass.getResourceAsStream("/menu.txt")?.bufferedReader()?.readLines()?.toList()

private val menuNames = menuData.map { (_, name, _) -> name }
private val menuPrices: Map<String, Double> = menuData.associate { (_, name, price) -> name to price.toDouble() }
private val menuItemTypes: Map<String, String> = menuData.associate { (type, name, _) -> name to type }

private val firstNames = listOf("Alex", "Mordoc", "Sophie", "Taric", "Hasan")
private val lastNames = listOf("Ironfoot", "Fernsworth", "Baggings", "Downstrider")

fun visitTavern() {
    narrate("$heroName enters '$TAVERN_NAME'.")

    val patronGold = mutableMapOf(TAVERN_MASTER to 86.00, heroName to 4.50)

    val patrons = firstNames.shuffled().zip(lastNames.shuffled()) { first, last -> "$first $last" }
    patrons.forEach { patronGold[it] = 6.0 }

    narrate("There are few people: ${patrons.joinToString(",")}.")
    repeat(3) {
        placeOrder(patrons.random(), menuNames.random(), patronGold)
    }

    val departing = patrons.filter { patron -> patronGold.getOrDefault(patron, 0.0) < 4.0 }
    departing.forEach { patron ->
        narrate("$heroName sees $patron departing from the tavern.")
    }
}

private fun placeOrder(patron: String, menuItem: String, patronGold: MutableMap<String, Double>) {
    narrate("$patron speaks with $TAVERN_MASTER to place an order.")
    val itemPrice = menuPrices[menuItem]
    if (itemPrice == null) {
        respond(TAVERN_MASTER, "Oh sorry bud, we don't serve this here.")
        return
    }

    val amountOfPatronMoney = patronGold.getOrDefault(patron, 0.0)
    if (itemPrice <= amountOfPatronMoney) {
        narrate("$TAVERN_MASTER hands $patron '$menuItem'.")
        narrate("$patron pays $TAVERN_MASTER $itemPrice gold.")
        patronGold[patron] = patronGold.getValue(patron) - itemPrice
        patronGold[TAVERN_MASTER] = patronGold.getValue(TAVERN_MASTER) + itemPrice
    } else {
        respond(TAVERN_MASTER, "You need more coin for '$menuItem', sorry mate.")
    }
}
