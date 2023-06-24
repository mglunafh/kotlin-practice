package part16

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

        private val responseVerbs = listOf("responds", "answers", "replies", "says")
    }

    private fun tavernMasterResponds(message: String, modifier: (String) -> String = part15.makeMagenta) {
        narrate("$TAVERN_MASTER ${responseVerbs.random()}: ${modifier(message)}")
    }

}