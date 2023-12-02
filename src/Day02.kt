const val TOTAL_RED = 12
const val TOTAL_GREEN = 13
const val TOTAL_BLUE = 14

fun main() {
    // start time 10:15
    // finish time 11:25
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
            if (isValidGame) {
                sum += gameNumber
            }
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        // per game, find max red, max blue, max green
        return input.sumOf { game ->
            val gameWithoutTitle = game.split(":").drop(1).joinToString()
            // for each game, find max of color for each draw, find max of the three draws
            val gameDraws = gameWithoutTitle.split(";")
            val mappedGame: MappedGame = gameDraws.flatMap { draw ->
                //val drawType = Draw(red = , blue =, green = )
                // draw is e.g. 6 red, 1 blue, 3 green
                val cubes = draw.split(",")
                cubes.map { cubeColor ->
                    val split = cubeColor.split(" ")
                    mapOf(split.last() to split[1].toInt())
                }
            }
            val maxRed = mappedGame.getMaxByColor("red")
            val maxBlue = mappedGame.getMaxByColor("blue")
            val maxGreen = mappedGame.getMaxByColor("green")
            maxRed * maxBlue * maxGreen
        }
    }

//     test if implementation meets criteria from the description, like:
    val testInput = readInput("day02_test")
    part1(testInput).println()
    check(part1(testInput) == 8)

    val input = readInput("day02")
    part1(input).println()
    check(part1(input) == 2528)

    val part2TestInput = readInput("day02_test") // it's the same test input
    part2(part2TestInput).println()
    check(part2(part2TestInput) == 2286)
//
    part2(input).println()
    check(part2(input) == 67363)
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

fun MappedGame.getMaxByColor(color: String): Int = (this.maxByOrNull { it[color] ?: 0 })?.getValue(color)?: 0

typealias MappedGame = List<Map<String, Int>>