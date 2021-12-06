fun main() {

    // Function copied from stackoverflow: https://stackoverflow.com/a/52892357
    fun LongArray.leftShift(d: Int) {
        val n = d % this.size  // just in case
        if (n == 0) return  // no need to shift

        val left = this.copyOfRange(0, n)
        val right = this.copyOfRange(n, this.size)
        System.arraycopy(right, 0, this, 0, right.size)
        System.arraycopy(left, 0, this, right.size, left.size)
    }

    fun part1(input: List<String>): Int {
        var lanternFishes: MutableList<Int> = mutableListOf()
        input.first().split(',').forEach {
            lanternFishes.add(it.toInt())
        }

        for (day in 0 until 80) {
            var ceros = 0
            lanternFishes = lanternFishes.map(fun(it: Int): Int {
                if (it == 0) {
                    ceros++
                    return 6
                }

                return it - 1
            }).toMutableList()

            for (i in 0 until ceros) {
                lanternFishes.add(8)
            }
        }

        return lanternFishes.size
    }

    fun part2(input: List<String>): Long {
        val timers = longArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)
        input.first().split(',').forEach {
            timers[it.toInt()] = timers[it.toInt()] + 1
        }

        for (day in 0 until 256) {
            val safe = timers[0]
            timers.leftShift(1)
            timers[6] = timers[6] + safe
        }

        return timers.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 5934)
    check(part2(testInput) == 26984457539L)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
