fun main(args: Array<String>) {
    println("A hero enters the tavern. What is their name?")
    val heroName = readlnOrNull() ?: "kek"
    println(loudNarration("Hello, $heroName!", "sneaky"))

    val specialChar = 'o'
    val commonChar = 'q'
    val someNumber = heroName.count { c -> c == specialChar }
    val anotherNumber = heroName.count { it == commonChar }
    narrate("Number of special chars '$specialChar': $someNumber")
    narrate("Number of usual chars '$commonChar': $anotherNumber")
}
