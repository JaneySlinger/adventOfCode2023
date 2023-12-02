import java.util.*

const val TOTAL_RED = 12
const val TOTAL_GREEN = 13
const val TOTAL_BLUE = 14

fun main() {
    // start time 10:15
    // HELPERS

    // exampleGame "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"


    fun part1(input: List<String>): Int {
        var sum = 0
        input.forEachIndexed { index, game ->
            val gameNumber = index + 1
            val gameWithoutTitle = game.split(":").drop(1).joinToString()
            val gameDraws = gameWithoutTitle.split(";")
            val isValidGame = gameDraws.map { draw ->
                // draw is e.g. 6 red, 1 blue, 3 green
                isValidDraw(draw)
            }.all { it }
            if(isValidGame) {
                sum += gameNumber
            }
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        TODO()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day02_test")
    part1(testInput).println()
    check(part1(testInput) == 8)

    val input = readInput("day02")
    part1(input).println()
    // check(part1(input) == 54388)
//
//    val part2TestInput = readInput("day02_part2_test")
//    part2(part2TestInput).println()
//    check(part2(part2TestInput) == 281)
//
//    part2(input).println()
//    check(part2(input) == 53515)
}

fun isValidDraw(draw: String): Boolean {
    // draw is e.g. 6 red, 1 blue, 3 green
    val cubes = draw.split(",")
    val mappedCubes = cubes.map { cubeColor ->
        val split = cubeColor.split(" ")
        Pair(split.last(), split[1].toInt())
    }
    return mappedCubes.map { (color, number) ->
        when {
            color == "red" && number > TOTAL_RED -> false
            color == "blue" && number > TOTAL_BLUE -> false
            color == "green" && number > TOTAL_GREEN -> false
            else -> true
        }
    }.all { it }
}