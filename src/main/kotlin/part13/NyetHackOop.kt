package part13


fun main() {

    val sword = Sword("Excalibur")
    println(sword.name)
    sword.name = "Gleipnir"
    println(sword.name)

    val heroname = "Madrigal"
    val player = Player(heroname, "Kronstadt", 100, false)
    val sideChar = Player("Jason", healthPoints = 200, hometown = "Jacksonville", isImmortal = false)
    player.title = createTitle(player.name)

    val mortality = if (player.isImmortal) "an immortal" else "a mortal"
    narrate("${player.name} of ${player.hometown}, ${player.title}, heads to the town square.")
    narrate("${player.name}, $mortality, has ${player.healthPoints} health points and is wielding ${player.weaponName}.")
    player.castFireball()

    val npc = LazyInitNpc("Slammer", "Slamville")
    val destiny = npc.prophecy
    narrate("${npc.title}, got his fate sealed: $destiny.")
    npc.weapon = Weapon("Pick axe")
    println(npc.title)
}

fun narrate(message: String, modifier: (String) -> String = { msg -> msg }) = println(modifier(message))

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
