fun main() {
    var solutions = 0
    val paths: MutableSet<Pair<String, String>> = mutableSetOf()

    fun findAdjacent(position: String): List<String> {
        return paths
            .filter { it.first == position || it.second == position }
            .map { if (it.first == position) it.second else it.first }
    }

    fun backtrack(currentSolution: List<String>) {
        val lastNode = currentSolution.last()

        if (lastNode == "end") {
            solutions++
            return
        }

        val adjacent = findAdjacent(lastNode)
            .filter { it != "start" }
            .filter { it != lastNode }
            .toMutableList()

        adjacent.forEach { adj ->
            val currentSolutionClone = currentSolution.toMutableList()
            currentSolutionClone.add(adj)

            if (!currentSolutionClone
                    .filter { it.isSmallCave() }
                    .groupingBy { it }
                    .eachCount()
                    .any { it.value > 2 }
                && currentSolutionClone
                    .filter { it.isSmallCave() }
                    .groupingBy { it }
                    .eachCount()
                    .count {it.value == 2} <= 1
            ) {
                backtrack(currentSolutionClone)
            }
        }
    }

    fun part1(input: List<String>): Int {
        input.forEach {
            val (from, to) = it.split('-')
            paths.add(Pair(from, to))
        }

        backtrack(listOf("start"))

        return solutions
    }

    // test if implementation meets criteria from the description, like:
    // val testInput = readInput("Day12_test")
    //check(part1(testInput) == 36)

    val input = readInput("Day12")
    println(part1(input))
}

private fun String.isSmallCave(): Boolean {
    return this.lowercase() == this && this != "start" && this != "end"
}
