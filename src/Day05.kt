import java.awt.geom.Line2D
import java.awt.geom.Point2D

fun main() {
    fun getPointsForLine(line: Line2D): List<Point2D> {
        val points: MutableList<Point2D> = arrayListOf()

        if (line.x1 == line.x2) {
            val min = line.y1.coerceAtMost(line.y2).toInt()
            val max = line.y1.coerceAtLeast(line.y2).toInt()
            for (i in min .. max) {
                points.add(Point2D.Float(line.x1.toFloat(), i.toFloat()))
            }
        } else if (line.y1 == line.y2) {
            val min = line.x1.coerceAtMost(line.x2).toInt()
            val max = line.x1.coerceAtLeast(line.x2).toInt()
            for (i in min .. max) {
                points.add(Point2D.Float(i.toFloat(), line.y1.toFloat()))
            }
        } else {
            // diagonal
            var x = line.x1
            var y = line.y1

            do {
                points.add(Point2D.Float(x.toFloat(), y.toFloat()))
                x += if(line.x1 > line.x2) -1 else 1
                y += if(line.y1 > line.y2) -1 else 1
                points.add(Point2D.Float(x.toFloat(), y.toFloat()))
            } while (x != line.x2 && y != line.y2)
        }

        return points
    }

    fun readLines(input: List<String>): List<Line2D> {
        val lines: MutableList<Line2D> = arrayListOf()

        input.forEach {
            val parts = it.split(" -> ")
            val left = parts[0].split(',')
            val right = parts[1].split(',')
            val x1 = left[0].toFloat()
            val y1 = left[1].toFloat()
            val x2 = right[0].toFloat()
            val y2 = right[1].toFloat()
            lines.add(Line2D.Float(x1, y1, x2, y2))
        }

        return lines
    }

    fun countIntersections(lines: List<Line2D>): Int {
        val intersections: MutableSet<Point2D> = mutableSetOf()

        lines.forEachIndexed { indexA, lineA ->
            for (indexB in indexA + 1 until lines.size) {
                val lineB = lines[indexB]
                val intersects = lineA.intersectsLine(lineB)

                if (intersects) {
                    val lineAPoints = getPointsForLine(lineA)
                    val lineBPoints = getPointsForLine(lineB)

                    lineAPoints.intersect(lineBPoints.toSet()).forEach {
                        intersections.add(it)
                    }
                }
            }
        }

        return intersections.size
    }

    fun part1(input: List<String>): Int {
        val lines = readLines(input).filter {
            // Only horizontal and vertical lines
            it.x1 == it.x2 || it.y1 == it.y2
        }
        return countIntersections(lines)
    }

    fun part2(input: List<String>): Int {
        val lines = readLines(input)
        return countIntersections(lines)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
