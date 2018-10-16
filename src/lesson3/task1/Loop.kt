@file:Suppress("UNUSED_PARAMETER")

package lesson3.task1

import lesson1.task1.sqr
import java.lang.Math.pow
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.sqrt

/**l
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result = result * i // Please do not fix in master
    }
    return result
}

/**
 * Пример
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    if (n == 2) return true
    if (n % 2 == 0) return false
    for (m in 3..sqrt(n.toDouble()).toInt() step 2) {
        if (n % m == 0) return false
    }
    return true
}

/**
 * Пример
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n / 2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
        when {
            n == m -> 1
            n < 10 -> 0
            else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
        }

/**
 * Тривиальная
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun digitNumber(n: Int): Int {
    var s = 0
    var d = n
    do {
        d /= 10
        s++
    } while (d != 0)
    return s
}

/**
 * Простая
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
fun fib(n: Int): Int {
    var c = 1
    var a = 1
    return if (n <= 2) c else {
        for (x in 3..n) {
            if (x % 2 == 1) c += a else a += c
        }
        if (n % 2 == 1) c else a
    }
}

/**
 * Простая
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
fun lcm(m: Int, n: Int): Int {
    var x = 2
    var b = 1
    var m1 = m
    var n1 = n
    while ((n1 != 1) || (m1 != 1)) {
        val f1 = n1 % x
        val f2 = m1 % x
        if ((f1 != 0) && (f2 != 0)) x++ else {
            b *= x
            when {
                (f1 == 0) && (f2 == 0) -> {
                    n1 /= x
                    m1 /= x
                }
                f1 == 0 -> n1 /= x
                else -> m1 /= x
            }
        }
    }
    return b
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int): Int {
    var a = 0
    for (x in (n / 2) downTo 2)
        if (n % x == 0) a = x
    if (a == 0) a = n
    return a
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int {
    var a = 1
    for (x in (n / 2)
            downTo minDivisor(n))
        if (n % x == 0) {
            a = x
            break
        }
    return a
}

/**
 * Простая
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun isCoPrime(m: Int, n: Int): Boolean {
    val c = if (m > n) n else m
    var a = 0
    for (x in 1..c)
        if ((n % x == 0) && (m % x == 0)) a = x
    return a == 1
}

/**
 * Простая
 *
 * Для заданных чисел m и n, m <= n, определить, имеется ли хотя бы один точный квадрат между m и n,
 * то есть, существует ли такое целое k, что m <= k*k <= n.
 * Например, для интервала 21..28 21 <= 5*5 <= 28, а для интервала 51..61 квадрата не существует.
 */
fun squareBetweenExists(m: Int, n: Int): Boolean {
    var c = 0
    val a = (sqrt(m.toDouble())).toInt()
    val b = (sqrt(n.toDouble())).toInt()
    for (x in a..b)
        if ((x * x >= m) && (x * x <= n)) c++
    return c > 0
}

/**
 * Средняя
 *
 * Гипотеза Коллатца. Рекуррентная последовательность чисел задана следующим образом:
 *
 *   ЕСЛИ (X четное)
 *     Xслед = X /2
 *   ИНАЧЕ
 *     Xслед = 3 * X + 1
 *
 * например
 *   15 46 23 70 35 106 53 160 80 40 20 10 5 16 8 4 2 1 4 2 1 4 2 1 ...
 * Данная последовательность рано или поздно встречает X == 1.
 * Написать функцию, которая находит, сколько шагов требуется для
 * этого для какого-либо начального X > 0.
 */
fun collatzSteps(x: Int): Int {
    var count = 0
    var a = x
    while (a != 1) {
        if (a % 2 == 0) {
            count++
            a /= 2
        } else {
            count++
            a = 3 * a + 1
        }
    }
    return count
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun sin(x: Double, eps: Double): Double {
    var s = 0
    var c = 1
    val p = ((x / PI * 2.0) % 4.0) * PI / 2
    var r = p
    var f = 1.0
    do {
        s++
        c += 2
        f *= c * (c - 1)
        val z = pow(-1.0, s.toDouble()) *
                pow(p, c.toDouble()) / f
        r += z
    } while (abs(z) >= eps)
    return r
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun cos(x: Double, eps: Double): Double {
    var s = 0
    var c = 0
    val p = ((x / PI * 2.0) % 4.0) * PI / 2
    var r = 1.0
    var f = 1.0
    do {
        s++
        c += 2
        f *= c * (c - 1)
        val z = pow(-1.0, s.toDouble()) *
                pow(p, c.toDouble()) / f
        r += z
    } while (abs(z) >= eps)
    return r
}

/**
 * Средняя
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun revert(n: Int): Int {
    var t = 0
    var d = n
    val s = digitNumber(n)
    for (z in 1..s) {
        t = t * 10 + d % 10
        d /= 10
    }
    return t
}

/**
 * Средняя
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun isPalindrome(n: Int): Boolean =
        n == revert(n)

/**
 * Средняя
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun hasDifferentDigits(n: Int): Boolean {
    var f = 0
    val s = digitNumber(n)
    val b = n % 10
    var m = n
    for (x in 1..s) {
        val a = m % 10
        m /= 10
        if (a != b) {
            f++
            break
        }
    }
    return (f == 1)
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun squareSequenceDigit(n: Int): Int {
    var s2 = 0
    var f = 0
    for (x in 1..n) {
        val c = sqr(x)
        f = c
        val s1 = digitNumber(c)
        s2 += s1
        if (s2 >= n) break
    }
    for (x in 1..(s2 - n)) f /= 10
    return f % 10
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun fibSequenceDigit(n: Int): Int {
    var c = 1
    var a = 1
    var k = 0
    var d = 2
    var q: Int
    var t: Int
    return if (n <= 2) c else {
        for (x in 3..n) {
            if (x % 2 == 1) {
                c += a
                t = c
                k = c
                q = digitNumber(t)
            } else {
                a += c
                t = a
                k = a
                q = digitNumber(t)
            }
            d += q
            if (d >= n) break
        }
        for (x in 1..(d - n)) k /= 10
        k % 10
    }
}
