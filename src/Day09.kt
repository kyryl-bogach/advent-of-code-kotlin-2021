import java.awt.Point

fun main() {
    data class Adjacent(val top: Int, val down: Int, val left: Int, val right: Int)

    fun getAdjacentPoints(heightmap: Array<IntArray>, point: Point): Adjacent {
        val pointValue = heightmap[point.x][point.y]

        val up = heightmap.getOrNull(point.x - 1)?.getOrNull(point.y) ?: pointValue
        val down = heightmap.getOrNull(point.x + 1)?.getOrNull(point.y) ?: pointValue
        val left = heightmap.getOrNull(point.x)?.getOrNull(point.y - 1) ?: pointValue
        val right = heightmap.getOrNull(point.x)?.getOrNull(point.y + 1) ?: pointValue

        return Adjacent(up, down, left, right)
    }

    fun isLowestPoint(heightmap: Array<IntArray>, i: Int, j: Int): Boolean {
        val point = heightmap[i][j]

        val (up, down, left, right) = getAdjacentPoints(heightmap, Point(i, j))

        if (arrayOf(up, down, left, right).all { it == point }) {
            return false
        }

        return arrayOf(point, up, down, left, right).minOrNull() == point
    }

    fun part1(input: List<String>): Int {
        val heightmap = Array(input.size) { IntArray(input[0].length) { 0 } }
        input.forEachIndexed { index, it ->
            heightmap[index] = it.chunked(1).map { it.toInt() }.toIntArray()
        }

        var riskLevel = 0

        heightmap.forEachIndexed { i, row ->
            row.forEachIndexed { j, height ->
                if (isLowestPoint(heightmap, i, j)) {
                    riskLevel += height + 1
                }
            }
        }

        return riskLevel
    }

    fun calculateBasinSize(heightmap: Array<IntArray>, i: Int, j: Int): Int {
        val initialPoint = Point(i, j)
        val visited: MutableSet<Point> = mutableSetOf()
        val queue: MutableList<Point> = mutableListOf(initialPoint)

        while (queue.isNotEmpty()) {
            val node = queue.removeAt(0)
            val point = heightmap[node.x][node.y]

            if (visited.contains(node) || point == 9) continue

            val (up, down, left, right) = getAdjacentPoints(heightmap, node)
            if (arrayOf(up, down, left, right).all { it == point }) {
                continue
            }

            if (up > point) {
                queue.add(Point(node.x - 1, node.y))
            }

            if (down > point) {
                queue.add(Point(node.x + 1, node.y))
            }

            if (left > point) {
                queue.add(Point(node.x, node.y - 1))
            }

            if (right > point) {
                queue.add(Point(node.x, node.y + 1))
            }

            visited.add(node)
        }

        return visited.size
    }

    fun part2(input: List<String>): Int {
        val heightmap = Array(input.size) { IntArray(input[0].length) { 0 } }
        input.forEachIndexed { index, it ->
            heightmap[index] = it.chunked(1).map { it.toInt() }.toIntArray()
        }

        val basinSizes: MutableList<Int> = mutableListOf()

        heightmap.forEachIndexed { i, row ->
            row.forEachIndexed { j, _ ->
                if (isLowestPoint(heightmap, i, j)) {
                    val basinSize = calculateBasinSize(heightmap, i, j)
                    basinSizes.add(basinSize)
                }
            }
        }

        basinSizes.sortDescending()
        return basinSizes.take(3).fold(1){ acc, i -> acc * i }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 1134)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}
