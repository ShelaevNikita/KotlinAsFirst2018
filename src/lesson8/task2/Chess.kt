@file:Suppress("UNUSED_PARAMETER")

package lesson8.task2

import lesson8.task3.Graph
import kotlin.math.abs

/**
 * Клетка шахматной доски. Шахматная доска квадратная и имеет 8 х 8 клеток.
 * Поэтому, обе координаты клетки (горизонталь row, вертикаль column) могут находиться в пределах от 1 до 8.
 * Горизонтали нумеруются снизу вверх, вертикали слева направо.
 */
data class Square(val column: Int, val row: Int) {
    /**
     * Пример
     *
     * Возвращает true, если клетка находится в пределах доски
     */
    fun inside(): Boolean = column in 1..8 && row in 1..8

    /**
     * Простая
     *
     * Возвращает строковую нотацию для клетки.
     * В нотации, колонки обозначаются латинскими буквами от a до h, а ряды -- цифрами от 1 до 8.
     * Для клетки не в пределах доски вернуть пустую строку
     */
    fun notation(): String {
        val result = StringBuilder()
        if (inside()) {
            val f = when (column) {
                1 -> 'a'
                2 -> 'b'
                3 -> 'c'
                4 -> 'd'
                5 -> 'e'
                6 -> 'f'
                7 -> 'g'
                8 -> 'h'
                else -> throw IllegalArgumentException()
            }
            result.append(f).append(row)
        }
        return result.toString()
    }
}

/**
 * Простая
 *
 * Создаёт клетку по строковой нотации.
 * В нотации, колонки обозначаются латинскими буквами от a до h, а ряды -- цифрами от 1 до 8.
 * Если нотация некорректна, бросить IllegalArgumentException
 */
fun square(notation: String): Square {
    if (notation.length != 2) throw IllegalArgumentException()
    val k1 = when (notation[0]) {
        'a' -> 1
        'b' -> 2
        'c' -> 3
        'd' -> 4
        'e' -> 5
        'f' -> 6
        'g' -> 7
        'h' -> 8
        else -> throw IllegalArgumentException()
    }
    try {
        val k2 = notation[1].toInt() - 48
        return Square(k1, k2)
    } catch (e: IllegalArgumentException) {
        throw IllegalArgumentException()
    }
}

/**
 * Простая
 *
 * Определить число ходов, за которое шахматная ладья пройдёт из клетки start в клетку end.
 * Шахматная ладья может за один ход переместиться на любую другую клетку
 * по вертикали или горизонтали.
 * Ниже точками выделены возможные ходы ладьи, а крестиками -- невозможные:
 *
 * xx.xxххх
 * xх.хxххх
 * ..Л.....
 * xх.хxххх
 * xx.xxххх
 * xx.xxххх
 * xx.xxххх
 * xx.xxххх
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: rookMoveNumber(Square(3, 1), Square(6, 3)) = 2
 * Ладья может пройти через клетку (3, 3) или через клетку (6, 1) к клетке (6, 3).
 */
fun rookMoveNumber(start: Square, end: Square): Int =
        if ((start.inside()) && (end.inside())) {
            if (start == end) 0
            else if (((start.column == end.column) && (start.row != end.row)) ||
                    ((start.column != end.column) && (start.row == end.row))) 1
            else 2
        } else throw IllegalArgumentException()


/**
 * Средняя
 *
 * Вернуть список из клеток, по которым шахматная ладья может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов ладьи см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры: rookTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          (здесь возможен ещё один вариант)
 *          rookTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(3, 3), Square(6, 3))
 *          (здесь возможен единственный вариант)
 *          rookTrajectory(Square(3, 5), Square(8, 5)) = listOf(Square(3, 5), Square(8, 5))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun rookTrajectory(start: Square, end: Square): List<Square> {
    val result = mutableListOf<Square>()
    result += start
    if (rookMoveNumber(start, end) == 1) result += end
    if (rookMoveNumber(start, end) == 2) {
        result += Square(start.column, end.row)
        result += end
    }
    return result
}

/**
 * Простая
 *
 * Определить число ходов, за которое шахматный слон пройдёт из клетки start в клетку end.
 * Шахматный слон может за один ход переместиться на любую другую клетку по диагонали.
 * Ниже точками выделены возможные ходы слона, а крестиками -- невозможные:
 *
 * .xxx.ххх
 * x.x.xххх
 * xxСxxxxx
 * x.x.xххх
 * .xxx.ххх
 * xxxxx.хх
 * xxxxxх.х
 * xxxxxхх.
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если клетка end недостижима для слона, вернуть -1.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Примеры: bishopMoveNumber(Square(3, 1), Square(6, 3)) = -1; bishopMoveNumber(Square(3, 1), Square(3, 7)) = 2.
 * Слон может пройти через клетку (6, 4) к клетке (3, 7).
 */
fun bishopMoveNumber(start: Square, end: Square): Int {
    if ((start.inside()) && (end.inside())) {
        if (start == end) return 0
        if (abs(start.column - end.column) == abs(start.row - end.row)) return 1
        if (((abs(start.column - start.row) % 2 == 0) &&
                        (abs(end.column - end.row) % 2 == 1)) ||
                ((abs(start.column - start.row) % 2 == 1) &&
                        (abs(end.column - end.row) % 2 == 0))) return -1
        return 2
    } else throw IllegalArgumentException()
}

/**
 * Сложная
 *
 * Вернуть список из клеток, по которым шахматный слон может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов слона см. предыдущую задачу.
 *
 * Если клетка end недостижима для слона, вернуть пустой список.
 *
 * Если клетка достижима:
 * - список всегда включает в себя клетку start
 * - клетка end включается, если она не совпадает со start.
 * - между ними должны находиться промежуточные клетки, по порядку от start до end.
 *
 * Примеры: bishopTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          bishopTrajectory(Square(3, 1), Square(3, 7)) = listOf(Square(3, 1), Square(6, 4), Square(3, 7))
 *          bishopTrajectory(Square(1, 3), Square(6, 8)) = listOf(Square(1, 3), Square(6, 8))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun bishopTrajectory(start: Square, end: Square): List<Square> {
    val result = mutableListOf<Square>()
    if (bishopMoveNumber(start, end) == -1) return result
    result += start
    if (bishopMoveNumber(start, end) == 0) return result
    if (bishopMoveNumber(start, end) == 1) {
        result += end
        return result
    }
    var x = abs(start.column + end.column + abs(start.row - end.row)) / 2
    var y = abs(start.row + end.row + abs(start.column - end.column)) / 2
    if ((abs(x - start.column) != abs(start.row - y)) ||
            (abs(x - end.column) != abs(end.row - y))) {
        y = abs(start.row + end.row - abs(start.column - end.column)) / 2
        x = abs(start.column + end.column - abs(start.row - end.row)) / 2
    }
    if ((abs(x - start.column) != abs(start.row - y)) ||
            (abs(x - end.column) != abs(end.row - y))) {
        y = abs(start.row + end.row + abs(start.column - end.column)) / 2
        x = abs(start.column + end.column - abs(start.row - end.row)) / 2
    }
    if ((abs(x - start.column) != abs(start.row - y)) ||
            (abs(x - end.column) != abs(end.row - y))) {
        y = abs(start.row + end.row - abs(start.column - end.column)) / 2
        x = abs(start.column + end.column + abs(start.row - end.row)) / 2
    }
    result += Square(x, y)
    result += end
    return result
}

/**
 * Средняя
 *
 * Определить число ходов, за которое шахматный король пройдёт из клетки start в клетку end.
 * Шахматный король одним ходом может переместиться из клетки, в которой стоит,
 * на любую соседнюю по вертикали, горизонтали или диагонали.
 * Ниже точками выделены возможные ходы короля, а крестиками -- невозможные:
 *
 * xxxxx
 * x...x
 * x.K.x
 * x...x
 * xxxxx
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: kingMoveNumber(Square(3, 1), Square(6, 3)) = 3.
 * Король может последовательно пройти через клетки (4, 2) и (5, 2) к клетке (6, 3).
 */
fun kingMoveNumber(start: Square, end: Square): Int {
    if ((start.inside()) && (end.inside()))
        return maxOf(abs(start.column - end.column), abs(start.row - end.row))
    else throw IllegalArgumentException()
}

/**
 * Сложная
 *
 * Вернуть список из клеток, по которым шахматный король может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов короля см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры: kingTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          (здесь возможны другие варианты)
 *          kingTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(4, 2), Square(5, 2), Square(6, 3))
 *          (здесь возможен единственный вариант)
 *          kingTrajectory(Square(3, 5), Square(6, 2)) = listOf(Square(3, 5), Square(4, 4), Square(5, 3), Square(6, 2))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun kingTrajectory(start: Square, end: Square): List<Square> {
    val result = mutableListOf<Square>()
    result += start
    if (kingMoveNumber(start, end) == 0) return result
    var d1 = start.column
    var d2 = start.row
    if ((start.column >= end.column) && (start.row >= end.row)) {
        while ((d1 != end.column) && (d2 != end.row)) {
            d1--
            d2--
            result += Square(d1, d2)
        }
        if (d1 == end.column)
            while (d2 != end.row) {
                d2--
                result += Square(d1, d2)
            }
        if (d2 == end.row)
            while (d1 != end.column) {
                d1--
                result += Square(d1, d2)
            }
    }
    if ((start.column <= end.column) && (start.row >= end.row)) {
        while ((d1 != end.column) && (d2 != end.row)) {
            d1++
            d2--
            result += Square(d1, d2)
        }
        if (d1 == end.column)
            while (d2 != end.row) {
                d2--
                result += Square(d1, d2)
            }
        if (d2 == end.row)
            while (d1 != end.column) {
                d1++
                result += Square(d1, d2)
            }
    }
    if ((start.column >= end.column) && (start.row <= end.row)) {
        while ((d1 != end.column) && (d2 != end.row)) {
            d1--
            d2++
            result += Square(d1, d2)
        }
        if (d1 == end.column)
            while (d2 != end.row) {
                d2++
                result += Square(d1, d2)
            }
        if (d2 == end.row)
            while (d1 != end.column) {
                d1--
                result += Square(d1, d2)
            }
    }
    if ((start.column <= end.column) && (start.row <= end.row)) {
        while ((d1 != end.column) && (d2 != end.row)) {
            d1++
            d2++
            result += Square(d1, d2)
        }
        if (d1 == end.column)
            while (d2 != end.row) {
                d2++
                result += Square(d1, d2)
            }
        if (d2 == end.row)
            while (d1 != end.column) {
                d1++
                result += Square(d1, d2)
            }
    }
    return result
}

/**
 * Сложная
 *
 * Определить число ходов, за которое шахматный конь пройдёт из клетки start в клетку end.
 * Шахматный конь одним ходом вначале передвигается ровно на 2 клетки по горизонтали или вертикали,
 * а затем ещё на 1 клетку под прямым углом, образуя букву "Г".
 * Ниже точками выделены возможные ходы коня, а крестиками -- невозможные:
 *
 * .xxx.xxx
 * xxKxxxxx
 * .xxx.xxx
 * x.x.xxxx
 * xxxxxxxx
 * xxxxxxxx
 * xxxxxxxx
 * xxxxxxxx
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: knightMoveNumber(Square(3, 1), Square(6, 3)) = 3.
 * Конь может последовательно пройти через клетки (5, 2) и (4, 4) к клетке (6, 3).
 */
fun knightMoveNumber(start: Square, end: Square): Int = graphchess().bfs(start.notation(), end.notation())

private fun graphchess(): Graph {
    val graph = Graph()
    for (x in 1..8)
        for (y in 1..8)
            graph.addVertex(Square(x, y).notation())
    for (x in 1..8)
        for (y in 1..8) {
            if (Square(x + 1, y + 2).inside())
                graph.connect(Square(x, y).notation(), Square(x + 1, y + 2).notation())
            if (Square(x + 2, y + 1).inside())
                graph.connect(Square(x, y).notation(), Square(x + 2, y + 1).notation())
            if (Square(x - 1, y - 2).inside())
                graph.connect(Square(x, y).notation(), Square(x - 1, y - 2).notation())
            if (Square(x - 2, y - 1).inside())
                graph.connect(Square(x, y).notation(), Square(x - 2, y - 1).notation())
            if (Square(x + 1, y - 2).inside())
                graph.connect(Square(x, y).notation(), Square(x + 1, y - 2).notation())
            if (Square(x - 1, y + 2).inside())
                graph.connect(Square(x, y).notation(), Square(x - 1, y + 2).notation())
            if (Square(x + 2, y - 1).inside())
                graph.connect(Square(x, y).notation(), Square(x + 2, y - 1).notation())
            if (Square(x - 2, y + 1).inside())
                graph.connect(Square(x, y).notation(), Square(x - 2, y + 1).notation())
        }
    return graph
}

/**
 * Очень сложная
 *
 * Вернуть список из клеток, по которым шахматный конь может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов коня см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры:
 *
 * knightTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 * здесь возможны другие варианты)
 * knightTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(5, 2), Square(4, 4), Square(6, 3))
 * (здесь возможен единственный вариант)
 * knightTrajectory(Square(3, 5), Square(5, 6)) = listOf(Square(3, 5), Square(5, 6))
 * (здесь опять возможны другие варианты)
 * knightTrajectory(Square(7, 7), Square(8, 8)) =
 *     listOf(Square(7, 7), Square(5, 8), Square(4, 6), Square(6, 7), Square(8, 8))
 *
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */

fun knightTrajectory(start: Square, end: Square): List<Square> {
    val list = graphchess().bfsWithPath(start.notation(), end.notation())
    val result = mutableListOf<Square>()
    if (list.size == 1) {
        result += start
        return result
    }
    result += end
    var dist = 0
    var sq = end
    for (x in (list.size - 1) downTo 0)
        if (list[x].first == end.notation()) {
            dist = list[x].second
            break
        }
    for (x in (list.size - 1) downTo 0) {
        val square = square(list[x].first)
        val second = list[x].second
        if ((abs(sq.column - square.column) == 2 && abs(sq.row - square.row) == 1 ||
                        abs(sq.column - square.column) == 1 && abs(sq.row - square.row) == 2) &&
                (second == dist - 1)) {
            sq = square
            dist = second
            result += square
        }
    }
    return result.reversed()
}

