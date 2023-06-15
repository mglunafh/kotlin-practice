package part13

class LazyInitNpc(val name: String, val hometown: String) {

    lateinit var weapon: Weapon

    // 'lazy' is a property delegate, among 'observable', 'vetoable', 'notNull'
    // There can also be custom delegates (see interfaces ReadOnlyProperty / ReadWriteProperty)
    val prophecy by lazy {
        narrate("$name embarks on a arduous quest to locate a fortune teller")
        Thread.sleep(1000)
        narrate("The fortune teller bestows a prophecy upon $name")
        "An intrepid hero from $hometown shall some day " + listOf(
            "form an unlikely bond between two belligerent factions",
            "take the possession of otherworldly blade",
            "bring the gift of the creation back to the world",
            "best the world-eater"
        ).random()
    }

    val weaponName: String
        get() {
            // A way of checking whether the field is uninitialized yet.
            // NB: if this check is performed too often, it's a sign that the idea of
            // late initialization is probably compromised,
            // and maybe it's better to just make the field nullable.
            return if (::weapon.isInitialized) weapon.name else "nothing"
        }

    val title: String
        get() = "$name, the wielder of $weaponName"
}
