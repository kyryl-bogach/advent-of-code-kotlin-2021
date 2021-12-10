fun main() {
    fun calculateIllegalCharacterPoints(pattern: String): Int {
        val queue: MutableList<Char> = mutableListOf()

        pattern.forEach { character ->
            when (character) {
                '(', '[', '{', '<' -> queue.add(character)
                ')' -> if ('(' != queue.removeLast()) {
                    return 3
                }
                ']' -> if ('[' != queue.removeLast()) {
                    return 57
                }
                '}' -> if ('{' != queue.removeLast()) {
                    return 1197
                }
                '>' -> if ('<' != queue.removeLast()) {
                    return 25137
                }
            }
        }

        return 0
    }

    fun calculateMissingCharacterPoints(pattern: String): Long {
        val queue: MutableList<Char> = mutableListOf()

        pattern.forEach { character ->
            when (character) {
                '(', '[', '{', '<' -> queue.add(character)
                ')', ']', '}', '>' -> queue.removeLast()
            }
        }

        queue.reverse()

        var total = 0L
        queue.forEach {
            total *= 5
            total += when (it) {
                '(' -> 1
                '[' -> 2
                '{' -> 3
                '<' -> 4
                else -> 0
            }
        }

        return total
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { calculateIllegalCharacterPoints(it) }
    }

    fun part2(input: List<String>): Long {
        val incompleteSequences = input.filter { calculateIllegalCharacterPoints(it) == 0 }

        return incompleteSequences
            .map { calculateMissingCharacterPoints(it) }
            .sortedDescending()[incompleteSequences.size / 2]
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 26397)
    check(part2(testInput) == 288957L)

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}
