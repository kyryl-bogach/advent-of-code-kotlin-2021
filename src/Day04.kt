fun main() {
    fun getNumbers(input: List<String>): List<Int> {
        return input.first().split(',').map { it.toInt() }
    }

    fun treatGridLine(gridLine: String): IntArray {
        return gridLine.trim().split("\\s+".toRegex()).map { it.toInt() }.toIntArray()
    }

    fun getGridsAndMarks(input: List<String>): Pair<MutableList<Array<IntArray>>, MutableList<Array<BooleanArray>>> {
        val grids: MutableList<Array<IntArray>> = arrayListOf()
        val marks: MutableList<Array<BooleanArray>> = arrayListOf()

        for (i in 2 until input.size step 6) {
            grids.add(
                arrayOf(
                    treatGridLine(input[i]),
                    treatGridLine(input[i + 1]),
                    treatGridLine(input[i + 2]),
                    treatGridLine(input[i + 3]),
                    treatGridLine(input[i + 4]),
                )
            )
            marks.add(
                arrayOf(
                    booleanArrayOf(false, false, false, false, false),
                    booleanArrayOf(false, false, false, false, false),
                    booleanArrayOf(false, false, false, false, false),
                    booleanArrayOf(false, false, false, false, false),
                    booleanArrayOf(false, false, false, false, false)
                )
            )
        }

        return Pair(grids, marks)
    }

    // Is the vertical or horizontal checked?
    fun checkMarksForIndex(marks: Array<BooleanArray>, i: Int, j: Int): Boolean {
        return marks[0][j] && marks[1][j] && marks[2][j] && marks[3][j] && marks[4][j]
                || marks[i][0] && marks[i][1] && marks[i][2] && marks[i][3] && marks[i][4]
    }

    fun sumNotMarkedValuesOfGrid(grid: Array<IntArray>, marks: Array<BooleanArray>): Int {
        var sum = 0

        for (i in 0 until 5) {
            for (j in 0 until 5) {
                if (!marks[i][j]) {
                    sum += grid[i][j]
                }
            }
        }

        return sum
    }

    fun part1(input: List<String>): Int {
        val numbers = getNumbers(input)
        val (grids, marks) = getGridsAndMarks(input)

        numbers.forEach { number ->
            grids.forEachIndexed { gridIndex, grid ->
                for (i in 0 until 5) {
                    for (j in 0 until 5) {
                        if (grid[i][j] == number) {
                            marks[gridIndex][i][j] = true

                            // Grid marked!
                            if (checkMarksForIndex(marks[gridIndex], i, j)) {
                                return number * sumNotMarkedValuesOfGrid(grid, marks[gridIndex])
                            }
                        }
                    }
                }
            }
        }

        return -1
    }

    fun part2(input: List<String>): Int {
        val numbers = getNumbers(input)
        val (grids, marks) = getGridsAndMarks(input)

        val gridsChecked: MutableSet<Int> = mutableSetOf()

        numbers.forEach { number ->
            for (gridIndex in 0 until grids.size) {
                val grid = grids[gridIndex]

                if (gridsChecked.contains(gridIndex)) {
                    continue
                }

                for (i in 0 until 5) {
                    for (j in 0 until 5) {
                        if (grid[i][j] == number) {
                            marks[gridIndex][i][j] = true

                            if (checkMarksForIndex(marks[gridIndex], i, j)) {
                                gridsChecked.add(gridIndex)

                                // Last grid
                                if (gridsChecked.size == grids.size) {
                                    return number * sumNotMarkedValuesOfGrid(grid, marks[gridIndex])
                                }
                            }
                        }
                    }
                }
            }
        }

        return -1
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
