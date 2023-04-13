
// lambda return value is the type of the last expression, in this case -- String
// return is prohibited in lambdas
val narrationLambda: (String) -> String = { message ->
    val numberOfExclamations = 3
    message.uppercase() + "!".repeat(numberOfExclamations)
}

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

val sneakyNarration: (String) -> String = { loudNarration(it, "sneaky") }

fun narrate(message: String) {
    println(narrationLambda(message))
}