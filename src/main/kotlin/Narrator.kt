import java.util.Random

// lambda return value is the type of the last expression, in this case -- String;
// return is prohibited in lambdas.
val narrationLambda: (String) -> String = { message ->
    val numberOfExclamations = 3
    message.uppercase() + "!".repeat(numberOfExclamations)
}

// declare the type of arguments in the body
val anotherNarration = { message: String ->
    message + ".".repeat(3)
}

// declare the lambda with several arguments
val loudNarration: (String, String) -> String = { message, tone ->
    when (tone) {
        "excited" -> { narrationLambda(message) }
        "sneaky" -> { "$message. The narrator has just blown Madrigal's cover.".uppercase() }
        else -> message.uppercase()
    }
}

val sneakyNarration: (String) -> String = { loudNarration(it, "sneaky") }

var narrationModifier: (String) -> String = { it }

val moods = arrayOf("loud", "tired", "unsure", "professional", "lazy", "mysterious")
fun changeNarratorMood() {
    val random = Random()
    val moodIndex = random.nextInt(moods.size)
    val mood = moods[moodIndex]
    narrationModifier = when (mood) {
        "loud" -> narrationLambda
        "tired" -> { message -> message.lowercase().replace(" ", "... ") }
        "unsure" -> { message -> "$message?" }
        "professional" -> { message -> "$message." }
        "lazy" -> { message -> message.take(message.length / 2) + "..." }
        "mysterious" -> { message -> message.replace("i", "1").replace("E", "3").replace("T", "7") }
        else -> { message -> message }
    }
    narrate("The narrator begins to feel $mood")
}

fun narrate(message: String) {
    println(narrationModifier(message))
}

// function which takes function as a parameter with a default value
fun narrateFancy1(message: String, modifier: (String) -> String = { narrationModifier(it) }) {
    println(modifier(message))
}

// function which takes function as a parameter with a default value, more concise syntax
fun narrateFancy2(message: String, modifier: (String) -> String = narrationModifier) {
    println(modifier(message))
}

// Function, can be passed using function reference syntax
fun makeYellow(message: String) = "\u001B[33m$message\u001B[0m"

// Lambdas, can be passed by their names
val makeGreen = { message: String -> "\u001B[32m$message\u001B[0m" }
val makeRed = { message: String -> "\u001B[31m$message\u001B[0m" }
