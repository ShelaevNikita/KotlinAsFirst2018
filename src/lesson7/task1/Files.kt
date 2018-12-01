@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson7.task1

import lesson3.task1.digitNumber
import java.io.File
import java.lang.Math.pow

/**
 * Пример
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Вывести его в выходной файл с именем outputName, выровняв по левому краю,
 * чтобы длина каждой строки не превосходила lineLength.
 * Слова в слишком длинных строках следует переносить на следующую строку.
 * Слишком короткие строки следует дополнять словами из следующей строки.
 * Пустые строки во входном файле обозначают конец абзаца,
 * их следует сохранить и в выходном файле
 */
fun alignFile(inputName: String, lineLength: Int, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    var currentLineLength = 0
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            outputStream.newLine()
            if (currentLineLength > 0) {
                outputStream.newLine()
                currentLineLength = 0
            }
            continue
        }
        for (word in line.split(" ")) {
            if (currentLineLength > 0) {
                if (word.length + currentLineLength >= lineLength) {
                    outputStream.newLine()
                    currentLineLength = 0
                } else {
                    outputStream.write(" ")
                    currentLineLength++
                }
            }
            outputStream.write(word)
            currentLineLength += word.length
        }
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * На вход подаётся список строк substrings.
 * Вернуть ассоциативный массив с числом вхождений каждой из строк в текст.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 */
fun countSubstrings(inputName: String, substrings: List<String>): Map<String, Int> {
    val result = mutableMapOf<String, Int>()
    val file = File(inputName).readText()
    for (c in substrings) {
        val s = Regex(c.toLowerCase())
                .findAll(file.toLowerCase(), 0).count()
        result += (c to s)
    }
    return result
}


/**
 * Средняя
 *
 * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
 * Во входном файле с именем inputName содержится некоторый текст на русском языке.
 * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
 * файл outputName текст с исправленными ошибками.
 *
 * Регистр заменённых букв следует сохранять.
 *
 * Исключения (жюри, брошюра, парашют) в рамках данного задания обрабатывать не нужно
 *
 */
fun sibilants(inputName: String, outputName: String) {
    val g = File(inputName).readText()
    val result = File(outputName).bufferedWriter()
    result.write(g[0].toString())
    for (x in 1 until g.length) {
        var k = g[x]
        if ((g[x - 1] == 'Ч') || (g[x - 1] == 'ч') || (g[x - 1] == 'Щ') ||
                (g[x - 1] == 'щ') || (g[x - 1] == 'Ш') || (g[x - 1] == 'ш') ||
                (g[x - 1] == 'Ж') || (g[x - 1] == 'ж')) {
            if (g[x] == 'Я') k = 'А'
            if (g[x] == 'я') k = 'а'
            if (g[x] == 'Ы') k = 'И'
            if (g[x] == 'ы') k = 'и'
            if (g[x] == 'Ю') k = 'У'
            if (g[x] == 'ю') k = 'у'
        }
        result.write(k.toString())
    }
    result.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по центру
 * относительно самой длинной строки.
 *
 * Выравнивание следует производить путём добавления пробелов в начало строки.
 *
 *
 * Следующие правила должны быть выполнены:
 * 1) Пробелы в начале и в конце всех строк не следует сохранять.
 * 2) В случае невозможности выравнивания строго по центру, строка должна быть сдвинута в ЛЕВУЮ сторону
 * 3) Пустые строки не являются особым случаем, их тоже следует выравнивать
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых)
 *
 */
fun centerFile(inputName: String, outputName: String) {
    val file = File(inputName).readLines().toMutableList()
    val result = File(outputName).bufferedWriter()
    for (x in 0 until file.size) file[x] = file[x].trim()
    var max = 0
    for (x in file) max = maxOf(x.length, max)
    for (x in file) {
        val d = StringBuilder()
        val h = (max - x.length) / 2
        for (p in 0 until h) d.append(' ')
        d.append(x)
        result.write(d.toString())
        result.newLine()
    }
    result.close()
}

/**
 * Сложная
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по левому и правому краю относительно
 * самой длинной строки.
 * Выравнивание производить, вставляя дополнительные пробелы между словами: равномерно по всей строке
 *
 * Слова внутри строки отделяются друг от друга одним или более пробелом.
 *
 * Следующие правила должны быть выполнены:
 * 1) Каждая строка входного и выходного файла не должна начинаться или заканчиваться пробелом.
 * 2) Пустые строки или строки из пробелов трансформируются в пустые строки без пробелов.
 * 3) Строки из одного слова выводятся без пробелов.
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых).
 *
 * Равномерность определяется следующими формальными правилами:
 * 5) Число пробелов между каждыми двумя парами соседних слов не должно отличаться более, чем на 1.
 * 6) Число пробелов между более левой парой соседних слов должно быть больше или равно числу пробелов
 *    между более правой парой соседних слов.
 *
 * Следует учесть, что входной файл может содержать последовательности из нескольких пробелов  между слвоами. Такие
 * последовательности следует учитывать при выравнивании и при необходимости избавляться от лишних пробелов.
 * Из этого следуют следующие правила:
 * 7) В самой длинной строке каждая пара соседних слов должна быть отделена В ТОЧНОСТИ одним пробелом
 * 8) Если входной файл удовлетворяет требованиям 1-7, то он должен быть в точности идентичен выходному файлу
 */
fun alignFileByWidth(inputName: String, outputName: String) {
    val file = File(inputName).readLines().toMutableList()
    val result = File(outputName).bufferedWriter()
    for (x in 0 until file.size) file[x] = file[x].trim()
    var max = 0
    for (x in file) max = maxOf(x.length, max)
    for (x in 0 until file.size) {
        val count = max - file[x].length
        var s1 = 0
        var s2 = 0
        if (file[x].isEmpty()) s1++
        while ((file[x].length != max) && (s1 == 0)) {
            val d = StringBuilder()
            for (char in 0 until file[x].length) {
                d.append((file[x])[char])
                if (((file[x])[char] == ' ') && (char >= 1) &&
                        ((file[x])[char - 1] != ' ') && (s2 != count)) {
                    d.append(' ')
                    s2++
                }
                if (d.length == max) break
                if ((char == file[x].length - 1) && (s2 == 0)) {
                    s1++
                    break
                }
            }
            file[x] = d.toString()
        }
        result.write(file[x])
        result.newLine()
    }
    result.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * Вернуть ассоциативный массив, содержащий 20 наиболее часто встречающихся слов с их количеством.
 * Если в тексте менее 20 различных слов, вернуть все слова.
 *
 * Словом считается непрерывная последовательность из букв (кириллических,
 * либо латинских, без знаков препинания и цифр).
 * Цифры, пробелы, знаки препинания считаются разделителями слов:
 * Привет, привет42, привет!!! -привет?!
 * ^ В этой строчке слово привет встречается 4 раза.
 *
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 * Ключи в ассоциативном массиве должны быть в нижнем регистре.
 *
 */
fun top20Words(inputName: String): Map<String, Int> {
    val result = mutableMapOf<String, Int>()
    val f = StringBuilder()
    for ((q, string) in File(inputName).readLines().withIndex()) {
        if (q > 0) f.append(' ')
        f.append(string.toLowerCase()
                .replace(Regex("""[1234567890.,!?:;—()«»*/-]"""), " "))
    }
    val s = f.toString().split(' ')
    var count = 0
    for (x in s)
        if (x.isNotEmpty()) result += (x to 0)
    for (x in result.keys) {
        for (y in s) if (x == y) count++
        result[x] = count
        count = 0
    }
    return result.toList().sortedBy { it.second }.reversed().take(20).toMap()
}

/**
 * Средняя
 *
 * Реализовать транслитерацию текста из входного файла в выходной файл посредством динамически задаваемых правил.

 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * В ассоциативном массиве dictionary содержится словарь, в котором некоторым символам
 * ставится в соответствие строчка из символов, например
 * mapOf('з' to "zz", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "yy", '!' to "!!!")
 *
 * Необходимо вывести в итоговый файл с именем outputName
 * содержимое текста с заменой всех символов из словаря на соответствующие им строки.
 *
 * При этом регистр символов в словаре должен игнорироваться,
 * но при выводе символ в верхнем регистре отображается в строку, начинающуюся с символа в верхнем регистре.
 *
 * Пример.
 * Входной текст: Здравствуй, мир!
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Пример 2.
 *
 * Входной текст: Здравствуй, мир!
 * Словарь: mapOf('з' to "zZ", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "YY", '!' to "!!!")
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun transliterate(inputName: String, dictionary: Map<Char, String>, outputName: String) {
    val file = File(inputName).readText()
    val result = File(outputName).bufferedWriter()
    val f = StringBuilder()
    val dict = mutableMapOf<Char, String>()
    for ((x, y) in dictionary) dict += (x.toLowerCase() to y.toLowerCase())
    for (ch in 0 until file.length) {
        var g = 0
        for ((char, string) in dict)
            if (file[ch].toLowerCase() == char) {
                if ((file[ch] != char) && (string.isNotEmpty())) {
                    f.append(string.capitalize())
                } else f.append(string)
                g++
                break
            }
        if (g == 0) f.append(file[ch])
    }
    result.write(f.toString())
    result.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName имеется словарь с одним словом в каждой строчке.
 * Выбрать из данного словаря наиболее длинное слово,
 * в котором все буквы разные, например: Неряшливость, Четырёхдюймовка.
 * Вывести его в выходной файл с именем outputName.
 * Если во входном файле имеется несколько слов с одинаковой длиной, в которых все буквы разные,
 * в выходной файл следует вывести их все через запятую.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 * Пример входного файла:
 * Карминовый
 * Боязливый
 * Некрасивый
 * Остроумный
 * БелогЛазый
 * ФиолетОвый

 * Соответствующий выходной файл:
 * Карминовый, Некрасивый
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun chooseLongestChaoticWord(inputName: String, outputName: String) {
    val file = File(inputName).readLines().toList()
    val result = File(outputName).bufferedWriter()
    val list = mutableListOf<String>()
    val res = StringBuilder()
    var max = 0
    for (x in file) {
        val set = mutableSetOf<Char>()
        for (y in x) set += y.toLowerCase()
        if (set.size == x.length) {
            list += x
            max = maxOf(max, x.length)
        }
    }
    for (string in list) {
        if (string.length == max) {
            if (res.isNotEmpty()) res.append(", ")
            res.append(string)
        }
    }
    result.write(res.toString())
    result.close()
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе элементы текстовой разметки следующих типов:
 * - *текст в курсивном начертании* -- курсив
 * - **текст в полужирном начертании** -- полужирный
 * - ~~зачёркнутый текст~~ -- зачёркивание
 *
 * Следует вывести в выходной файл этот же текст в формате HTML:
 * - <i>текст в курсивном начертании</i>
 * - <b>текст в полужирном начертании</b>
 * - <s>зачёркнутый текст</s>
 *
 * Кроме того, все абзацы исходного текста, отделённые друг от друга пустыми строками, следует обернуть в теги <p>...</p>,
 * а весь текст целиком в теги <html><body>...</body></html>.
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 * Отдельно следует заметить, что открывающая последовательность из трёх звёздочек (***) должна трактоваться как "<b><i>"
 * и никак иначе.
 *
 * Пример входного файла:
Lorem ipsum *dolor sit amet*, consectetur **adipiscing** elit.
Vestibulum lobortis, ~~Est vehicula rutrum *suscipit*~~, ipsum ~~lib~~ero *placerat **tortor***,

Suspendisse ~~et elit in enim tempus iaculis~~.
 *
 * Соответствующий выходной файл:
<html>
<body>
<p>
Lorem ipsum <i>dolor sit amet</i>, consectetur <b>adipiscing</b> elit.
Vestibulum lobortis. <s>Est vehicula rutrum <i>suscipit</i></s>, ipsum <s>lib</s>ero <i>placerat <b>tortor</b></i>.
</p>
<p>
Suspendisse <s>et elit in enim tempus iaculis</s>.
</p>
</body>
</html>
 *
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlSimple(inputName: String, outputName: String) {
    val file = File(inputName).readLines().toMutableList()
    val result = File(outputName).bufferedWriter()
    val string = StringBuilder()
    val list = listOf("<html>", "<body>", "<p>", "</html>", "</body>", "</p>")
    for (x in 0..2) {
        result.write(list[x])
        result.newLine()
    }
    var s1 = 0
    var s2 = 0
    var s3 = 0
    var count = 0
    for (x in file) {
        var find1 = Regex("~~").findAll(x, 0).count()
        var find2 = Regex("\\*\\*").findAll(x, 0).count()
        var find3 = Regex("\\*").findAll(x, 0).count()
        if (x.isNotEmpty()) {
            for (char in 0 until x.length) {
                if (count <= char) {
                    if ((find1 >= 2) && (x[char] == '~') && (s1 == 0) &&
                            (char < x.length - 1) && (x[char + 1] == '~')) {
                        s1 = 1
                        find1--
                        string.append("<s>")
                        count += 2
                        continue
                    }
                    if ((x[char] == '~') && (s1 == 1) &&
                            (char < x.length - 1) && (x[char + 1] == '~')) {
                        s1 = 0
                        find1--
                        string.append("</s>")
                        count += 2
                        continue
                    }
                    if ((find2 >= 2) && (x[char] == '*') && (s2 == 0) &&
                            (char < x.length - 1) && (x[char + 1] == '*')) {
                        s2 = 1
                        find2--
                        string.append("<b>")
                        count += 2
                        continue
                    }
                    if ((find2 < 2) && (x[char] == '*') && (s2 == 0) &&
                            (char < x.length - 1) && (x[char + 1] == '*')) {
                        count += 2
                        continue
                    }
                    if ((x[char] == '*') && (s2 == 1) &&
                            (char < x.length - 1) && (x[char + 1] == '*')) {
                        s2 = 0
                        find2--
                        string.append("</b>")
                        count += 2
                        continue
                    }
                    if ((find3 >= 2) && (x[char] == '*') && (s3 == 0)) {
                        s3 = 1
                        find3--
                        count++
                        string.append("<i>")
                        continue
                    }
                    if ((x[char] == '*') && (s3 == 1)) {
                        s3 = 0
                        find3--
                        count++
                        string.append("</i>")
                        continue
                    }
                    string.append(x[char])
                    count++
                }
            }
            count = 0
            result.write(string.toString())
            result.newLine()
            string.delete(0, string.length)
        } else {
            result.write(list[5])
            result.newLine()
            result.write(list[2])
            result.newLine()
        }
    }
    for (x in 5 downTo 3) {
        result.write(list[x])
        if (x != 3) result.newLine()
    }
    result.close()
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе набор вложенных друг в друга списков.
 * Списки бывают двух типов: нумерованные и ненумерованные.
 *
 * Каждый элемент ненумерованного списка начинается с новой строки и символа '*', каждый элемент нумерованного списка --
 * с новой строки, числа и точки. Каждый элемент вложенного списка начинается с отступа из пробелов, на 4 пробела большего,
 * чем список-родитель. Максимально глубина вложенности списков может достигать 6. "Верхние" списки файла начинются
 * прямо с начала строки.
 *
 * Следует вывести этот же текст в выходной файл в формате HTML:
 * Нумерованный список:
 * <ol>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ol>
 *
 * Ненумерованный список:
 * <ul>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ul>
 *
 * Кроме того, весь текст целиком следует обернуть в теги <html><body>...</body></html>
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 *
 * Пример входного файла:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
 * Утка по-пекински
 * Утка
 * Соус
 * Салат Оливье
1. Мясо
 * Или колбаса
2. Майонез
3. Картофель
4. Что-то там ещё
 * Помидоры
 * Фрукты
1. Бананы
23. Яблоки
1. Красные
2. Зелёные
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 *
 *
 * Соответствующий выходной файл:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
<html>
<body>
<ul>
<li>
Утка по-пекински
<ul>
<li>Утка</li>
<li>Соус</li>
</ul>
</li>
<li>
Салат Оливье
<ol>
<li>Мясо
<ul>
<li>
Или колбаса
</li>
</ul>
</li>
<li>Майонез</li>
<li>Картофель</li>
<li>Что-то там ещё</li>
</ol>
</li>
<li>Помидоры</li>
<li>
Яблоки
<ol>
<li>Красные</li>
<li>Зелёные</li>
</ol>
</li>
</ul>
</body>
</html>
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlLists(inputName: String, outputName: String) {
    val file = File(inputName).readLines().toMutableList()
    val result = File(outputName).bufferedWriter()
    val string = StringBuilder()
    result.write("<html>")
    result.newLine()
    result.write("<body>")
    result.newLine()
    for (x in 0 until file.size) {
        var s1 = 0
        var s2 = 0
        var s3 = 0
        for (char in file[x]) if (char == ' ') s1++ else break
        if (x < file.size - 1)
            for (char in file[x + 1]) if (char == ' ') s2++ else break
        if (x >= 1)
            for (char in file[x - 1]) if (char == ' ') s3++ else break
        if ((file[x])[s1] == '*') {
            if ((s1 > s3) || (x == 0)) {
                result.write("<ul>")
                result.newLine()
            }
            for (y in 0 until s1) string.append(' ')
            string.append("<li>")
            for (y in (s1 + 2) until file[x].length)
                string.append((file[x])[y])
            if (s2 <= s1) string.append("</li>")
            result.write(string.toString())
            result.newLine()
            string.delete(0, string.length)
            if (s2 < s1) for (c in 0..((s1 - 4 - s2) / 4)) {
                result.write("</ul>")
                result.newLine()
                result.write("</li>")
                result.newLine()
            }
        } else {
            if ((s1 > s3) || (x == 0)) {
                result.write("<ol>")
                result.newLine()
            }
            for (y in 0 until s1) string.append(' ')
            string.append("<li>")
            var f = s1 + 1
            for (y in (s1 + 1) until file[x].length)
                if ((file[x])[y] != '.') f++ else break
            for (y in (f + 1) until file[x].length)
                string.append((file[x])[y])
            if (s2 <= s1) string.append("</li>")
            result.write(string.toString())
            result.newLine()
            string.delete(0, string.length)
            if (s2 < s1) for (c in 0..((s1 - 4 - s2) / 4)) {
                result.write("</ol>")
                result.newLine()
                result.write("</li>")
                result.newLine()
            }
        }
    }
    if ((file[0])[0] == '*') result.write("</ul>") else result.write("</ol>")
    result.write("</body>")
    result.newLine()
    result.write("</html>")
    result.close()
}

/**
 * Очень сложная
 *
 * Реализовать преобразования из двух предыдущих задач одновременно над одним и тем же файлом.
 * Следует помнить, что:
 * - Списки, отделённые друг от друга пустой строкой, являются разными и должны оказаться в разных параграфах выходного файла.
 *
 */
fun markdownToHtml(inputName: String, outputName: String) {
    TODO()
}

/**
 * Средняя
 *
 * Вывести в выходной файл процесс умножения столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 111):
19935
 *    111
--------
19935
+ 19935
+19935
--------
2212785
 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 * Нули в множителе обрабатывать так же, как и остальные цифры:
235
 *  10
-----
0
+235
-----
2350
 *
 */
fun printMultiplicationProcess(lhv: Int, rhv: Int, outputName: String) {
    val result = File(outputName).bufferedWriter()
    val size = digitNumber(lhv * rhv) + 1
    val def = StringBuilder()
    val list = mutableListOf<Int>()
    list += lhv
    list += rhv
    for (x in 0 until digitNumber(rhv)) {
        list += lhv * (rhv / pow(10.0, x.toDouble()) % 10).toInt()
    }
    list += lhv * rhv
    while (def.length != size) def.append('-')
    for (x in 0 until list.size) {
        val string = StringBuilder()
        var d = 0
        if (x == 1) {
            string.append('*')
            d++
        }
        if ((x == 2) || (x == list.size - 1)) {
            result.write(def.toString())
            result.newLine()
        }
        if ((x >= 3) && (x < list.size - 1)) {
            string.append('+')
            d += x - 1
        }
        while (d + digitNumber(list[x]) != size) {
            string.append(' ')
            d++
        }
        string.append(list[x])
        result.write(string.toString())
        result.newLine()
    }
    result.close()
}


/**
 * Сложная
 *
 * Вывести в выходной файл процесс деления столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 22):
19935 | 22
-198     906
----
13
-0
--
135
-132
----
3

 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 *
 */
fun printDivisionProcess(lhv: Int, rhv: Int, outputName: String) {
    val result = File(outputName).bufferedWriter()
    val digitlhv = digitNumber(lhv)
    val del = lhv / rhv
    val string = StringBuilder()
    var s1 = 0
    var s2 = 0
    var length = 0
    for (x in 0 until digitNumber(del)) {
        val number = (del / pow(10.0,
                (digitNumber(del) - x - 1).toDouble()) % 10).toInt()
        val pz = rhv * number
        if (x == 0) {
            if (digitNumber(pz) + 1 != digitlhv)
                string.append(' ')
            string.append(lhv).append(" | ").append(rhv)
            result.write(string.toString())
            result.newLine()
            string.delete(0, string.length)
            length += digitNumber(pz)
            if (digitNumber(pz) + 1 != digitlhv)
                s1 = lhv / pow(10.0, (digitlhv - length).toDouble()).toInt() - pz
            else s1 = lhv / pow(10.0, (digitlhv - length - 1).toDouble()).toInt() - pz
            string.append('-')
            string.append(pz)
            for (y in 0 until (digitlhv - length + 2))
                string.append(' ')
            if (digitNumber(pz) + 1 != digitlhv)
                string.append(' ')
            string.append(del)
        } else {
            length++
            s1 = s1 * 10 + s2 - pz
            for (y in 0 until (length - digitNumber(pz)))
                string.append(' ')
            string.append('-').append(pz)
        }
        var max: Int
        if (x != 0) max = maxOf(digitNumber(pz) + 1, digitNumber(s1))
        else max = digitNumber(pz) + 1
        result.write(string.toString())
        result.newLine()
        string.delete(0, string.length)
        if (x != 0) for (y in 0 until (length + 1 - max))
            string.append(' ')
        for (y in 0 until max) string.append('-')
        result.write(string.toString())
        result.newLine()
        string.delete(0, string.length)
        if ((length != digitlhv) && (digitNumber(pz) + 1 < digitlhv)) {
            s2 = (lhv / (pow(10.0,
                    (digitlhv - length - 1).toDouble()).toInt())) % 10
            for (y in 0 until length + 1 - digitNumber(s1))
                string.append(' ')
            if (s1 == 0) string.append(0)
            string.append(s1 * 10 + s2)
        } else {
            for (y in 0 until length + 1 - digitNumber(s1)) string.append(' ')
            string.append(s1)
        }
        result.write(string.toString())
        result.newLine()
        string.delete(0, string.length)
    }
    result.close()
}

