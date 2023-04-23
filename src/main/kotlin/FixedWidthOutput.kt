import java.lang.StringBuilder
import java.nio.file.Files
import java.nio.file.Path

const val DEFAULT_WIDTH = 35

fun main(args: Array<String>) {

    val n = if (args.isNotEmpty()) args[0].toIntOrNull() ?: DEFAULT_WIDTH else DEFAULT_WIDTH

    val loremIpsum = Files.readString(Path.of("data/lorem-ipsum.txt"))
    checkNotNull(loremIpsum)
    println(loremIpsum)

    val loremIpsumSplit = loremIpsum.split(" ")

    println("Max line width: $n")
    val ruler = createRuler()
    println(ruler)

    textEqualWidth(loremIpsumSplit, n)
}

private fun createRuler(): String {
    val rulerBuilder = StringBuilder("")

    for (i in 1..9) {
        rulerBuilder.append("        ").append(10 * i)
    }
    rulerBuilder.append("\n")
    for (i in 1..9) {
        rulerBuilder.append("---------|")
    }
    return rulerBuilder.toString()
}

private fun textEqualWidth(words: List<String>, limit: Int) {
    if (words.isEmpty()) {
        return
    }
    val wordsBuffer = mutableListOf<String>()
    var tempLength = 0

    for (word in words) {
        if (tempLength + wordsBuffer.size + word.length > limit) {
            val spacedLine = spacedLine(wordsBuffer.toList(), limit)
            println(spacedLine)

            wordsBuffer.clear()
            tempLength = 0
        }
        tempLength += word.length
        wordsBuffer += word
    }
    println(wordsBuffer.joinToString(separator = " "))
}

private fun spacedLine(wordsInLine: List<String>, limit: Int): String {
    require(wordsInLine.isNotEmpty())
    if (wordsInLine.size == 1) {
        return wordsInLine.first()
    }
    val wordsTotalSpan = wordsInLine.sumOf { it.length }
    require(limit - wordsTotalSpan >= wordsInLine.size - 1)
    val n = wordsInLine.size
    val numberOfSpaces = limit - wordsTotalSpan
    if (numberOfSpaces == n - 1) {
        return wordsInLine.joinToString(separator = " ")
    }
    val minSpacesPerWord = numberOfSpaces / (n - 1)
    val residualSpaces = numberOfSpaces - minSpacesPerWord * (n - 1)

    val result = wordsInLine.foldIndexed(StringBuilder()) { ind, sb, word ->
        val spaceAmount = if (ind == n - 1) 0 else minSpacesPerWord + (if (ind < residualSpaces) 1 else 0)
        sb.append(word).append(" ".repeat(spaceAmount))
    }
    check(result.length == limit) { "Limit: $limit, line width: ${result.length}" }
    return result.toString()
}

private fun textLeftAligned(words: List<String>, limit: Int) {
    if (words.isEmpty()) {
        return
    }
    var tempLength = 0
    var isNewLine = true

    for (word in words) {
        if (tempLength + word.length + 1 > limit) {
            println()
            isNewLine = true
            tempLength = 0
        }

        tempLength += word.length
        if (!isNewLine) {
            tempLength++
            print(" ")
        }
        print(word)
        isNewLine = false
    }
}

private fun textLeftAlignedExceed(words: List<String>, limit: Int) {

    if (words.isEmpty()) {
        return
    }
    var tempLength = 0
    var isNewLine = true

    for (word in words) {
        tempLength += word.length + 1
        if (!isNewLine) {
            print(" ")
        }
        print(word)
        isNewLine = false
        if (tempLength > limit) {
            println()
            isNewLine = true
            tempLength = 0
        }
    }
}
