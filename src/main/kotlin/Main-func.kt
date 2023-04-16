fun main(args: Array<String>) {
    narrateFancy1("A hero enters the tavern. What is their name?",
        { message -> "\u001b[33;1m$message\u001b[0m" })
    val heroName = readlnOrNull()
    require(!heroName.isNullOrEmpty()) {
        "\u001b[32;1mThe hero must have a name.\u001b[0m"
    }
    val title = createTitle(heroName)
    // The last parameter of `narrateFancy2` is function,
    // so it's allowed to move it out the parenthesis.
    narrateFancy2("Hello, $title $heroName!") {message -> "\u001b[31m$message\u001b[0m"}

    val someNames = arrayOf("Name", "Auriaya", "RazorScale", "Ayaya", "12463132411", "0-124-24!54", "DeathFireExtinguisher")
    someNames.forEach(::greetName)

    val someChars = arrayOf('o', 'q', 'f', 'g')
    for (char in someChars) {
        changeNarratorMood()
        numberOfChars(heroName, char)
    }
}

fun greetName(name: String) {
    val title = createTitle(name)
    narrateFancy2("Hello, $title $name!") {message -> "\u001b[31m$message\u001b[0m"}
}

// Function reference '::makeYellow'
fun numberOfChars(name: String, c: Char) {
    val someNumber = name.lowercase().count { it == c }
    narrateFancy1("Number of chars '$c': $someNumber", ::makeYellow)
}

fun createTitle(name: String): String {
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
