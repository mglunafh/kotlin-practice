package part13

class Player(initialName: String,
             val hometown: String = "NeverSummer",
             var healthPoints: Int,
             val isImmortal: Boolean) {

    // named parameters in the ctor
    constructor(name:String): this(
        initialName = name,
        healthPoints = 100,
        isImmortal = false
    )

//  The dull way to call the main ctor
//    constructor(name:String): this(name, "NeverSummer", 100, false)

    constructor(name: String, healthPoints: Int): this(
        initialName = name,
        healthPoints = healthPoints,
        isImmortal = false
    ) {
        if (name == hometown) {
            this.healthPoints += 100
        }
    }

    // we use custom getter and setter for 'name',
    // so we cannot define its 'val'/'var' property in the main constructor declaration
    var name = initialName
        get() = field.replaceFirstChar { it.uppercase() }
        private set(value) {
            field = value.trim()
        }

    var title = ""
        // restricted setter visibility a module. Public is default, can be private / protected as well.
        internal set(value) {
            field = value.trim()
        }

    var level: Int = 1
        private set

    // This property is not backed by a field, so it can't be marked as 'var'
    // although it may produce different results over time
    val calcTitle: String
        get() = when {
            name.all { it.isDigit() } -> "The Identifiable"
            name.none { it.isLetter() } -> "The Witness Protection Member"
            name.count { it.lowercase() in "aeoui" } > 4 -> "The Master of Vowels"
            else -> "The Renowned Hero"
        }

    var weapon: Weapon? = Weapon("Mjolnir")

    val weaponName: String
        get() {
            return weapon?.name ?: "nothing"
        }

    // Initialization block! \m/
    // It can be declared before any additional ctor but must be after all properties
    init {
        require(isImmortal || healthPoints > 0) { "Health point fo a mortal character must be greater than zero" }
        require(name.isNotBlank()) { "Player must have a name!" }
    }

    fun castFireball(numFireballs: Int = 2) {
        narrate("I summon a glass of Fireball springs into existence (x$numFireballs)!")
    }

    fun narrate(message: String) {
        narrate(message, heroSpeech)
    }

    fun levelUp() {
        level++
    }

    private val heroSpeech: (String) -> String = { "\u001B[34;1m -- $it\u001B[0m"}

}

class Weapon(val name: String)