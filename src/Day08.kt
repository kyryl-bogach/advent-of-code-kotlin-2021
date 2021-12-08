fun main() {
    fun part1(input: List<String>): Int {
        var ones = 0
        var fours = 0
        var sevens = 0
        var eights = 0

        input.forEach { line ->
            val output = line.split(" | ")[1].split(' ')

            ones += output.count { it.length == 2}
            fours += output.count { it.length == 4}
            sevens += output.count { it.length == 3}
            eights += output.count { it.length == 7}
        }

        return ones + eights + fours + sevens
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        input.forEach { line ->
            val (patternsString, outputString) = line.split(" | ")
            val patterns = patternsString.split(' ')
            val output = outputString.split(' ')

            val onePattern = patterns.find { it.length == 2 }!!
            val fourPattern = patterns.find { it.length == 4}!!.filter { it != onePattern[0] && it != onePattern[1]}
            val ninePattern = patterns.find { it.length == 6 && it.containsAllChars(onePattern) && it.containsAllChars(fourPattern)}!!

            var number = ""

            output.forEach { entry ->
                when (entry.length) {
                    2 -> number = number.plus('1')
                    3 -> number = number.plus('7')
                    4 -> number = number.plus('4')
                    5 -> {
                        // 2,3,5
                        if (entry.containsAllChars(onePattern)) {
                            number = number.plus('3')
                        } else {
                            var entryWith = entry
                            entryWith = if (entryWith.contains(onePattern[0])) {
                                entryWith.plus(onePattern[1])
                            } else {
                                entryWith.plus(onePattern[0])
                            }

                            number = if (entryWith.containsAllChars(ninePattern) && entryWith.containsAllChars(fourPattern)) {
                                number.plus('5')
                            } else {
                                number.plus('2')
                            }
                        }
                    }
                    6 -> {
                        // 0,6,9
                        number = if (entry.containsAllChars(ninePattern)) {
                            number.plus('9')
                        } else if (entry.containsAllChars(onePattern)) {
                            number.plus('0')
                        } else {
                            number.plus('6')
                        }
                    }
                    7 -> number = number.plus('8')
                }
            }

            sum += number.toInt()
        }

        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 26)
    check(part2(testInput) == 61229)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}

private fun String.containsAllChars(onePattern: String): Boolean {
    onePattern.forEach {
        if (! this.contains(it)) {
            return false
        }
    }

    return true
}
