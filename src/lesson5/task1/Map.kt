@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson5.task1

/**
 * Пример
 *
 * Для заданного списка покупок `shoppingList` посчитать его общую стоимость
 * на основе цен из `costs`. В случае неизвестной цены считать, что товар
 * игнорируется.
 */
fun shoppingListCost(
        shoppingList: List<String>,
        costs: Map<String, Double>): Double {
    var totalCost = 0.0
    for (item in shoppingList) {
        val itemCost = costs[item]
        if (itemCost != null)
            totalCost += itemCost
    }
    return totalCost
}

/**
 * Пример
 *
 * Для набора "имя"-"номер телефона" `phoneBook` оставить только такие пары,
 * для которых телефон начинается с заданного кода страны `countryCode`
 */
fun filterByCountryCode(
        phoneBook: MutableMap<String, String>,
        countryCode: String) {
    val namesToRemove = mutableListOf<String>()
    for ((name, phone) in phoneBook) {
        if (!phone.startsWith(countryCode))
            namesToRemove.add(name)
    }
    for (name in namesToRemove)
        phoneBook.remove(name)
}

/**
 * Пример
 *
 * Для заданного текста `text` убрать заданные слова-паразиты `fillerWords`
 * и вернуть отфильтрованный текст
 */
fun removeFillerWords(
        text: List<String>,
        vararg fillerWords: String): List<String> {
    val fillerWordSet = setOf(*fillerWords)
    val res = mutableListOf<String>()
    for (word in text) {
        if (word !in fillerWordSet)
            res += word
    }
    return res
}

/**
 * Пример
 *
 * Для заданного текста `text` построить множество встречающихся в нем слов
 */
fun buildWordSet(text: List<String>): MutableSet<String> {
    val res = mutableSetOf<String>()
    for (word in text) res.add(word)
    return res
}

/**
 * Средняя
 *
 * Объединить два ассоциативных массива `mapA` и `mapB` с парами
 * "имя"-"номер телефона" в итоговый ассоциативный массив, склеивая
 * значения для повторяющихся ключей через запятую.
 * В случае повторяющихся *ключей* значение из mapA должно быть
 * перед значением из mapB.
 *
 * Повторяющиеся *значения* следует добавлять только один раз.
 *
 * Например:
 *   mergePhoneBooks(
 *     mapOf("Emergency" to "112", "Police" to "02"),
 *     mapOf("Emergency" to "911", "Police" to "02")
 *   ) -> mapOf("Emergency" to "112, 911", "Police" to "02")
 */
fun mergePhoneBooks(mapA: Map<String, String>,
                    mapB: Map<String, String>): Map<String, String> {
    val c = (mapA + mapB).toMutableMap()
    for (name in mapA.keys) {
        if ((mapA[name] != mapB[name]) &&
                (mapB[name] != null)) {
            val b = mapA[name] +
                    ", " + mapB[name]
            c[name] = b
        }
    }
    return c
}

/**
 * Простая
 *
 * По заданному ассоциативному массиву "студент"-"оценка за экзамен" построить
 * обратный массив "оценка за экзамен"-"список студентов с этой оценкой".
 *
 * Например:
 *   buildGrades(mapOf("Марат" to 3, "Семён" to 5, "Михаил" to 5))
 *     -> mapOf(5 to listOf("Семён", "Михаил"), 3 to listOf("Марат"))
 */
fun buildGrades(grades: Map<String, Int>): Map<Int, List<String>> {
    val s = mutableMapOf<Int, List<String>>()
    val k = mutableSetOf<Int>()
    for (b in grades.values) k += b
    for (x in k) {
        val f = mutableListOf<String>()
        for ((name, mark) in grades)
            if (mark == x) f += name
        s += (x to f)
    }
    return s
}

/**
 * Простая
 *
 * Определить, входит ли ассоциативный массив a в ассоциативный массив b;
 * это выполняется, если все ключи из a содержатся в b с такими же значениями.
 *
 * Например:
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "z", "b" to "sweet")) -> true
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "zee", "b" to "sweet")) -> false
 */
fun containsIn(a: Map<String, String>, b: Map<String, String>) = b + a == b

/**
 * Средняя
 *
 * Для заданного списка пар "акция"-"стоимость" вернуть ассоциативный массив,
 * содержащий для каждой акции ее усредненную стоимость.
 *
 * Например:
 *   averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0))
 *     -> mapOf("MSFT" to 150.0, "NFLX" to 40.0)
 */
fun averageStockPrice(stockPrices: List<Pair<String, Double>>): Map<String, Double> {
    val d = stockPrices.toMap().toMutableMap()
    for (f1 in d.keys) {
        var sum = 0.0
        var count = 0
        for ((s1, s2) in stockPrices) {
            if (s1 == f1) {
                sum += s2
                count++
            }
        }
        if (count > 1) {
            d[f1] = sum / count
        }
    }
    return d
}

/**
 * Средняя
 *
 * Входными данными является ассоциативный массив
 * "название товара"-"пара (тип товара, цена товара)"
 * и тип интересующего нас товара.
 * Необходимо вернуть название товара заданного типа с минимальной стоимостью
 * или null в случае, если товаров такого типа нет.
 *
 * Например:
 *   findCheapestStuff(
 *     mapOf("Мария" to ("печенье" to 20.0), "Орео" to ("печенье" to 100.0)),
 *     "печенье"
 *   ) -> "Мария"
 */
fun findCheapestStuff(stuff: Map<String, Pair<String, Double>>, kind: String): String? {
    val k = mutableListOf<Double>()
    val d = StringBuilder()
    var e = 0
    var t = 0.0
    for ((z, x) in stuff.values)
        if (kind == z) {
            e++
            k += x
            t = x
        }
    var s = t
    if (e > 0) {
        for (h in k) {
            s = minOf(h, s)
        }
        val f = (kind to s)
        for ((a, b) in stuff)
            if (b == f) {
                d.append(a)
                break
            }
    }
    return (if (e > 0) d.toString() else null)
}

/**
 * Сложная
 *
 * Для заданного ассоциативного массива знакомых через одно рукопожатие `friends`
 * необходимо построить его максимальное расширение по рукопожатиям, то есть,
 * для каждого человека найти всех людей, с которыми он знаком через любое
 * количество рукопожатий.
 * Считать, что все имена людей являются уникальными, а также что рукопожатия
 * являются направленными, то есть, если Марат знает Свету, то это не означает,
 * что Света знает Марата.
 *
 * Например:
 *   propagateHandshakes(
 *     mapOf(
 *       "Marat" to setOf("Mikhail", "Sveta"),
 *       "Sveta" to setOf("Marat"),
 *       "Mikhail" to setOf("Sveta")
 *     )
 *   ) -> mapOf(
 *          "Marat" to setOf("Mikhail", "Sveta"),
 *          "Sveta" to setOf("Marat", "Mikhail"),
 *          "Mikhail" to setOf("Sveta", "Marat")
 *        )
 */
fun propagateHandshakes(friends: Map<String, Set<String>>): Map<String, Set<String>> {
    val result = mutableMapOf<String, Set<String>>()
    val sum = mutableSetOf<String>()
    for ((x, y) in friends) {
        sum += x
        sum += y
    }
    for (x1 in sum) {
        val g = friends[x1]?.toMutableSet() ?: mutableSetOf()
        for (c in 0 until (friends.size - 1))
            for (y1 in friends.keys) {
                if (y1 in g) g += friends[y1]?.toMutableSet() ?: mutableSetOf()
                g -= x1
            }
        result += (x1 to g)
    }
    return result
}


/**
 * Простая
 *
 * Удалить из изменяемого ассоциативного массива все записи,
 * которые встречаются в заданном ассоциативном массиве.
 * Записи считать одинаковыми, если и ключи, и значения совпадают.
 *
 * ВАЖНО: необходимо изменить переданный в качестве аргумента
 *        изменяемый ассоциативный массив
 *
 * Например:
 *   subtractOf(a = mutableMapOf("a" to "z"), mapOf("a" to "z"))
 *     -> a changes to mutableMapOf() aka becomes empty
 */
fun subtractOf(a: MutableMap<String, String>, b: Map<String, String>) {
    val t = a.toMap()
    for (n in t.keys)
        if (t[n] == b[n]) a.remove(n)
}

/**
 * Простая
 *
 * Для двух списков людей найти людей, встречающихся в обоих списках
 */
fun whoAreInBoth(a: List<String>, b: List<String>): List<String> =
        (a intersect b).toList()

/**
 * Средняя
 *
 * Для заданного набора символов определить, можно ли составить из него
 * указанное слово (регистр символов игнорируется)
 *
 * Например:
 *   canBuildFrom(listOf('a', 'b', 'o'), "baobab") -> true
 */
fun canBuildFrom(chars: List<Char>, word: String): Boolean {
    val f = mutableSetOf<Char>()
    for (x1 in word.toLowerCase())
        f += x1
    for (x2 in chars) {
        if (x2.toLowerCase() in f)
            f -= x2.toLowerCase()
        if (f.isEmpty()) break
    }
    return (f.isEmpty())
}

/**
 * Средняя
 *
 * Найти в заданном списке повторяющиеся элементы и вернуть
 * ассоциативный массив с информацией о числе повторений
 * для каждого повторяющегося элемента.
 * Если элемент встречается только один раз, включать его в результат
 * не следует.
 *
 * Например:
 *   extractRepeats(listOf("a", "b", "a")) -> mapOf("a" to 2)
 */
fun extractRepeats(list: List<String>): Map<String, Int> {
    val g = mutableMapOf<String, Int>()
    for (f1 in list) {
        var count = 0
        for (h in list)
            if (f1 == h) count += 1
        if (count >= 2) g += (f1 to count)
    }
    return g
}

/**
 * Средняя
 *
 * Для заданного списка слов определить, содержит ли он анаграммы
 * (два слова являются анаграммами, если одно можно составить из второго)
 *
 * Например:
 *   hasAnagrams(listOf("тор", "свет", "рот")) -> true
 */
fun hasAnagrams(words: List<String>): Boolean {
    val s = mutableListOf<Char>()
    for (x1 in 0 until words.size) {
        for (a1 in words[x1])
            s += a1
        val g = s.toList()
        for (x2 in (x1 + 1) until words.size) {
            if (words[x1].length == words[x2].length) {
                for (a2 in words[x2])
                    if (a2 in s)
                        s -= a2
                if (s.isEmpty()) break else {
                    s.clear()
                    s += g
                }
            }
        }
        if (s.isEmpty()) break
    }
    return ((s.isEmpty()) && (words.isNotEmpty()))
}

/**
 * Сложная
 *
 * Для заданного списка неотрицательных чисел и числа определить,
 * есть ли в списке пара чисел таких, что их сумма равна заданному числу.
 * Если да, верните их индексы в виде Pair<Int, Int>;
 * если нет, верните пару Pair(-1, -1).
 *
 * Индексы в результате должны следовать в порядке (меньший, больший).
 *
 * Постарайтесь сделать ваше решение как можно более эффективным,
 * используя то, что вы узнали в данном уроке.
 *
 * Например:
 *   findSumOfTwo(listOf(1, 2, 3), 4) -> Pair(0, 2)
 *   findSumOfTwo(listOf(1, 2, 3), 6) -> Pair(-1, -1)
 */
fun findSumOfTwo(list: List<Int>, number: Int): Pair<Int, Int> {
    var p = (-1 to -1)
    for (x1 in 0 until list.size) {
        for (x2 in (x1 + 1) until list.size) {
            p = if (list[x1] + list[x2] == number)
                (x1 to x2) else p
        }
    }
    return p
}

/**
 * Очень сложная
 *
 * Входными данными является ассоциативный массив
 * "название сокровища"-"пара (вес сокровища, цена сокровища)"
 * и вместимость вашего рюкзака.
 * Необходимо вернуть множество сокровищ с максимальной суммарной стоимостью,
 * которые вы можете унести в рюкзаке.
 *
 * Например:
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     850
 *   ) -> setOf("Кубок")
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     450
 *   ) -> emptySet()
 */
fun bagPacking(treasures: Map<String, Pair<Int, Int>>, capacity: Int): Set<String> {
    val result = mutableSetOf<String>()
    val k1 = mutableListOf<Pair<Int, Int>>()
    val k2 = mutableListOf<Pair<Int, Int>>()
    val st = mutableSetOf<Int>()
    for ((weight1, cost1) in treasures.values) {
        if (weight1 <= capacity)
            k1 += (weight1 to cost1)
    }
    k2 += k1
    var g = 0
    val size = k1.size
    var weight = 0
    var max = 1
    if (size != 0) for (c1 in 0 until size) {
        if (c1 == 0) {
            var min = k1[0].first
            var maxmin = k1[0].first
            for ((first) in k1)
                min = minOf(first, min)
            for ((first, second) in k1)
                if (first == min) maxmin = maxOf(second, maxmin)
            for (c2 in 0 until size)
                if ((k1[c2].first == min) &&
                        (k1[c2].second == maxmin)) {
                    g = c2
                    st += c2
                    k2 -= (min to maxmin)
                    break
                }
        }
        if (weight == 0) weight = k1[g].first
        if (max != 0) max = k1[g].second
        var f = 0
        for (x1 in k2)
            max = maxOf(x1.second, max)
        for ((first, second) in k2)
            if (second == max) f = first
        if (weight + f <= capacity) {
            for (c in 0 until k1.size)
                if ((k1[c].first == f) && (k1[c].second == max)) {
                    st += c
                    break
                }
            weight += f
        }
        k2 -= (f to max)
        max = 0
    }
    for (key in st) {
        for ((x, y) in treasures)
            if (y == k1[key]) result += x

    }
    return result
}
