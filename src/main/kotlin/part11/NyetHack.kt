package part11

import java.util.*


var heroName = ""

fun main() {
    heroName = promptHeroName()
    val heroTitle = createTitle(heroName)
    narrate("$heroName, $heroTitle, heads to the town square.")

    visitTavern()
}

fun narrate(message: String, modifier: (String) -> String = { msg -> msg }) = println(modifier(message))
fun narrateHero(message: String) = narrate(message, heroSpeech)
private val heroSpeech: (String) -> String = { "\u001B[34;1m -- $it\u001B[0m"}

fun respond(character: String,
            message: String,
            modifier: (String) -> String = makeMagenta) {
    val random = Random()
    val responseVerbs = listOf("responds", "answers", "replies", "says")
    val verb = responseVerbs[random.nextInt(responseVerbs.size)]

    println("$character ${verb}: ${modifier(message)}")
}

val makeMagenta = { message: String -> "\u001B[35m$message\u001B[0m" }
val makeRed = { message: String -> "\u001B[31m$message\u001B[0m" }


private fun promptHeroName(useDefaultName: Boolean = true): String {
    if (useDefaultName)
        return "Madrigal"
    narrate("A hero enters the town of Kronstadt. What is their name?") { message -> "\u001b[33;1m$message\u001b[0m" }
    val heroName = readlnOrNull()
    require(!heroName.isNullOrBlank()) { "The hero must have a name" }
    return heroName
}

private fun createTitle(name: String): String {
    return when {
        name.all { it.isUpperCase() } -> "The Outstanding"
        name.all { it.isDigit() } -> "The Identifiable"
        name.none { it.isLetter() } -> "The Witness Protection Member"
        name.lowercase().reversed() == name.lowercase() -> "The Palindrome-Wielder"
        name.length > 15 -> "The Long-winded"
        name.count {it.lowercase() in "aeiou" } > 4 -> "The Master Of Vowels"
        else -> "The Renowned Hero"
    }
}
