package part9

import java.nio.file.Files
import java.nio.file.Path

private const val TAVERN_MASTER = "Taernyl"
private const val TAVERN_NAME = "$TAVERN_MASTER's Folly"
private val menuData = Files.readAllLines(Path.of("data/tavern-menu-data.txt")).toList()
private val resourceData = object {}.javaClass.getResourceAsStream("/menu.txt")?.bufferedReader()?.readLines()?.toList()

fun visitTavern() {
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
    resourceData?.forEachIndexed { i, data -> println("resource $i: $data")}
}

private fun chatAboutPerson(people: List<String>, personName: String): String {
    return if (people.contains(personName)) {
        "Yes, $personName is right at that desk."
    } else {
        "No, haven't seen him today."
    }
}

