fun main(args: Array<String>) {
    narrateFancy1("A hero enters the tavern. What is their name?",
        { message -> "\u001b[33;1m$message\u001b[0m" })
    val heroName = readlnOrNull() ?: "kek"

    // The last parameter of `narrateFancy2` is function,
    // so it's allowed to move it out the parenthesis.
    narrateFancy2("Hello, $heroName!") {message -> "\u001b[31m$message\u001b[0m"}

    val someChars = arrayOf('o', 'q', 'f', 'g')
    for (char in someChars) {
        changeNarratorMood()
        numberOfChars(heroName, char)
    }
}

fun numberOfChars(name: String, c: Char) {
    val someNumber = name.lowercase().count { it == c }
    narrate("Number of chars '$c': $someNumber")
}
