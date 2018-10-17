@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson4.task1

import lesson1.task1.discriminant
import lesson1.task1.sqr
import java.lang.Math.pow
import kotlin.math.max
import kotlin.math.sqrt

/**
 * Пример
 *
 * Найти все корни уравнения x^2 = y
 */
fun sqRoots(y: Double) =
        when {
            y < 0 -> listOf()
            y == 0.0 -> listOf(0.0)
            else -> {
                val root = sqrt(y)
                // Результат!
                listOf(-root, root)
            }
        }

/**
 * Пример
 *
 * Найти все корни биквадратного уравнения ax^4 + bx^2 + c = 0.
 * Вернуть список корней (пустой, если корней нет)
 */
fun biRoots(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        return if (b == 0.0) listOf()
        else sqRoots(-c / b)
    }
    val d = discriminant(a, b, c)
    if (d < 0.0) return listOf()
    if (d == 0.0) return sqRoots(-b / (2 * a))
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
    return sqRoots(y1) + sqRoots(y2)
}

/**
 * Пример
 *
 * Выделить в список отрицательные элементы из заданного списка
 */
fun negativeList(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (element in list) {
        if (element < 0) {
            result.add(element)
        }
    }
    return result
}

/**
 * Пример
 *
 * Изменить знак для всех положительных элементов списка
 */
fun invertPositives(list: MutableList<Int>) {
    for (i in 0 until list.size) {
        val element = list[i]
        if (element > 0) {
            list[i] = -element
        }
    }
}

/**
 * Пример
 *
 * Из имеющегося списка целых чисел, сформировать список их квадратов
 */
fun squares(list: List<Int>) = list.map { it * it }

/**
 * Пример
 *
 * Из имеющихся целых чисел, заданного через vararg-параметр, сформировать массив их квадратов
 */
fun squares(vararg array: Int) = squares(array.toList()).toTypedArray()

/**
 * Пример
 *
 * По заданной строке str определить, является ли она палиндромом.
 * В палиндроме первый символ должен быть равен последнему, второй предпоследнему и т.д.
 * Одни и те же буквы в разном регистре следует считать равными с точки зрения данной задачи.
 * Пробелы не следует принимать во внимание при сравнении символов, например, строка
 * "А роза упала на лапу Азора" является палиндромом.
 */
fun isPalindrome(str: String): Boolean {
    val lowerCase = str.toLowerCase().filter { it != ' ' }
    for (i in 0..lowerCase.length / 2) {
        if (lowerCase[i] != lowerCase[lowerCase.length - i - 1]) return false
    }
    return true
}

/**
 * Пример
 *
 * По имеющемуся списку целых чисел, например [3, 6, 5, 4, 9], построить строку с примером их суммирования:
 * 3 + 6 + 5 + 4 + 9 = 27 в данном случае.
 */
fun buildSumExample(list: List<Int>) = list.joinToString(separator = " + ", postfix = " = ${list.sum()}")

/**
 * Простая
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun abs(v: List<Double>): Double {
    var s = 0.0
    for (element in v)
        s += sqr(element)
    return sqrt(s)
}

/**
 * Простая
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>): Double {
    var d = 0.0
    val s = list.size
    return if (list.isEmpty()) 0.0 else {
        for (element in list) d += element
        d / s
    }
}

/**
 * Средняя
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double> {
    val s = list.size
    val f = mean(list)
    for (i in 0 until s) {
        val element = list[i] - f
        list[i] = element
    }
    return list
}

/**
 * Средняя
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.0.
 */
fun times(a: List<Double>, b: List<Double>): Double {
    var x = 0.0
    val f = max(a.size, b.size)
    for (i in 0 until f) {
        val d = a[i] * b[i]
        x += d
    }
    return x
}

/**
 * Средняя
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0.0 при любом x.
 */
fun polynom(p: List<Double>, x: Double): Double {
    var s = 0.0
    var t = 0.0
    for (i in 0 until p.size) {
        val v = p[i] * pow(x, s)
        t += v
        s++
    }
    return t
}

/**
 * Средняя
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Double>): MutableList<Double> {
    for (i in 1 until list.size) {
        list[i] += list[i - 1]
    }
    return list
}

/**
 * Средняя
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun factorize(n: Int): List<Int> {
    val list = mutableListOf<Int>()
    var d = n
    var x = 2
    while (d != 1) {
        if (d % x == 0) {
            list.add(x)
            d /= x
        } else x += 1
    }
    return list.sorted()
}

/**
 * Сложная
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 * Множители в результирующей строке должны располагаться по возрастанию.
 */
fun factorizeToString(n: Int): String {
    val t =
            factorize(n)
    val g = StringBuilder()
    g.append(t[0])
    for (x in 1 until t.size) {
        g.append('*')
        g.append(t[x])
    }
    return g.toString()
}

/**
 * Средняя
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convert(n: Int, base: Int): List<Int> {
    val list = mutableListOf<Int>()
    if ((n >= 0) && (base > 1)) {
        var f = n
        do {
            val d = f % base
            f /= base
            list.add(0, d)
        } while (f != 0)
    } else println("ошибка")
    return list
}

/**
 * Сложная
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 */
fun convertToString(n: Int, base: Int): String {
    val p1 = StringBuilder()
    if ((n >= 0) && (base > 1) && (base <= 36)) {
        var f = n
        do {
            val r = f % base
            val d = if (r >= 10) (r + 87).toChar()
            else (r + 48).toChar()
            f /= base
            p1.append(d)
        } while (f != 0)
    } else println("ошибка")
    return p1.reversed().toString()
}

/**
 * Средняя
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int {
    var n = 0
    val s = digits.size
    for (i in 0..(s - 1))
        n += digits[i] * (pow(base.toDouble(),
                (s - 1 - i).toDouble())).toInt()
    return n
}

/**
 * Сложная
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 */
fun decimalFromString(str: String, base: Int): Int {
    var r = 0
    if ((base > 1) && (base <= 36)) {
        for (i in 0 until str.length) {
            val t = if (str[i].toInt() <= 87)
                str[i].toInt() - 48
            else str[i].toInt() - 87
            r += (t * pow(base.toDouble(),
                    (str.length - 1 - i).toDouble()))
                    .toInt()
        }
    } else println("ошибка")
    return r
}

/**
 * Сложная
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */
fun roman(n: Int): String {
    var z = n
    val s = StringBuilder()
    if (n > 0) while (z != 0) {
        when {
            z / 1000 != 0 -> {
                s.append("M")
                z -= 1000
            }
            z - 900 >= 0 -> {
                s.append("CM")
                z -= 900
            }
            z / 500 != 0 -> {
                s.append("D")
                z -= 500
            }
            z - 400 >= 0 -> {
                s.append("CD")
                z -= 400
            }
            z / 100 != 0 -> {
                s.append("C")
                z -= 100
            }
            z - 90 >= 0 -> {
                s.append("XC")
                z -= 90
            }
            z / 50 != 0 -> {
                s.append("L")
                z -= 50
            }
            z - 40 >= 0 -> {
                s.append("XL")
                z -= 40
            }
            z / 10 != 0 -> {
                s.append("X")
                z -= 10
            }
            z - 9 >= 0 -> {
                s.append("IX")
                z -= 9
            }
            z / 5 != 0 -> {
                s.append("V")
                z -= 5
            }
            z - 4 >= 0 -> {
                s.append("IV")
                z -= 4
            }
            z - 1 >= 0 -> {
                s.append("I")
                z -= 1
            }
        }
    } else println("ошибка")
    return s.toString()
}

/**
 * Очень сложная
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */
fun russian(n: Int): String {
    val s = StringBuilder()
    if ((n >= 1) && (n <= 999999)) {
        val f = (n / 1000) % 10
        val r = StringBuilder()
        val p = (n / 10000) % 10
        val d = when {
            (f == 1) && (p != 1) -> " тысяча"
            (f > 1) && (f < 5) && (p != 1) ->
                " тысячи"
            else -> " тысяч"
        }
        val list = listOf("", "один",
                "два", "три", "четыр", "пят",
                "шест", "сем", "восем", "девят")
        val k1 = "надцать"
        val k2 = "ьсот"
        val k3 = "ьдесят"
        val m = if (n / 1000 > 0) 2 else 1
        for (l in m downTo 1) {
            val c1 = n / pow(10.0, -1.0 +
                    l * 3).toInt() % 10
            val c2 = n / pow(10.0, -2.0 +
                    l * 3).toInt() % 10
            val c3 = n / pow(10.0, -3.0 +
                    l * 3).toInt() % 10
            val c4 = (n / pow(10.0, -3.0 +
                    l * 3).toInt()) % 100
            if ((c1 != 0) && (l == 1)
                    && (s.isNotEmpty())) s.append(' ')
            for (x in 5..9) {
                when (c1) {
                    1 -> r.append("сто")
                    2 -> r.append("двести")
                    3 -> r.append("триста")
                    4 -> r.append("четыреста")
                    x -> r.append(list[x] + k2)
                }
                if (r.isNotEmpty()) break
            }
            s.append(r)
            if ((c2 != 0) && (s.isNotEmpty()))
                s.append(' ')
            r.delete(0, 16)
            for (x in 1..9) {
                when (c2) {
                    1 -> when (c4) {
                        10 -> r.append("десять")
                        12 -> r.append("двенадцать")
                        10 + x -> r.append(list[x] + k1)
                    }
                    2 -> r.append("двадцать")
                    3 -> r.append("тридцать")
                    4 -> r.append("сорок")
                    9 -> r.append("девяносто")
                    x -> r.append(list[x] + k3)
                }
                if (r.isNotEmpty()) break
            }
            s.append(r)
            if ((c3 != 0) && (c2 != 1)
                    && (s.isNotEmpty())) s.append(' ')
            r.delete(0, 16)
            if (c2 != 1) {
                for (x in 5..9) {
                    when (c3) {
                        1 -> r.append(if (l == 2)
                            "одна" else "один")
                        2 -> r.append(if (l == 2)
                            "две" else "два")
                        3 -> r.append("три")
                        4 -> r.append("четыре")
                        x -> r.append(list[x] + 'ь')
                    }
                    if (r.isNotEmpty()) break
                }
            } else r.delete(0, 16)
            s.append(r)
            r.delete(0, 16)
            if (l == 2) s.append(d)
        }
    } else println("ошибка")
    return s.toString()
}
