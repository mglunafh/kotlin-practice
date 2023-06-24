package part16

data class Coordinate(val x: Int, val y: Int) {

    operator fun plus(other: Coordinate) = Coordinate(x + other.x, y + other.y)

// Data classes provide
// 1. toString: (Coordinate(x=0, y=0)
// 2. equals: structural equality of the fields declared in the main constructor
// 3. hashCode
// 4. Convenient copy method with the parameterized arguments
//      val duplicate = coordinate.copy()
//      val leftDuplicate = coordinate.copy(x = 4)
//      val top Duplicate = coordinate.copy(y = 5)
//
// 5. Destructuring syntax
}

class PlayerScore(val experience: Int, val level: Int) {
    // define destructuring syntax for a regular class
    operator fun component1() = experience
    operator fun component2() = level

    companion object {

        private val TOP_SCORE = PlayerScore(1250, 5)

        fun printTopScore() {
            val (exp, level) = TOP_SCORE
            println("Max experience gained: $exp, Max level gained: $level")
        }
    }
}

enum class Direction(
    private val directionCoordinate: Coordinate
)
{
    North(Coordinate(0, -1)),
    East(Coordinate(1, 0)),
    South(Coordinate(0,1)),
    West(Coordinate(-1, 0));

    fun updateCoordinate(coordinate: Coordinate) = directionCoordinate + coordinate
}
