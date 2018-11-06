package lesson5.task1

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

class Tests {
    @Test
    @Tag("Example")
    fun shoppingListCostTest() {
        val itemCosts = mapOf(
                "Хлеб" to 50.0,
                "Молоко" to 100.0
        )
        assertEquals(
                150.0,
                shoppingListCost(
                        listOf("Хлеб", "Молоко"),
                        itemCosts
                )
        )
        assertEquals(
                150.0,
                shoppingListCost(
                        listOf("Хлеб", "Молоко", "Кефир"),
                        itemCosts
                )
        )
        assertEquals(
                0.0,
                shoppingListCost(
                        listOf("Хлеб", "Молоко", "Кефир"),
                        mapOf()
                )
        )
    }

    @Test
    @Tag("Example")
    fun filterByCountryCode() {
        val phoneBook = mutableMapOf(
                "Quagmire" to "+1-800-555-0143",
                "Adam's Ribs" to "+82-000-555-2960",
                "Pharmakon Industries" to "+1-800-555-6321"
        )

        filterByCountryCode(phoneBook, "+1")
        assertEquals(2, phoneBook.size)

        filterByCountryCode(phoneBook, "+1")
        assertEquals(2, phoneBook.size)

        filterByCountryCode(phoneBook, "+999")
        assertEquals(0, phoneBook.size)
    }

    @Test
    @Tag("Example")
    fun removeFillerWords() {
        assertEquals(
                "Я люблю Котлин".split(" "),
                removeFillerWords(
                        "Я как-то люблю Котлин".split(" "),
                        "как-то"
                )
        )
        assertEquals(
                "Я люблю Котлин".split(" "),
                removeFillerWords(
                        "Я как-то люблю таки Котлин".split(" "),
                        "как-то",
                        "таки"
                )
        )
        assertEquals(
                "Я люблю Котлин".split(" "),
                removeFillerWords(
                        "Я люблю Котлин".split(" "),
                        "как-то",
                        "таки"
                )
        )
    }

    @Test
    @Tag("Example")
    fun buildWordSet() {
        assertEquals(
                buildWordSet("Я люблю Котлин".split(" ")),
                mutableSetOf("Я", "люблю", "Котлин")
        )
        assertEquals(
                buildWordSet("Я люблю люблю Котлин".split(" ")),
                mutableSetOf("Котлин", "люблю", "Я")
        )
        assertEquals(
                buildWordSet(listOf()),
                mutableSetOf<String>()
        )
    }

    @Test
    @Tag("Normal")
    fun mergePhoneBooks() {
        assertEquals(
                mapOf("Emergency" to "112"),
                mergePhoneBooks(
                        mapOf("Emergency" to "112"),
                        mapOf("Emergency" to "112")
                )
        )
        assertEquals(
                mapOf("Emergency" to "112", "Police" to "02"),
                mergePhoneBooks(
                        mapOf("Emergency" to "112"),
                        mapOf("Emergency" to "112", "Police" to "02")
                )
        )
        assertEquals(
                mapOf("Emergency" to "112, 911", "Police" to "02"),
                mergePhoneBooks(
                        mapOf("Emergency" to "112"),
                        mapOf("Emergency" to "911", "Police" to "02")
                )
        )
        assertEquals(
                mapOf("Emergency" to "112, 911", "Fire department" to "01", "Police" to "02"),
                mergePhoneBooks(
                        mapOf("Emergency" to "112", "Fire department" to "01"),
                        mapOf("Emergency" to "911", "Police" to "02")
                )
        )
    }

    @Test
    @Tag("Easy")
    fun buildGrades() {
        assertEquals(
                mapOf<Int, List<String>>(),
                buildGrades(mapOf())
        )
        // TODO: Sort the values here or let the students do it?
        assertEquals(
                mapOf(5 to listOf("Семён", "Михаил"), 3 to listOf("Марат")),
                buildGrades(mapOf("Марат" to 3, "Семён" to 5, "Михаил" to 5))
        )
        assertEquals(
                mapOf(3 to listOf("Марат", "Семён", "Михаил")),
                buildGrades(mapOf("Марат" to 3, "Семён" to 3, "Михаил" to 3))
        )
    }

    @Test
    @Tag("Easy")
    fun containsIn() {
        assertTrue(containsIn(mapOf("a" to "z"), mapOf("a" to "z", "b" to "sweet")))
        assertFalse(containsIn(mapOf("a" to "z"), mapOf("a" to "zee", "b" to "sweet")))
    }

    @Test
    @Tag("Normal")
    fun averageStockPrice() {
        assertEquals(
                mapOf<String, Double>(),
                averageStockPrice(listOf())
        )
        assertEquals(
                mapOf("MSFT" to 100.0, "NFLX" to 40.0),
                averageStockPrice(listOf("MSFT" to 100.0, "NFLX" to 40.0))
        )
        assertEquals(
                mapOf("MSFT" to 150.0, "NFLX" to 40.0),
                averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0))
        )
        assertEquals(
                mapOf("MSFT" to 150.0, "NFLX" to 45.0),
                averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0, "NFLX" to 50.0))
        )
    }

    @Test
    @Tag("Normal")
    fun findCheapestStuff() {
        assertNull(
                findCheapestStuff(
                        mapOf("Мария" to ("печенье" to 20.0), "Орео" to ("печенье" to 100.0)),
                        "торт"
                )
        )
        assertEquals(
                "Мария",
                findCheapestStuff(
                        mapOf("Мария" to ("печенье" to 20.0), "Орео" to ("печенье" to 100.0)),
                        "печенье"
                )
        )
    }

    @Test
    @Tag("Hard")
    fun propagateHandshakes() {
        assertEquals(
                mapOf(
                        "Marat" to setOf("Mikhail", "Sveta"),
                        "Sveta" to setOf("Mikhail"),
                        "Mikhail" to setOf()
                ),
                propagateHandshakes(
                        mapOf(
                                "Marat" to setOf("Sveta"),
                                "Sveta" to setOf("Mikhail")
                        )
                )
        )
        assertEquals(
                mapOf(
                        "Marat" to setOf("Mikhail", "Sveta"),
                        "Sveta" to setOf("Marat", "Mikhail"),
                        "Mikhail" to setOf("Sveta", "Marat")
                ),
                propagateHandshakes(
                        mapOf(
                                "Marat" to setOf("Mikhail", "Sveta"),
                                "Sveta" to setOf("Marat"),
                                "Mikhail" to setOf("Sveta")
                        )
                )
        )
        assertEquals(
                mapOf(
                        "0" to setOf(),
                        "1" to setOf("0"),
                        "2" to setOf("0", "1", "3", "4"),
                        "3" to setOf("4", "2", "1", "0"),
                        "4" to setOf("2", "1", "0", "3")
                ),
                propagateHandshakes(
                        mapOf(
                                "0" to setOf(),
                                "1" to setOf("0"),
                                "2" to setOf("1", "3"),
                                "3" to setOf("4"),
                                "4" to setOf("2")
                        )
                )
        )
    }

    @Test
    @Tag("Easy")
    fun subtractOf() {
        val from = mutableMapOf("a" to "z", "b" to "c")

        subtractOf(from, mapOf())
        assertEquals(from, mapOf("a" to "z", "b" to "c"))

        subtractOf(from, mapOf("b" to "z"))
        assertEquals(from, mapOf("a" to "z", "b" to "c"))

        subtractOf(from, mapOf("a" to "z"))
        assertEquals(from, mapOf("b" to "c"))
    }

    @Test
    @Tag("Easy")
    fun whoAreInBoth() {
        assertEquals(
                emptyList<String>(),
                whoAreInBoth(emptyList(), emptyList())
        )
        assertEquals(
                listOf("Marat"),
                whoAreInBoth(listOf("Marat", "Mikhail"), listOf("Marat", "Kirill"))
        )
        assertEquals(
                emptyList<String>(),
                whoAreInBoth(listOf("Marat", "Mikhail"), listOf("Sveta", "Kirill"))
        )
    }

    @Test
    @Tag("Normal")
    fun canBuildFrom() {
        assertFalse(canBuildFrom(emptyList(), "foo"))
        assertTrue(canBuildFrom(listOf('a', 'b', 'o'), "baobab"))
        assertFalse(canBuildFrom(listOf('a', 'm', 'r'), "Marat"))
    }

    @Test
    @Tag("Normal")
    fun extractRepeats() {
        assertEquals(
                emptyMap<String, Int>(),
                extractRepeats(emptyList())
        )
        assertEquals(
                mapOf("a" to 2),
                extractRepeats(listOf("a", "b", "a"))
        )
        assertEquals(
                emptyMap<String, Int>(),
                extractRepeats(listOf("a", "b", "c"))
        )
    }

    @Test
    @Tag("Normal")
    fun hasAnagrams() {
        assertFalse(hasAnagrams(emptyList()))
        assertTrue(hasAnagrams(listOf("рот", "свет", "тор")))
        assertFalse(hasAnagrams(listOf("рот", "свет", "код", "дверь")))
    }

    @Test
    @Tag("Hard")
    fun findSumOfTwo() {
        assertEquals(
                Pair(-1, -1),
                findSumOfTwo(emptyList(), 1)
        )
        assertEquals(
                Pair(0, 2),
                findSumOfTwo(listOf(1, 2, 3), 4)
        )
        assertEquals(
                Pair(-1, -1),
                findSumOfTwo(listOf(1, 2, 3), 6)
        )
    }

    @Test
    @Tag("Impossible")
    fun bagPacking() {
        assertEquals(
                setOf("Кубок"),
                bagPacking(
                        mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
                        850
                )
        )
        assertEquals(
                emptySet<String>(),
                bagPacking(
                        mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
                        450
                )
        )
        assertEquals(
                setOf("28", "27", "24", "22", "21", "18", "16", "14", "9", "7", "5", "4", "3", "2"),
                bagPacking(
                        mapOf("0" to (24 to 1), "1" to (148 to 13), "2" to (149 to 476),
                                "3" to (148 to 148), "4" to (148 to 450), "5" to (149 to 310),
                                "6" to (221 to 248), "7" to (148 to 460), "8" to (154 to 118),
                                "9" to (148 to 324), "10" to (32 to 2), "11" to (380 to 173),
                                "12" to (304 to 1), "13" to (237 to 442), "14" to (78 to 456),
                                "15" to (373 to 138), "16" to (2 to 16), "17" to (459 to 256),
                                "18" to (1 to 148), "19" to (338 to 148), "20" to (430 to 2),
                                "21" to (2 to 252), "22" to (1 to 355), "23" to (149 to 2),
                                "24" to (2 to 24), "25" to (148 to 129), "26" to (128 to 16),
                                "27" to (28 to 379), "28" to (148 to 383), "29" to (149 to 148)),
                        1160
                )
        )
        assertEquals(
                setOf("23", "22", "21", "20", "19", "18", "17", "16", "15", "14", "13", "12", "11", "10", "9", "8", "6", "5", "4", "2", "1", "0"),
                bagPacking(
                        mapOf("0" to (149 to 180), "1" to (148 to 301), "2" to (148 to 2),
                                "3" to (224 to 1), "4" to (1 to 148), "5" to (375 to 176),
                                "6" to (178 to 203), "7" to (479 to 2), "8" to (1 to 2),
                                "9" to (374 to 148), "10" to (149 to 418), "11" to (148 to 148),
                                "12" to (148 to 477), "13" to (361 to 149), "14" to (2 to 149),
                                "15" to (2 to 52), "16" to (2 to 148), "17" to (2 to 2),
                                "18" to (2 to 50), "19" to (2 to 148), "20" to (327 to 283),
                                "21" to (345 to 271), "22" to (227 to 148), "23" to (365 to 222)),
                        3648
                )
        )
        assertEquals(
                setOf("40", "35", "33", "29", "26", "24", "22", "19", "18", "14", "13", "11", "6", "2", "1"),
                bagPacking(
                        mapOf("0" to (50 to 70), "1" to (1 to 148), "2" to (149 to 368),
                                "3" to (159 to 2), "4" to (450 to 2), "5" to (1 to 1),
                                "6" to (30 to 148), "7" to (237 to 2), "8" to (149 to 1),
                                "9" to (148 to 124), "10" to (2 to 2), "11" to (1 to 446),
                                "12" to (297 to 278), "13" to (1 to 382), "14" to (2 to 352),
                                "15" to (1 to 1), "16" to (2 to 1), "17" to (307 to 490),
                                "18" to (271 to 462), "19" to (2 to 149), "20" to (317 to 149),
                                "21" to (149 to 149), "22" to (149 to 347), "23" to (2 to 2),
                                "24" to (1 to 2), "25" to (148 to 1), "26" to (2 to 148),
                                "27" to (404 to 149), "28" to (148 to 36), "29" to (2 to 198),
                                "30" to (149 to 148), "31" to (1 to 1), "32" to (385 to 148),
                                "33" to (149 to 240), "34" to (466 to 1), "35" to (1 to 119),
                                "36" to (149 to 148), "37" to (158 to 293), "38" to (148 to 2),
                                "39" to (148 to 149), "40" to (2 to 149), "41" to (360 to 405)),
                        763
                )
        )
        assertEquals(
                setOf("9", "8", "7", "6", "5", "4", "3", "2", "1", "0"),
                bagPacking(
                        mapOf("0" to (2 to 1), "1" to (2 to 149), "2" to (46 to 148),
                                "3" to (244 to 414), "4" to (1 to 1), "5" to (2 to 273),
                                "6" to (2 to 380), "7" to (75 to 148), "8" to (1 to 148),
                                "9" to (149 to 433)),
                        643
                )
        )
        assertEquals(
                setOf("15", "11", "10", "8", "5", "4", "3", "2", "0"),
                bagPacking(
                        mapOf("0" to (2 to 61), "1" to (253 to 148), "2" to (148 to 125),
                                "3" to (1 to 222), "4" to (1 to 148), "5" to (1 to 1),
                                "6" to (326 to 148), "7" to (213 to 149), "8" to (2 to 6),
                                "9" to (365 to 219), "10" to (52 to 181), "11" to (2 to 3),
                                "12" to (149 to 1), "13" to (141 to 1), "14" to (380 to 449),
                                "15" to (108 to 149)),
                        374
                )
        )
    }

    // TODO: map task tests
}
