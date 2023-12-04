import kotlin.math.pow
var day4Input : List<String> = listOf()
var cardCount: Int = 0

fun main() {
    // start time 18:23
    // finish time 18:42 part 1
    // finish time part 2 19:34

    fun part1(input: List<String>): Int {
        return input.sumOf { card ->
            val overlapCount = getCountOfWinningNumbers(card)
            if (overlapCount >= 1) {
                2.toThePowerOf(overlapCount - 1)
            } else {
                0
            }
        }
    }

    fun part2(input: List<String>): Int {
        cardCount = 0
        day4Input = input
        getCardCounts(input)
        return cardCount
    }

//     test if implementation meets criteria from the description, like:
    val testInput = readInput("day04_test")
    part1(testInput).println()
    check(part1(testInput) == 13)

    val input = readInput("day04")
    part1(input).println()
    check(part1(input) == 23235)

    val part2TestInput = readInput("day04_test") // it's the same test input
    part2(part2TestInput).println()
    check(part2(part2TestInput) == 30)

    val part2 = part2(input)
    part2.println()
    check(part2 == 5920640)
}

// HELPER FUNCTIONS
/**
 * Integer power using [Double.pow]
 */
infix fun Int.toThePowerOf(exponent: Int): Int = toDouble().pow(exponent).toInt()

/** returns count of matching numbers **/
fun getCountOfWinningNumbers(card: String): Int {
    val withoutCardNumber = card.split(":")[1].split("|")
    val winningNumbers = withoutCardNumber.first().split(" ").filterNot { it == "" }.map { number -> number.toInt() }.toSet()
    val cardNumbers = withoutCardNumber.last().split(" ").filterNot { it == "" }.map { number -> number.toInt() }.toSet()

    return winningNumbers.intersect(cardNumbers).size
}

fun getCardCounts(cards: List<String>): Int {
    return cards.sumOf {card ->
        cardCount++
        val cardNumber = card.split(":")[0].split(" ").filterNot { it == "" }.last().toInt()
        val winCount = getCountOfWinningNumbers(card)
        if(winCount == 0) {
            winCount
        } else {
            val extraCards = (cardNumber..<cardNumber + winCount).map { cardToAdd ->
                day4Input[cardToAdd]
            }
            getCardCounts(extraCards)
        }
        winCount
    }
}


tailrec fun factorial(n: Int, run: Int = 1): Long {
    return if (n == 1) run.toLong() else factorial(n-1, run*n)
}