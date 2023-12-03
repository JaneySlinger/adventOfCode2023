var grid: Grid = mutableListOf()
var globalInput: List<String> = listOf()

fun main() {
    // start time 11:20
    // finish time 13:25 to do part one
    // finish time part two 14:07

    fun part1(input: List<String>): Int {
        var sum = 0
        grid = processGrid(input)
        globalInput = input
        grid.map { row ->
            row.map { gridEntry ->
                if (gridEntry is GridEntry.PART_NUMBER && !gridEntry.checked
                ) {
                    if (gridEntry.isAdjacentToSymbol()) {
                        // process part number to collect all digits and then add to sum
                        val partValue = gridEntry.processPartNumber()
                        sum += partValue
                    }
                }
            }
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        grid = processGrid(input)
        globalInput = input

        // find gears adjacent to two numbers
        // find what the numbers are
        // add them up
        grid.map { row ->
            row.map { gridEntry ->
                if (gridEntry is GridEntry.SYMBOL && gridEntry.isGear) {
                    sum += gridEntry.findGearRatio()
                }
            }
        }
        return sum
    }

//     test if implementation meets criteria from the description, like:
    val testInput = readInput("day03_test")
    part1(testInput).println()
    check(part1(testInput) == 4361)

    val input = readInput("day03")
    part1(input).println()
    check(part1(input) == 535351)

    val part2TestInput = readInput("day03_test") // it's the same test input
    part2(part2TestInput).println()
    check(part2(part2TestInput) == 467835)

    part2(input).println()
//    check(part2(input) == 67363)
}

// HELPER FUNCTIONS
fun processGrid(input: List<String>): Grid {
    // for each row in the grid, convert to row and grid types where the entry is either a symbol, full stop, or a part number
    val processedGrid = input.mapIndexed { index, row ->
        row.mapIndexed { indexInRow, character ->
            when {
                character == '.' -> GridEntry.FULL_STOP(rowIndex = index, indexInRow = indexInRow)
                character.isSymbol() -> GridEntry.SYMBOL(isGear = character == '*', rowIndex = index, indexInRow = indexInRow)
                else -> GridEntry.PART_NUMBER(number = character.toString(), rowIndex = index, indexInRow = indexInRow)
            }
        }.toMutableList()
    }
    return processedGrid
}

fun GridEntry.PART_NUMBER.isAdjacentToSymbol(): Boolean {
    // if above, below, horizontal, diagonal is GridEntry.SYMBOL then true
//     (-1, -1) (-1, 0) (-1, +1)
//     (0, -1)     X    (0, +1)
//     (+1, -1) (+1, 0) (+1, +1)

    return getValidGridEntry(rowIndex - 1, indexInRow - 1) is GridEntry.SYMBOL ||
        getValidGridEntry(rowIndex - 1, indexInRow) is GridEntry.SYMBOL ||
        getValidGridEntry(rowIndex - 1, indexInRow + 1) is GridEntry.SYMBOL ||
        getValidGridEntry(rowIndex, indexInRow - 1) is GridEntry.SYMBOL ||
        getValidGridEntry(rowIndex, indexInRow + 1) is GridEntry.SYMBOL ||
        getValidGridEntry(rowIndex + 1, indexInRow - 1) is GridEntry.SYMBOL ||
        getValidGridEntry(rowIndex + 1, indexInRow) is GridEntry.SYMBOL ||
        getValidGridEntry(rowIndex + 1, indexInRow + 1) is GridEntry.SYMBOL
}

fun GridEntry.SYMBOL.findGearRatio(): Int {
    val surroundingSquares = listOf(
        getValidGridEntry(rowIndex - 1, indexInRow - 1),
        getValidGridEntry(rowIndex - 1, indexInRow),
        getValidGridEntry(rowIndex - 1, indexInRow + 1),
        getValidGridEntry(rowIndex, indexInRow - 1),
        getValidGridEntry(rowIndex, indexInRow + 1),
        getValidGridEntry(rowIndex + 1, indexInRow - 1),
        getValidGridEntry(rowIndex + 1, indexInRow),
        getValidGridEntry(rowIndex + 1, indexInRow + 1))

    val surroundingNumberRefs: List<GridEntry> = surroundingSquares
        .filterNotNull()
        .filter {
            it is GridEntry.PART_NUMBER && !it.checked
        }
//    surroundingNumberRefs.println()
    val resultsMap = surroundingNumberRefs.mapNotNull {
        if (it is GridEntry.PART_NUMBER && !it.checked) {
            it.processPartNumber()
        } else null
    }
//    resultsMap.println()
    return if (resultsMap.size == 2) {
        resultsMap.first() * resultsMap.last()
    } else {
        0
    }
}

fun getValidGridEntry(rowIndex: Int, indexInRow: Int): GridEntry? {
    return grid.getOrNull(rowIndex)?.getOrNull(indexInRow)
}

fun GridEntry.PART_NUMBER.processPartNumber(): Int {
    val row = globalInput[rowIndex]
    val regex = Regex(pattern = "[0-9]+")
    val results = regex.findAll(input = row)

    //return the value to be summed
    val result = results.map { it.range to it.value.toInt() }.toList().first { it.first.contains(this.indexInRow) }
    val range = result.first
    range.forEach { indexInRowInRange ->
        val item = grid[rowIndex][indexInRowInRange]

        if (item is GridEntry.PART_NUMBER) {
            item.checked = true
            grid[rowIndex][indexInRowInRange] = item
        }
    }

    return result.second
}

fun markNumberAsChecked(range: IntRange, rowIndex: Int) {
    range.forEach { indexInRowInRange ->
        val item = grid[rowIndex][indexInRowInRange]

        if (item is GridEntry.PART_NUMBER) {
            item.checked = true
            grid[rowIndex][indexInRowInRange] = item
        }
    }
}


/** Check if the character is a symbol - any non digit character that isn't a full stop **/
fun Char.isSymbol(): Boolean = !(this.isDigit() || this == '.')

typealias Grid = List<Row>
typealias Row = MutableList<GridEntry>
typealias PartNumber = String

sealed class GridEntry {
    data class SYMBOL(
        val isGear: Boolean,
        val rowIndex: Int,
        val indexInRow: Int,
    ) : GridEntry()

    data class FULL_STOP(
        val rowIndex: Int,
        val indexInRow: Int,
    ) : GridEntry()

    data class PART_NUMBER(
        val number: PartNumber,
        val rowIndex: Int,
        val indexInRow: Int,
        var checked: Boolean = false
    ) : GridEntry()
}