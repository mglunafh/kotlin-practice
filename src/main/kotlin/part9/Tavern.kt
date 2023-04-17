package part9

private const val TAVERN_MASTER = "Taernyl"
private const val TAVERN_NAME = "$TAVERN_MASTER's Folly"

fun visitTavern() {
    narrate("$heroName enters '$TAVERN_NAME'.")
    val patrons = listOf("Eli", "Mordoc", "Sophie")
    narrate("The first person $heroName sees is ${patrons[0]}") { msg -> msg.uppercase() + "!!!"}
    narrate("The last person $heroName would like to see there is ${patrons.last()}") { msg -> "\u001B[30;1m${msg.lowercase()}...\u001B[0m"}

//    val ciaInfluencer = patrons.getOrNull(4) ?: "Unknown Patron"

    val welder = patrons[1]
    narrateHero("Hello! By any chance, can I find $welder here?")
    respond(TAVERN_MASTER, chatAboutPerson(patrons, welder))
}

private fun chatAboutPerson(people: List<String>, personName: String): String {
    return if (people.contains(personName)) {
        "Yes, $personName is right at that desk"
    } else {
        "No, haven't seen him today"
    }
}

