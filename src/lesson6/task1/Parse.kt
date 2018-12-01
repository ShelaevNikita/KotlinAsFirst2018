@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

import lesson2.task2.daysInMonth

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main(args: Array<String>) {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}


/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
fun dateStrToDigit(str: String): String {
    val f = str.split(' ')
    try {
        val r = if (f.size == 3) when (f[1]) {
            "января" -> 1
            "февраля" -> 2
            "марта" -> 3
            "апреля" -> 4
            "мая" -> 5
            "июня" -> 6
            "июля" -> 7
            "августа" -> 8
            "сентября" -> 9
            "октября" -> 10
            "ноября" -> 11
            "декабря" -> 12
            else -> -1
        } else -1
        if (r != -1) return ""
        val days = daysInMonth(r, f[2].toInt())
        return if (f[0].toInt() <= days) String.format("%02d.%02d.%d",
                f[0].toInt(), r, f[2].toInt()) else ""
    } catch (e: NumberFormatException) {
        return ""
    }
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun dateDigitToStr(digital: String): String {
    val f = digital.split('.')
    val r: String
    try {
        r = if (f.size == 3) when (f[1].toInt()) {
            1 -> "января"
            2 -> "февраля"
            3 -> "марта"
            4 -> "апреля"
            5 -> "мая"
            6 -> "июня"
            7 -> "июля"
            8 -> "августа"
            9 -> "сентября"
            10 -> "октября"
            11 -> "ноября"
            12 -> "декабря"
            else -> ""
        } else ""
        if (r.isNotEmpty()) return ""
        val days = daysInMonth(f[1].toInt(), f[2].toInt())
        return if (f[0].toInt() <= days)
            String.format("%d %s %d", f[0].toInt(), r, f[2].toInt()) else ""
    } catch (e: NumberFormatException) {
        return ""
    }
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -98 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку
 */
fun flattenPhoneNumber(phone: String): String {
    val d = phone[0]
    val f = phone.replace(Regex("""[-+ ()]"""), "")
    val result = StringBuilder()
    try {
        f.toDouble()
        if (d == '+') result.append('+')
        result.append(f)
    } catch (e: NumberFormatException) {
        return ""
    }
    return result.toString()
}

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    val f = jumps.split(' ')
    var max = -1
    return try {
        for (x in f)
            if ((x != "-") && (x != "%") && (x.isNotEmpty()))
                max = maxOf(x.toInt(), max)
        max
    } catch (e: NumberFormatException) {
        -1
    }
}

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    var max = -1
    val f = jumps.split(' ')
    return try {
        for (x in 0 until (f.size - 1))
            if ((f[x + 1] == "+") || (f[x + 1] == "%+") || (f[x + 1] == "%%+"))
                max = maxOf(max, f[x].toInt())
        max
    } catch (e: NumberFormatException) {
        -1
    }
}


/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    val f = expression.split(' ')
    for (x in 0 until f.size step 2)
        if (f[x].isNotEmpty()) {
            for (y in f[x])
                if (y !in '0'..'9')
                    throw IllegalArgumentException()
        } else throw IllegalArgumentException()
    var result = f[0].toInt()
    for (x in 1 until (f.size - 1) step 2) {
        when (f[x]) {
            "+" -> result += f[x + 1].toInt()
            "-" -> result -= f[x + 1].toInt()
            else -> throw IllegalArgumentException()
        }
    }
    return result
}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    val f = str.toLowerCase().split(' ')
    var g = 0
    var result = -1
    for (x in 0 until (f.size - 1)) {
        if (f[x] == f[x + 1]) {
            result = x + g
            break
        }
        g += f[x].length
    }
    return result
}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше либо равны нуля.
 */
fun mostExpensive(description: String): String {
    val f = description.split("; ")
    var result = ""
    var s = 0.0
    var max = 0.0
    return try {
        for (x in f) {
            val w = x.split(' ')
            if ((x.isEmpty()) || (w[1].toDouble() < 0)) return ""
            max = maxOf(max, w[1].toDouble())
            if (s != max) result = w[0]
            s = max
        }
        result
    } catch (e: NumberFormatException) {
        ""
    }
}

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int {
    var result = 0
    var k = ' '
    var f = 0
    if (roman.isEmpty()) {
        result = -1
        return result
    }
    for (x in 0 until roman.length) {
        val d = roman[x]
        if (x <= (roman.length - 2)) k = roman[x + 1]
        if (x == f) {
            when {
                d == 'M' -> result += 1000
                (d == 'C') && (k == 'M') -> {
                    result += 900
                    f++
                }
                d == 'D' -> result += 500
                (d == 'C') && (k == 'D') -> {
                    result += 400
                    f++
                }
                d == 'C' -> result += 100
                (d == 'X') && (k == 'C') -> {
                    result += 90
                    f++
                }
                d == 'L' -> result += 50
                (d == 'X') && (k == 'L') -> {
                    result += 40
                    f++
                }
                d == 'X' -> result += 10
                (d == 'I') && (k == 'X') -> {
                    result += 9
                    f++
                }
                d == 'V' -> result += 5
                (d == 'I') && (k == 'V') -> {
                    result += 4
                    f++
                }
                d == 'I' -> result += 1
                else -> result = -1
            }
            f++
        }
        if (result == -1) break
    }
    return result
}

/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> {
    val list = mutableListOf<Int>()
    var n = cells / 2
    var hop = 0
    for (x in 0 until cells) list += 0
    var s1 = 0
    val str1 = StringBuilder()
    val str2 = StringBuilder()
    for (x in commands) {
        if (x == '[') s1++
        if (x == ']') s1--
    }
    if (s1 != 0) throw IllegalArgumentException()
    for (x in 0 until commands.length) if (hop < limit) {
        if ((n >= 0) && (n <= cells - 1)) {
            if (x == s1) {
                when (commands[x]) {
                    '<' -> n--
                    '>' -> n++
                    '+' -> list[n]++
                    '-' -> list[n]--
                    ' ' -> Double.NaN
                    '[' -> {
                        var s2 = 1
                        for (char1 in (x + 1) until commands.length) {
                            if (commands[char1] == '[') s2++
                            if (commands[char1] == ']') s2--
                            if (s2 == 0) break else str1.append(commands[char1])
                        }
                        s1 += str1.length + 1
                        while ((list[n] != 0) && (hop < limit - 1)) {
                            s2 = 0
                            hop++
                            for (char in 0 until str1.length) {
                                if (char == s2) {
                                    when (str1[char]) {
                                        '<' -> n--
                                        '>' -> n++
                                        '+' -> list[n]++
                                        '-' -> list[n]--
                                        '[' -> {
                                            for (char2 in (char + 1) until str1.length)
                                                if (str1[char2] == ']') break else str2.append(str1[char2])
                                            s2 += str2.length + 1
                                            while ((list[n] != 0) && (hop < limit - 1)) {
                                                hop++
                                                for (char3 in str2.toString()) {
                                                    when (char3) {
                                                        '<' -> n--
                                                        '>' -> n++
                                                        '+' -> list[n]++
                                                        '-' -> list[n]--
                                                        else -> Double.NaN
                                                    }
                                                    if (hop == limit - 1) break
                                                    hop++
                                                }
                                            }
                                        }
                                        else -> Double.NaN
                                    }
                                    str2.delete(0, str2.length)
                                    s2++
                                    if (hop == limit - 1) break
                                    hop++
                                }
                            }
                        }
                    }
                    else -> throw IllegalArgumentException()
                }
                str1.delete(0, str1.length)
                s1++
                hop++
            }
        } else throw IllegalStateException()
    } else break
    return list
}
