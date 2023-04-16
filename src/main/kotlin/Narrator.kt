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
        "excited" ->  {
            narrationLambda(message)
        }
        "sneaky" -> {
            "$message. The narrator has just blown Madrigal's cover.".uppercase()
        }
        else -> message.uppercase()
    }
}

// Currying ?..
val sneakyNarration: (String) -> String = { loudNarration(it, "sneaky") }

var narrationModifier: (String) -> String = { it }

fun changeNarratorMood() {
    val mood: String
    val modifier: (String) -> String
    val random = Random()
    when (random.nextInt(5)) {
        1 -> {
            mood = "loud"
            modifier = narrationLambda
        }
        2 -> {
            mood = "tired"
            modifier = { it.lowercase().replace(" ", "... ") }
        }
        3 -> {
            mood = "unsure"
            modifier = { message -> "$message?"}
        }
        else -> {
            mood = "professional"
            modifier = { message -> "$message."}
        }
    }
    narrationModifier = modifier
    narrate("The narrator begins to feel $mood")
}

fun narrate(message: String) {
    println(narrationModifier(message))
}

// function which takes function as a parameter with a default value
fun narrateFancy1(message: String, modifier: (String) -> String = { narrationModifier(it)}) {
    println(modifier(message))
}

// function which takes function as a parameter with a default value, more concise syntax
fun narrateFancy2(message: String, modifier: (String) -> String = narrationModifier) {
    println(modifier(message))
}
