import java.util.*

fun main() {
    // HELPERS
    val validNumbers = listOf("one", "two", "three", "four", "five",
            "six", "seven", "eight", "nine", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9")

    fun part1(input: List<String>): Int {
        // return input.size
        var sum = 0
        input.forEach { line ->
            val digitsInLine: String = line.filter { it.isDigit() }
            val firstDigit = digitsInLine.first()
            val lastDigit = digitsInLine.last()
            val lineValue = "$firstDigit" + "$lastDigit"
            sum += lineValue.toInt()
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        input.forEach { line ->
            val firstDigit = line.findAnyOf(strings = validNumbers, ignoreCase = true)?.second?.convertToInt()
            val lastDigit = line.findLastAnyOf(strings = validNumbers, ignoreCase = true)?.second?.convertToInt()
            val lineValue = "$firstDigit" + "$lastDigit"
            sum += lineValue.toInt()
        }
        return sum
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day01_test")
    part1(testInput).println()
    check(part1(testInput) == 142)

    val input = readInput("day01")
    part1(input).println()
    check(part1(input) == 54388)

    val part2TestInput = readInput("day01_part2_test")
    part2(part2TestInput).println()
    check(part2(part2TestInput) == 281)


    part2(input).println()
    check(part2(input) == 53515)
}

fun String.convertToInt(): Int {
    return when (this.lowercase(Locale.getDefault())) {
        "one" -> 1
        "two" -> 2
        "three" -> 3
        "four" -> 4
        "five" -> 5
        "six" -> 6
        "seven" -> 7
        "eight" -> 8
        "nine" -> 9
        else -> this.toInt()
    }
}
