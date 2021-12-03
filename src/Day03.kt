fun main() {
    fun hasMoreZeros(input: List<Char>): Boolean {
        return input.count { '0' == it } > (input.size / 2)
    }

    fun parseBinary(input: String): Int
    {
        return Integer.parseInt(input, 2)
    }

    fun part1(input: List<String>): Int {
        var gamma = ""
        var epsilon = ""

        for (i in input.first().indices)
        {
            val zeroRate = hasMoreZeros(input.map { it[i] })
            gamma += if (zeroRate) "0" else "1"
            epsilon += if (zeroRate) "1" else "0"
        }

        return parseBinary(gamma) * parseBinary(epsilon)
    }

    fun part2(input: List<String>): Int {
        var oxygen = input
        var co2 = input

        for (i in input.first().indices)
        {
            if (oxygen.size > 1) {
                val oxygenSelectedDigits = oxygen.map { it[i] }
                oxygen = oxygen.filter { if (hasMoreZeros(oxygenSelectedDigits)) '0' == it[i] else '1' == it[i] }
            }

            if (co2.size > 1) {
                val co2SelectedDigits = co2.map { it[i] }
                co2 = co2.filter { if (hasMoreZeros(co2SelectedDigits)) '1' == it[i] else '0' == it[i] }
            }
        }

        return parseBinary(oxygen.first()) * parseBinary(co2.first())
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
