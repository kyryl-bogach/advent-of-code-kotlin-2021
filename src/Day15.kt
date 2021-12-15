import java.awt.Point

fun main() {
    fun readRiskMap(input: List<String>): Array<IntArray> {
        val riskMap = Array(input.size) { IntArray(input[0].length) { 0 } }
        input.forEachIndexed { index, it ->
            riskMap[index] = it.chunked(1).map { it.toInt() }.toIntArray()
        }

        return riskMap
    }

    fun getPointWithSmallestDistance(queue: MutableSet<Point>, distances: Array<IntArray>): Point {
        var min = Int.MAX_VALUE
        var point: Point? = null

        queue.forEach {
            if (distances[it.x][it.y] < min) {
                min = distances[it.x][it.y]
                point = Point(it.x, it.y)
            }
        }

        return point!!
    }

    fun calculateNeighbors(queue: MutableSet<Point>, point: Point): MutableSet<Point> {
        val neighbor: MutableSet<Point> = mutableSetOf()

        val coordinates = arrayOf(
            intArrayOf(0, 1),  // up
            intArrayOf(0, -1), // down
            intArrayOf(-1, 0), // left
            intArrayOf(1, 0),  // right
        )

        coordinates.forEach { coordinate ->
            val pointCoordinate = Point(point.x + coordinate[0], point.y + coordinate[1])

            if (queue.contains(pointCoordinate)) {
                neighbor.add(pointCoordinate)
            }
        }

        return neighbor
    }

    fun part1(input: List<String>): Int {
        val riskMap = readRiskMap(input)

        val distances = Array(input.size) { IntArray(input[0].length) { 0 } }
        //val previous: MutableMap<Point, Point?> = mutableMapOf()
        val queue: MutableSet<Point> = mutableSetOf()

        riskMap.forEachIndexed { rowIndex, row ->
            distances[rowIndex] = IntArray(row.size) { Int.MAX_VALUE }

            riskMap.forEachIndexed { columnIndex, _ ->
                val point = Point(rowIndex, columnIndex)

                //previous[point] = null
                queue.add(point)
            }
        }

        distances[0][0] = 0

        while (queue.isNotEmpty()) {
            val point = getPointWithSmallestDistance(queue, distances)
            queue.remove(point)

            calculateNeighbors(queue, point).forEach { neighbor ->
                val risk = distances[point.x][point.y] + riskMap[point.x][point.y]

                if (risk < distances[neighbor.x][neighbor.y]) {
                    distances[neighbor.x][neighbor.y] = risk
                    //previous[Point(neighbor.x, neighbor.y)] = point
                }
            }
        }

        return distances.last().last()
    }

    fun part2(input: List<String>): Int {
        val originalRiskMap = readRiskMap(input)
        val riskMap = Array(input.size * 5) { IntArray(input[0].length * 5) { 0 } }

        originalRiskMap.forEachIndexed { i, _ ->
            originalRiskMap.forEachIndexed { j, _ ->
                riskMap[i][j] = originalRiskMap[i][j]
            }
        }

        for (i in 0 until originalRiskMap.size * 5) {
            for (j in originalRiskMap[0].size until originalRiskMap[0].size * 5) {
                riskMap[i][j] = riskMap[i][j - originalRiskMap[0].size] + 1

                if (riskMap[i][j] > 9) {
                    riskMap[i][j] = 1
                }
            }
        }

        for (i in input.size until originalRiskMap.size * 5) {
            for (j in 0 until originalRiskMap[0].size * 5) {
                riskMap[i][j] = riskMap[i - originalRiskMap.size][j] + 1

                if (riskMap[i][j] > 9) {
                    riskMap[i][j] = 1
                }
            }
        }

        val distances = Array(input.size * 5) { IntArray(input[0].length * 5) { 0 } }
        //val previous: MutableMap<Point, Point?> = mutableMapOf()
        val queue: MutableSet<Point> = mutableSetOf()

        distances.forEachIndexed { rowIndex, row ->
            distances[rowIndex] = IntArray(row.size) { Int.MAX_VALUE }

            distances.forEachIndexed { columnIndex, _ ->
                val point = Point(rowIndex, columnIndex)

                //previous[point] = null
                queue.add(point)
            }
        }

        distances[0][0] = 0

        while (queue.isNotEmpty()) {
            val point = getPointWithSmallestDistance(queue, distances)
            queue.remove(point)

            calculateNeighbors(queue, point).forEach { neighbor ->
                val risk = distances[point.x][point.y] + riskMap[neighbor.x][neighbor.y]

                if (risk < distances[neighbor.x][neighbor.y]) {
                    distances[neighbor.x][neighbor.y] = risk
                    //previous[Point(neighbor.x, neighbor.y)] = point
                }

                if (point.x == input[0].length * 5 - 1 && point.y == input.size * 5 - 1) {
                    return distances[point.x][point.y]
                }
            }
        }

        return distances.last().last()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day15_test")
    check(part1(testInput) == 40)
    check(part2(testInput) == 315)

    val input = readInput("Day15")
    println(part1(input))
    println(part2(input))
}
