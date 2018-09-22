@file:Suppress("UNUSED_PARAMETER")
package lesson2.task2

import lesson1.task1.sqr

/**
 * Пример
 *
 * Лежит ли точка (x, y) внутри окружности с центром в (x0, y0) и радиусом r?
 */
fun pointInsideCircle(x: Double, y: Double, x0: Double, y0: Double, r: Double) =
        sqr(x - x0) + sqr(y - y0) <= sqr(r)

/**
 * Простая
 *
 * Четырехзначное число назовем счастливым, если сумма первых двух ее цифр равна сумме двух последних.
 * Определить, счастливое ли заданное число, вернуть true, если это так.
 */
fun isNumberHappy(number: Int): Boolean {
    val a1 = number % 10
    val a2 = number % 100 / 10
    val t = a2 * 10 + a1
    val a3 = (number % 1000 - t) / 100
    val a4 = (number - a3 * 100 - t) / 1000
    val s1 = a1 + a2
    val s2 = a3 + a4
    return s1 == s2
}

/**
 * Простая
 *
 * На шахматной доске стоят два ферзя (ферзь бьет по вертикали, горизонтали и диагоналям).
 * Определить, угрожают ли они друг другу. Вернуть true, если угрожают.
 * Считать, что ферзи не могут загораживать друг друга.
 */
fun queenThreatens(x1: Int, y1: Int, x2: Int, y2: Int): Boolean {
    val a = y1 - y2
    val b = x1 - x2
    return ((x1 == x2) || (y1 == y2)) || ((a == -b) || (-a == b) || (a == b) || (-a == -b))
}


/**
 * Простая
 *
 * Дан номер месяца (от 1 до 12 включительно) и год (положительный).
 * Вернуть число дней в этом месяце этого года по григорианскому календарю.
 */
fun daysInMonth(month: Int, year: Int): Int {
    val a = year % 4
    return when {
        (month == 2) && ((a != 0) || (year == 1900)) -> 28
        month == 2 -> 29
        (month == 4) || (month == 6) || (month == 9) || (month == 11) -> 30
        else -> 31
    }
}

/**
 * Средняя
 *
 * Проверить, лежит ли окружность с центром в (x1, y1) и радиусом r1 целиком внутри
 * окружности с центром в (x2, y2) и радиусом r2.
 * Вернуть true, если утверждение верно
 */
fun circleInside(x1: Double, y1: Double, r1: Double,
                 x2: Double, y2: Double, r2: Double): Boolean {
    val a = sqr(x1 - x2)
    val b = sqr(y1 - y2)
    return if ((a + b <= sqr(r2)) && (r1 <= r2)) {
        a + b <= sqr(r1 - r2)
    } else false
}

/**
 * Средняя
 *
 * Определить, пройдет ли кирпич со сторонами а, b, c сквозь прямоугольное отверстие в стене со сторонами r и s.
 * Стороны отверстия должны быть параллельны граням кирпича.
 * Считать, что совпадения длин сторон достаточно для прохождения кирпича, т.е., например,
 * кирпич 4 х 4 х 4 пройдёт через отверстие 4 х 4.
 * Вернуть true, если кирпич пройдёт
 */
fun brickPasses(a: Int, b: Int, c: Int, r: Int, s: Int): Boolean {
    val t1 = if ((a <= r) && (b <= s)) 1 else 0
    val t2 = if ((a <= s) && (b <= r)) 1 else 0
    val t3 = if ((a <= r) && (c <= s)) 1 else 0
    val t4 = if ((a <= s) && (c <= r)) 1 else 0
    val t5 = if ((b <= r) && (c <= s)) 1 else 0
    val t6 = if ((b <= s) && (c <= r)) 1 else 0
    return (t1 == 1) || (t2 == 1) || (t3 == 1) || (t4 == 1) || (t5 == 1) || (t6 == 1)
}
