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
        if (itemCost != null) {
            totalCost += itemCost
        }
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
        if (!phone.startsWith(countryCode)) {
            namesToRemove.add(name)
        }
    }

    for (name in namesToRemove) {
        phoneBook.remove(name)
    }
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
        if (word !in fillerWordSet) {
            res += word
        }
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
                    mapB: Map<String, String>):
        Map<String, String> {
    val c =
            (mapA + mapB).toMutableMap()
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
fun buildGrades(grades: Map<String, Int>):
        Map<Int, List<String>> {
    val s =
            mutableMapOf<Int, List<String>>()
    var t = 0
    for ((a, b) in grades) {
        var h = 0
        val k =
                mutableListOf<String>()
        k += a
        for ((name, mark) in grades) {
            if (b == mark)
                h++
            if (h >= 2) k += name
        }
        t += k.size
        if (t <= grades.size) s += (b to k)
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
fun containsIn(a: Map<String, String>,
               b: Map<String, String>):
        Boolean = b + a == b

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
fun averageStockPrice(stockPrices: List<Pair<String, Double>>):
        Map<String, Double> {
    val d =
            mutableMapOf<String, Double>()
    for ((f1, f2) in stockPrices)
        d += (f1 to f2)
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
fun findCheapestStuff(stuff: Map<String, Pair<String, Double>>,
                      kind: String): String? {
    val k =
            mutableListOf<Double>()
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
fun propagateHandshakes(friends: Map<String, Set<String>>):
        Map<String, Set<String>> {
    val result =
            mutableMapOf<String, Set<String>>()
    val f =
            mutableSetOf<String>()
    for ((x1, y1) in friends) {
        f += x1
        f += y1
    }
    val p = f.toList()
    for (x in p) {
        val r =
                mutableSetOf<String>()
        val s =
                friends[x] ?: setOf()
        r += s
        val d = s.toList()
        for (a in d) {
            val k =
                    friends[a] ?: setOf()
            r += k
        }
        if (x in r) r -= x
        result += (x to r)
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
fun subtractOf(a: MutableMap<String, String>,
               b: Map<String, String>) {
    val f = a.toMap()
    for (n in f.keys)
        if (f[n] == b[n]) a.remove(n)
}

/**
 * Простая
 *
 * Для двух списков людей найти людей, встречающихся в обоих списках
 */
fun whoAreInBoth(a: List<String>, b: List<String>): List<String> {
    val n = mutableListOf<String>()
    for (x1 in a) {
        for (x2 in b) {
            if (x1 == x2) n += x1
        }
    }
    return n
}

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
    word.toLowerCase()
    val d = word.toList()
    var result = 0
    val f = mutableSetOf<Char>()
    for (x1 in d)
        f += x1
    for (x2 in chars)
        if (x2 in f) result += 1
    return (result == f.size)
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
fun extractRepeats(list: List<String>):
        Map<String, Int> {
    val g =
            mutableMapOf<String, Int>()
    for (f1 in list) {
        var count = 0
        for (h in list) {
            val s1 = h
            if (f1 == s1) count += 1
        }
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
    var g = 0
    var h = 0
    val s = mutableSetOf<Char>()
    for (x1 in words) {
        for (a1 in x1)
            s += a1
        for (x2 in words) {
            for (a2 in x2) {
                if (a2 in s) g += 1
                if (g == s.size) h += 1
            }
            g = 0
        }
        s.clear()
    }
    return (h > words.size)
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
fun findSumOfTwo(list: List<Int>, number: Int):
        Pair<Int, Int> {
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
fun bagPacking(treasures: Map<String, Pair<Int, Int>>,
               capacity: Int): Set<String> {
    val f =
            mutableSetOf<String>()
    val k =
            mutableListOf<Pair<Int, Int>>()
    for ((weight, cost) in treasures.values) {
        if (weight <= capacity) k += (weight to cost)
    }
    var costmax = 0
    for (c2 in 0 until k.size) {
        val g1 = k[c2].first
        val g2 = k[c2].second
        var weight1 = g1
        if (costmax == 0) costmax = g1
        for (c3 in (c2 + 1) until k.size) {
            weight1 += k[c3].first
            if ((weight1 <= capacity)
                    && (g2 + k[c3].second >=
                            costmax))
                costmax += k[c3].second
        }
    }
    var result = (0 to 0)
    for (c4 in 0 until k.size) {
        val g2 = k[c4].second
        var i = g2
        if (k[c4].first == costmax)
            result =
                    (k[c4].first to g2)
        for (c5 in (c4 + 1) until k.size) {
            result = (0 to 0)
            val p = k[c5].second
            i += p
            if (i == costmax) {
                result = (k[c5].first to p)
                break
            }
        }
        for ((x, y) in treasures)
            if (y == result) f += x
    }
    return f
}
