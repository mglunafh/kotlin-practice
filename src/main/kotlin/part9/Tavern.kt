package part9

import java.io.File

private const val TAVERN_MASTER = "Taernyl"
private const val TAVERN_NAME = "$TAVERN_MASTER's Folly"
private val menuData = File("data/tavern-menu-data.txt").readText().split("\n")
private val resourceData = object {}.javaClass.getResourceAsStream("/menu.txt")?.bufferedReader()?.readLines()?.toList()

private val menuPrices: Map<String, Double> = List(menuData.size) { index ->
    val (_, name, price) = menuData[index].split(",")
    name to price.toDouble()
}.toMap()

private val firstNames = listOf("Alex", "Mordoc", "Sophie", "Taric", "Hasan")
private val lastNames = listOf("Ironfoot", "Fernsworth", "Baggings", "Downstrider")

fun visitTavern_v1() {
    narrate("$heroName enters '$TAVERN_NAME'.")
    val patrons = mutableListOf("Eli", "Mordoc", "Sophie")
    val vips = mutableListOf<String>()
    narrate("The first person $heroName sees is ${patrons[0]}") { msg -> msg.uppercase() + "!!!"}
    narrate("The last person $heroName would like to see there is ${patrons.last()}") { msg -> "\u001B[30;1m${msg.lowercase()}...\u001B[0m"}

//    val ciaInfluencer = patrons.getOrNull(4) ?: "Unknown Patron"

    val welder = patrons[1]
    narrateHero("Hello! By any chance, can I find $welder here?")
    respond(TAVERN_MASTER, chatAboutPerson(patrons, welder))

    val othersPresence = if (patrons.containsAll(listOf("Mordoc", "Sophie"))) {
        "By the way, Mordoc and Sophie are seated by the stew kettles."
    } else {
        "Sophie and Mordoc aren't with each other right now."
    }
    respond(TAVERN_MASTER, othersPresence)

    val personLeaves = { person: String ->  if (patrons.remove(person)) narrate("$person leaves the \"$TAVERN_NAME\".") }
    val personComesIn = { person: String -> patrons.add(person); narrate("$person enters the \"$TAVERN_NAME\".") }
    val vipComesIn = {person: String -> vips.add(0, person); narrate("$person (VIP) enters the \"$TAVERN_NAME\"!", makeRed) }

    personLeaves("Eli")
    personComesIn("Alex")
    vipComesIn("Alex")
    vips[0] = "Alexis"
    vips += "Caytlin"
    vips += listOf("Hasan", "Tughan")
    println(vips)

    narrate("The evening has come, and $TAVERN_NAME greets everyone in the tavern!")
    (vips + patrons).forEachIndexed { ind, visitor -> respond(TAVERN_MASTER, "Good evening, $visitor! You are #${ind + 1}") }

    menuData.forEachIndexed { i, data -> println("$i: $data") }
    resourceData?.forEachIndexed { i, data -> println("resource $i: $data") }
}

fun visitTavern_v2() {
    requireNotNull(resourceData)
    val menuItems = List(resourceData.size) { ind ->
        val (_, name, _) = resourceData[ind].split(",")
        name
    }
    val patrons = mutableSetOf<String>()
    val patronGold = mutableMapOf(TAVERN_MASTER to 86.00, heroName to 4.50)

    while (patrons.size < 10) {
        val patronName = "${firstNames.random()} ${lastNames.random()}"
        patrons += patronName
        patronGold += patronName to 6.0
    }
    narrate("$heroName enters '$TAVERN_NAME'.")
    narrate("$heroName sees several patrons here: ${patrons.joinToString()}")

//    narrate("There are several items for sale: ${menuItems.joinToString(transform = { name -> "'$name'" })}.")
//    patrons.forEachIndexed { index, patron -> respond(TAVERN_MASTER, "Greetings, $patron! You are #${index + 1} in the queue with order '${menuItems.random()}'.") }
    prettyPrintMenu()

    repeat(3) {
        placeOrder(patrons.random(), menuItems.random(), patronGold)
    }
    displayMoneyInPeoplePockets(patronGold)
}

private fun messingWithMaps() {
    // infix function syntax
    val patronGold = mapOf(TAVERN_MASTER to 86.00, heroName to 4.50)
    println(patronGold)
    // boring syntax
    val patronGold_v2 = mapOf(TAVERN_MASTER.to(86.00), heroName.to(4.50))
    // actually a collection of Pair objects
    val patronGold_v3 = mutableMapOf(Pair(TAVERN_MASTER, 86.00), Pair(heroName, 4.50))

    var heroMoney = patronGold[heroName]        // value or null
    heroMoney = patronGold.getValue(heroName)   // value or NoSuchElementException
    heroMoney = patronGold.getOrDefault(heroName, 0.0) // value with the default option
    heroMoney = patronGold.getOrElse(heroName) { if (heroName == "Jane") 4.0 else 0.0 } // value with an option to generate a default value

    val stolenMoney = patronGold_v3.remove(heroName)
    require(patronGold_v3[heroName] == null)
}

fun prettyPrintMenu() {
    requireNotNull(resourceData)
    val longestItem = resourceData.maxBy { it.length }
    val totalLength = longestItem.length
    val title = " Welcome to $TAVERN_NAME "
    val titleDiff = totalLength - title.length
    val leftMargin = titleDiff / 2
    val rightMargin = (titleDiff + 1) / 2
    val formattedTitle = "*".repeat(leftMargin) + title + "*".repeat(rightMargin)

    val menuEntries = List(resourceData.size + 1) { ind ->
        if (ind == 0) {
            formattedTitle
        } else {
            val (_, name, price) = resourceData[ind - 1].split(",")
            val dotsNumber = totalLength - name.length - price.length
            "$name${".".repeat(dotsNumber)}$price"
        }
    }
    menuEntries.forEach(::println)
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

private fun displayMoneyInPeoplePockets(personGold: Map<String, Double>) {
    // syntax with 'it'
    personGold.forEach { println("${it.key} has ${it.value} gold.") }
    // syntax with destructuring
    personGold.forEach { (name, money) -> println("$name has $money gold.") }
}

private fun chatAboutPerson(people: List<String>, personName: String): String {
    return if (people.contains(personName)) {
        "Yes, $personName is right at that desk."
    } else {
        "No, haven't seen him today."
    }
}

private fun loopClusterF() {
    var isTavernOpen = true
    var isClosingTime = false
    while (isTavernOpen) {
        if (isClosingTime) {
            break
        }
        println("Have a grand Time!")
    }

    // labels inside lambdas, break out of lambda
    ('a'..'z').forEach letters@{ letter ->
        if ("gpt".contains(letter)) {
            return@letters
        }
        println(letter)
    }

    // labels inside lambda return value
    var someCrazy = label@{n: Int ->
        var temp = 0
        for (i in 1..n) {
            if (temp > n) {
                return@label temp
            }
            temp += i*i
        }
        temp
    }
}