import java.awt.Point

fun main() {
    fun readDots(input: List<String>): Set<Point> {
        return input
            .takeWhile { it.isNotEmpty() }
            .map { it.split(',') }
            .map { Point(it.first().toInt(), it.last().toInt()) }
            .toSet()
    }

    fun readInstructions(input: List<String>): List<Point> {
        return input.takeLastWhile { it.isNotEmpty() }
            .map { it.split('=') }
            .map {
                val coordinate = it.first().split(' ').last()
                val value = it.last().toInt()

                if ("y" == coordinate) {
                    Point(0, value)
                } else {
                    Point(value, 0)
                }
            }
    }

    fun applyInstruction(dots: Set<Point>, instruction: Point): Set<Point> {
        if (instruction.x == 0) {
            return dots.map {
                if (it.y > instruction.y) {
                    Point(it.x, instruction.y * 2 - it.y)
                } else {
                    it
                }
            }.toSet()
        }

        return dots.map {
            if (it.x > instruction.x) {
                Point(instruction.x * 2 - it.x, it.y)
            } else {
                it
            }
        }.toSet()
    }

    fun part1(input: List<String>): Int {
        var dots: Set<Point> = readDots(input)
        val instructions: List<Point> = readInstructions(input)

        val firstInstruction = instructions.first()
        dots = applyInstruction(dots, firstInstruction)

        return dots.size
    }

    fun part2(input: List<String>) {
        var dots: Set<Point> = readDots(input)
        val instructions: List<Point> = readInstructions(input)

        instructions.forEach { instruction ->
            dots = applyInstruction(dots, instruction)
        }

        for (y in 0 .. dots.maxOf { it.y }) {
            for (x in 0 .. dots.maxOf { it.x }) {
                if (dots.contains(Point(x, y))) {
                    print('#')
                } else {
                    print(' ')
                }
            }
            println()
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day13_test")
    check(part1(testInput) == 17)
    part2(testInput)

    val input = readInput("Day13")
    println(part1(input))
    part2(input)
}
