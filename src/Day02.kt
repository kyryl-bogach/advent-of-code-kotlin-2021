fun main() {

    fun part1(input: List<String>): Int {
        var horizontal = 0
        var depth = 0

        input.forEach {
            val parts = it.split(' ')
            val operation = parts[0]
            val step = parts[1].toInt()

            when (operation) {
                "forward" -> horizontal += step
                "up" -> depth -= step
                "down" -> depth += step
             }
        }

        return horizontal * depth
    }

    fun part2(input: List<String>): Int {
        var horizontal = 0
        var depth = 0
        var aim = 0

        input.forEach {
            val parts = it.split(' ')
            val operation = parts[0]
            val step = parts[1].toInt()

            when (operation) {
                "forward" -> {
                    horizontal += step
                    depth += aim * step
                }
                "up" -> {
                    aim -= step
                }
                "down" -> {
                    aim += step
                }
            }
        }

        return horizontal * depth
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
