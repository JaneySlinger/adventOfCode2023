package _2024

import println
import readInput
import kotlin.math.absoluteValue

fun main() {
    fun getLeftList(input: List<String>): List<Int> = input.map { it.split(" ").first().toInt() }
    fun getRightList(input: List<String>): List<Int> = input.map { it.split(" ").last().toInt()}

    fun part1(input: List<String>): Int {
        // format into two sorted lists
        // sum distance between corresponding values

        val zippedList = getLeftList(input).sorted().zip(getRightList(input).sorted())
        return zippedList.sumOf { (it.first - it.second).absoluteValue }
    }

    fun part2(input: List<String>): Int {
        // how often each number from the left list appears in the right list.
        // adding up each number in the left list after multiplying it by the number of times that number appears in the right list
        val frequencyMap = getRightList(input).groupingBy { it }.eachCount()

        return getLeftList(input).sumOf { it * (frequencyMap[it] ?: 0) }
    }


    // test if implementation meets criteria from the description:
    val testInput = readInput("2024_day01_test")
    part1(testInput).println()
    check(part1(testInput) == 11)

    val input = readInput("2024_day01")
    part1(input).println()
    check(part1(input) == 2742123)

    part2(testInput).println()
    check(part2(testInput) == 31)

    part2(input).println()
    check(part2(input) == 21328497)
}