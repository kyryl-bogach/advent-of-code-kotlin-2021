import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val numbers = input.first().split(',').map { it.toInt() }
        var min = Int.MAX_VALUE

        val minN = numbers.minOrNull()!!
        val maxN = numbers.maxOrNull()!!

        for (n1 in minN..maxN) {
            var fuel = 0

            for (n2 in numbers) {
                fuel += abs(n1 - n2)
            }

            if (fuel < min) {
                min = fuel
            }
        }

        return min
    }

    fun part2(input: List<String>): Int {
        val numbers = input.first().split(',').map { it.toInt() }
        var min = Int.MAX_VALUE

        val minN = numbers.minOrNull()!!
        val maxN = numbers.maxOrNull()!!

        for (n1 in minN..maxN) {
            var fuel = 0

            for (n2 in numbers) {
                val cost = abs(n1 - n2)
                fuel += (cost * (cost + 1)) / 2 // (n * (n+1)) / 2
            }

            if (fuel < min) {
                min = fuel
            }
        }

        return min
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
