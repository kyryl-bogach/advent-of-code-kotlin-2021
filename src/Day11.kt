import java.awt.Point

fun main() {
    fun increaseEnergy(energies: Array<IntArray>, i: Int, j: Int): Int {
        if (null != energies.getOrNull(i)?.getOrNull(j)) {
            energies[i][j]++

            return energies[i][j]
        }

        return Int.MIN_VALUE
    }

    fun part1(input: List<String>): Int {
        val energies = Array(input.size) { IntArray(input[0].length) { 0 } }
        input.forEachIndexed { index, it ->
            energies[index] = it.chunked(1).map { it.toInt() }.toIntArray()
        }

        var flashes = 0

        for (step in 0 until 100) {
            val queue: MutableList<Point> = mutableListOf()

            // All energies increase by 1
            for (i in energies.indices) {
                for (j in energies.indices) {
                    energies[i][j]++

                    if (energies[i][j] > 9) {
                        queue.add(Point(i, j))
                    }
                }
            }

            val visited: MutableSet<Point> = mutableSetOf()

            while (queue.isNotEmpty()) {
                val node = queue.removeAt(0)
                if (visited.contains(node)) continue

                // up
                if (increaseEnergy(energies, node.x - 1, node.y) > 9) {
                    queue.add(Point(node.x - 1, node.y))
                }
                // down
                if (increaseEnergy(energies, node.x + 1, node.y) > 9) {
                    queue.add(Point(node.x + 1, node.y))
                }
                // left
                if (increaseEnergy(energies, node.x, node.y - 1) > 9) {
                    queue.add(Point(node.x, node.y - 1))
                }
                // right
                if (increaseEnergy(energies, node.x, node.y + 1) > 9) {
                    queue.add(Point(node.x, node.y + 1))
                }
                // topLeftDialogal
                if (increaseEnergy(energies, node.x - 1, node.y - 1) > 9) {
                    queue.add(Point(node.x - 1, node.y - 1))
                }
                // topRightDialogal
                if (increaseEnergy(energies, node.x + 1, node.y - 1) > 9) {
                    queue.add(Point(node.x + 1, node.y - 1))
                }
                // bottomLeftDialogal
                if (increaseEnergy(energies, node.x - 1, node.y + 1) > 9) {
                    queue.add(Point(node.x - 1, node.y + 1))
                }
                // bottomRightDialogal
                if (increaseEnergy(energies, node.x + 1, node.y + 1) > 9) {
                    queue.add(Point(node.x + 1, node.y + 1))
                }

                visited.add(node)
            }

            // Reset flashed energies to 0
            for (i in energies.indices) {
                for (j in energies.indices) {
                    if (energies[i][j] > 9) {
                        energies[i][j] = 0
                    }
                }
            }

            flashes += visited.size
        }

        return flashes
    }

    fun part2(input: List<String>): Int {
        val energies = Array(input.size) { IntArray(input[0].length) { 0 } }
        input.forEachIndexed { index, it ->
            energies[index] = it.chunked(1).map { it.toInt() }.toIntArray()
        }

        var step = 0
        while (true) {
            step++

            val queue: MutableList<Point> = mutableListOf()

            // All energies increase by 1
            for (i in energies.indices) {
                for (j in energies.indices) {
                    energies[i][j]++

                    if (energies[i][j] > 9) {
                        queue.add(Point(i, j))
                    }
                }
            }

            val visited: MutableSet<Point> = mutableSetOf()

            while (queue.isNotEmpty()) {
                val node = queue.removeAt(0)
                if (visited.contains(node)) continue

                // up
                if (increaseEnergy(energies, node.x - 1, node.y) > 9) {
                    queue.add(Point(node.x - 1, node.y))
                }
                // down
                if (increaseEnergy(energies, node.x + 1, node.y) > 9) {
                    queue.add(Point(node.x + 1, node.y))
                }
                // left
                if (increaseEnergy(energies, node.x, node.y - 1) > 9) {
                    queue.add(Point(node.x, node.y - 1))
                }
                // right
                if (increaseEnergy(energies, node.x, node.y + 1) > 9) {
                    queue.add(Point(node.x, node.y + 1))
                }
                // topLeftDialogal
                if (increaseEnergy(energies, node.x - 1, node.y - 1) > 9) {
                    queue.add(Point(node.x - 1, node.y - 1))
                }
                // topRightDialogal
                if (increaseEnergy(energies, node.x + 1, node.y - 1) > 9) {
                    queue.add(Point(node.x + 1, node.y - 1))
                }
                // bottomLeftDialogal
                if (increaseEnergy(energies, node.x - 1, node.y + 1) > 9) {
                    queue.add(Point(node.x - 1, node.y + 1))
                }
                // bottomRightDialogal
                if (increaseEnergy(energies, node.x + 1, node.y + 1) > 9) {
                    queue.add(Point(node.x + 1, node.y + 1))
                }

                visited.add(node)
            }

            // Reset flashed energies to 0
            var allFlash = true
            for (i in energies.indices) {
                for (j in energies.indices) {
                    if (energies[i][j] > 9) {
                        energies[i][j] = 0
                    } else {
                        allFlash = false
                    }
                }
            }

            if (allFlash) {
                return step
            }
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput) == 1656)
    check(part2(testInput) == 195)

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}
