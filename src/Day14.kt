fun main() {
    fun readTemplate(input: List<String>): String {
        return input.first()
    }

    fun readRules(input: List<String>): Set<Pair<String, String>> {
        return input.takeLastWhile { it.isNotEmpty() }
            .map { it.split(" -> ") }
            .map {
                val key = it.first()
                val value = "${key[0]}${it.last()}${key[1]}"

                Pair(key, value)
            }.toSet()
    }

    fun part1(input: List<String>): Int {
        var template = readTemplate(input)
        val rules = readRules(input)

        for (i in 0 until 10) {
            val validRules: HashMap<String, String> = hashMapOf()

            rules.forEach { rule ->
                if (template.contains(rule.first)) {
                    validRules[rule.first] = rule.second
                }
            }

            template.windowed(2).forEach {
                if (validRules.containsKey(it)) {
                    template = template.replaceFirst(it, validRules[it]!!)
                }
            }
        }

        val charCount = template.groupingBy { it }.eachCount()
        return charCount.maxOf { it.value } - charCount.minOf { it.value }
    }

    fun part2(input: List<String>): Long {
        val rules = readRules(input)
        val template = readTemplate(input)

        val pairs: HashMap<String, Long> = hashMapOf()
        template.windowed(2).forEach {
            pairs[it] = pairs.getOrPut(it) { 0 } + 1L
        }

        val charCount: HashMap<Char, Long> = hashMapOf()
        template.forEach {
            charCount[it] = charCount.getOrPut(it) { 0 } + 1L
        }

        for (i in 0 until 40) {
            val toIncrease: HashMap<String, Long> = hashMapOf()
            val toRemove: HashMap<String, Long> = hashMapOf()
            val doubleIncreaseChars: HashMap<Char, Long> = hashMapOf()

            rules.forEach { (from, to) ->
                if (pairs.containsKey(from)) {
                    val frequency = pairs[from]!!

                    val left = "${to[0]}${to[1]}"
                    val right = "${to[1]}${to[2]}"
                    toIncrease[left] = toIncrease.getOrPut(left) { 0 } + frequency
                    toIncrease[right] = toIncrease.getOrPut(right) { 0 } + frequency
                    toRemove[from] = toRemove.getOrPut(from) { 0 } + frequency
                    doubleIncreaseChars[to[1]] = doubleIncreaseChars.getOrPut(to[1]) { 0 } + frequency
                }
            }

            toIncrease.forEach {
                charCount[it.key[0]] = charCount.getOrPut(it.key[0]) { 0 } + it.value
                charCount[it.key[1]] = charCount.getOrPut(it.key[1]) { 0 } + it.value

                pairs[it.key] = pairs.getOrPut(it.key) { 0 } + it.value
            }

            toRemove.forEach {
                charCount[it.key[0]] = charCount[it.key[0]]!! - it.value
                charCount[it.key[1]] = charCount[it.key[1]]!! - it.value

                pairs[it.key] = pairs.getOrPut(it.key) { 0 } - it.value
                if (pairs[it.key]!! <= 0L) {
                    pairs.remove(it.key)
                }
            }

            doubleIncreaseChars.forEach {
                charCount[it.key] = charCount[it.key]!! - it.value
            }
        }

        return charCount.maxOf { it.value }  - charCount.minOf { it.value }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day14_test")
    check(part1(testInput) == 1588)
    check(part2(testInput) == 2188189693529L)

    val input = readInput("Day14")
    println(part1(input))
    println(part2(input))
}
