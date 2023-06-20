package part15

import java.io.File

class Tavern: Room(TAVERN_NAME) {
    override val status = "Busy"

    private val patrons = firstNames.shuffled().zip(lastNames.shuffled()) { first, last -> "$first $last" }.toMutableSet()

    private val patronGold = mutableMapOf(
        TAVERN_MASTER to 86.00,
        *patrons.map{ it to 6.00}.toTypedArray()
        )

    companion object {
        private const val TAVERN_MASTER = "Taernyl"
        private const val TAVERN_NAME = "$TAVERN_MASTER's Folly"

        private val menuData = File("data/tavern-menu-data.txt").readText().split("\n").map { it.split(",") }
        private val menuNames = menuData.map { (_, name, _) -> name }
        private val menuPrices: Map<String, Double> = menuData.associate { (_, name, price) -> name to price.toDouble() }

        private val firstNames = listOf("Alex", "Mordoc", "Sophie", "Taric", "Hasan")
        private val lastNames = listOf("Ironfoot", "Fernsworth", "Baggings", "Downstrider")
    }

    override fun enter(player: Player) {
        narrate("${player.name} enters '${TAVERN_NAME}'.")
        narrate("There are several items for sale:")
        narrate(menuNames.joinToString())

        patronGold[player.name] = 4.5

        narrate("${player.name} sees several patrons in the tavern:")
        narrate(patrons.joinToString())

        repeat(3) {
            placeOrder(patrons.random(), menuNames.random())
        }

        patrons.filter { patronGold.getOrDefault(it, 0.0) < 4.0 }
            .also { departingPatrons ->
                patrons -= departingPatrons
                patronGold -= departingPatrons
            }
            .forEach {
                narrate("${player.name} sees $it departing the tavern")
            }
        if (patrons.size > 0) {
            narrate("There are still some patrons in the tavern:")
            narrate(patrons.joinToString())
        }
    }

    private fun placeOrder(patron: String, menuItem: String) {
        narrate("$patron speaks with $TAVERN_MASTER to place an order.")
        val itemPrice = menuPrices[menuItem]
        if (itemPrice == null) {
            tavernMasterResponds("Oh sorry bud, we don't serve this here.")
            return
        }

        val amountOfPatronMoney = patronGold.getOrDefault(patron, 0.0)
        if (itemPrice <= amountOfPatronMoney) {
            narrate("$TAVERN_MASTER hands $patron '$menuItem'.")
            narrate("$patron pays $TAVERN_MASTER $itemPrice gold.")
            patronGold[patron] = patronGold.getValue(patron) - itemPrice
            patronGold[TAVERN_MASTER] = patronGold.getValue(TAVERN_MASTER) + itemPrice
        } else {
            tavernMasterResponds("You need more coin for '$menuItem', sorry mate.")
        }
    }

    private fun tavernMasterResponds(message: String, modifier: (String) -> String = makeMagenta) {
        val responseVerb = listOf("responds", "answers", "replies", "says").random()
        narrate("$TAVERN_MASTER ${responseVerb}: ${modifier(message)}")
    }
}