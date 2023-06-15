package part13

class Sword(name: String) {
    var name = name
        get() = "The Legendary $field"
        set(value) {
            field = value.lowercase().reversed().uppercase()
        }

    init {
        // the actual fields are accessible only in getter and setter,
        // everything else is a getter/setter method calls.
        this.name = name
    }
}
