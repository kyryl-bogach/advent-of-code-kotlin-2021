fun main() {
    fun countInputIncreased(input: List<Int>): Int {
        var counter = 0
        var previous = input[0]

        for (index in 1 until input.size) {
            val currentValue = input[index]

            if (previous < currentValue) {
                counter++
            }

            previous = currentValue
        }

        return counter
    }

    fun countWindowedInputIncreased(input: List<Int>): Int {
        var counter = 0
        var previousSum = input[0] + input[1] + input[2]

        for (index in 1 until input.size - 2) {
            val currentSum = input[index] + input[index + 1] + input[index + 2]

            if (currentSum > previousSum) {
                counter++
            }

            previousSum = currentSum
        }

        return counter
    }

    fun part1(input: List<String>): Int {
        return countInputIncreased(input.map{ it.toInt() })
    }

    fun part2(input: List<String>): Int {
        return countWindowedInputIncreased(input.map{ it.toInt() })
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
