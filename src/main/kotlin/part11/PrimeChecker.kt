package part11

import kotlin.math.abs
import kotlin.math.sqrt
import kotlin.system.measureNanoTime

fun main() {
    val columns = 20
    val firstPrimes = 100_000

    val knownPrimes = mutableListOf(2)

    repeat(10) {
        investigateSeq(firstPrimes)
        investigateSeqWithState(knownPrimes, firstPrimes)
        investigateList(knownPrimes.last(), firstPrimes)
        println("Known primes: ${knownPrimes.size}, last known prime: ${knownPrimes.last()}")
        println("==================================")
    }
}

fun investigateSeqWithState(knownPrimes: MutableList<Int>, firstPrimes: Int): Long {
    val primeSeqWithState: Sequence<Int>
    val timeSpent = measureNanoTime {
        primeSeqWithState = generateSequence (2) { value -> value + 1}
            .filter{ isPrimeStateful(it, knownPrimes) }
            .take(firstPrimes)
    }
    val toList = primeSeqWithState.toList()
    println("Time spent on seq with state: ${timeSpent}ns, primes: ${toList.size}, Last prime found: ${toList.last()}")
//    printTable(primeSeqWithState, columns, timeSpentOnStateSeq)
    return timeSpent
}

fun investigateSeq(firstPrimes: Int): Long {
    val primesSeq: Sequence<Int>
    val timeSpent = measureNanoTime {
        primesSeq = generateSequence (2) { value -> value + 1}
            .filter(::isPrimeSimple)
            .take(firstPrimes)
    }
    val toList2 = primesSeq.toList()
    println("Time spent on seq: ${timeSpent}ns, primes: ${toList2.size}, Last prime found: ${toList2.last()}")
//    printTable(primesSeq, columns, timeSpentOnSeq)
    return timeSpent
}

fun investigateList(lastNumber: Int, firstPrimes: Int): Long {
    val primesList: List<Int>
    val timeSpent = measureNanoTime {
        primesList = (1..lastNumber).toList()
            .filter(::isPrimeSimple)
            .take(firstPrimes)
    }
    println("Time spent on list: ${timeSpent}ns, primes: ${primesList.size}, Last prime found: ${primesList.last()}")
//    printTable(primes.asSequence(), columns, timeSpentOnList)
    return timeSpent
}

fun isPrimeSimple(n: Int): Boolean {
    val number = abs(n)
    if (number == 1 || number == 0) return false
    if (number == 2) return true

    var t = 2
    do {
        if (number % t == 0) return false
        t++
    } while (t <= sqrt(number.toFloat()))
    return true
}

fun isPrimeStateful(n: Int, knownPrimes: MutableList<Int>): Boolean {
    val number = abs(n)
    if (number == 1 || number == 0) return false
    if (number == 2) return true

    for (prime in knownPrimes) {
        if (number == prime) return true
        if (number % prime == 0) return false
        if (number < prime) return false
    }

    var temp = knownPrimes.last()
    while (temp <= number / temp) {
        if (number % temp == 0) return false
        temp++
    }

    knownPrimes.add(number)
    return true
}

fun printTable(numbers: Sequence<Int>, columns: Int, timeSpent: Long) {
    println("======================== Time spent: $timeSpent")
    numbers.forEachIndexed { i, n ->
        val formatted = String.format(" %4d", n)
        if (i % columns != 0)
            print("$formatted ")
        else {
            val row = String.format("%2d", 1 + i / columns)
            print("\n$row: $formatted")
        }
    }
    println("\n======================")
}
