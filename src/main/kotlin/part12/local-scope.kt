package part12

import java.io.File

val isAfterMidnight = true
val isOpenMicNight = false
val isHappyHour = true

// `apply` function helps call a bunch of functions applied to the object before returning it for the further use
val guestList = mutableListOf<String>().apply {
    if (isAfterMidnight) { add("Sidney") }
    if (isOpenMicNight) { add("Janet") }
    if (isHappyHour) { add("Jamie") }
    if (contains("Janet") || contains("Jamie")) { add("Hal") }
}.toList()


// `let` function. Returns the last expression in lambda
val greeting =  guestList.first().let {
    "$it walks over to Madrigal and says, \"Hi! I'm $it. Welcome to Kronstadt!\""
}

// without `let` function we have to declare additional variable
val friendlyPerson = guestList.first()
val longGreeting = "$friendlyPerson walks over to Madrigal and says, \"Hi! I'm $friendlyPerson. Welcome to Kronstadt!\""


// `run`
val tavernPlayList = mutableListOf("Kalinka", "Katyusha")
val nowPlaying = tavernPlayList.run {
    shuffle()
    "${first()} is currently playing in the tavern"
}

val temp = tavernPlayList.let {
    it.shuffle()
    "${it.first()} is currently playing in the tavern"
}

fun someFunWithAlso() {
    var fileContents: List<String>
    File("data/tavern-menu-data.txt")
        .also { print(it.name) }
        .readLines()
        .also { fileContents = it }
}

fun someFunWithTakeIf() {
    val fileContents = File("myfile.txt")
        .takeIf { it.exists() }
        ?. readText()
}
