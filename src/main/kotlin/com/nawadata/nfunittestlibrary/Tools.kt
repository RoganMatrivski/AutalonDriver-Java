package com.nawadata.nfunittestlibrary

import org.openqa.selenium.By
import java.nio.CharBuffer
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import java.util.stream.Stream

@Suppress("unused")
object Tools {
    private val rand = kotlin.random.Random
    @JvmStatic fun getVersion(): String = Tools.javaClass.`package`.implementationVersion

    @JvmStatic fun xpathToLower(attribute: String) =
        "translate($attribute, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')"

    @JvmStatic fun xpathInexactContains(a: String, b: String) =
        "contains(${xpathToLower(a)}, '${b.lowercase(Locale.getDefault())}')"

    @JvmStatic @JvmOverloads
    fun getElementContainingStringExact(text: String, by: Enums.ByOption, tag: String = "*", index: Int = 1): By =
        By.xpath(
            "//$tag[${by.strName} = '$text'][$index]"
        )

    @JvmStatic @JvmOverloads
    fun getElementContainingString(text: String, by: Enums.ByOption, tag: String = "*", index: Int = 1): By =
        By.xpath(
            "//$tag[${xpathInexactContains(by.strName, text)}][$index]"
        )

    @JvmStatic fun toUInt32(bytes: ByteArray, offset: Int): Long {
        var result = java.lang.Byte.toUnsignedLong(bytes[offset + 3])
        result = result or (java.lang.Byte.toUnsignedLong(bytes[offset + 2]) shl 8)
        result = result or (java.lang.Byte.toUnsignedLong(bytes[offset + 1]) shl 16)
        result = result or (java.lang.Byte.toUnsignedLong(bytes[offset]) shl 24)
        return result
    }

    private fun randstr(size: Int, charset: List<Char>) =
        List(size) {charset.random()}.joinToString("")

    @JvmStatic fun fastRandStr(size: Int) =
        randstr(size, (Consts.lowerAlphabets + Consts.upperAlphabets + Consts.numbers).toList())

    @JvmStatic fun getCharStream(arr: CharArray): Stream<Char> =
        CharBuffer.wrap(arr).chars().mapToObj { ch: Int -> ch.toChar() }

    @JvmStatic @JvmOverloads
    fun randomString(
        size: Int = 24,
        randStrOpt: Enums.RandomStringOption = Enums.RandomStringOption.Alphabetic,
        caseOpt: Enums.CaseOption = Enums.CaseOption.Mixed
    ): String {
        val optToggle: BooleanArray = when (randStrOpt) {
            Enums.RandomStringOption.Alphabetic -> booleanArrayOf(true, false, false)
            Enums.RandomStringOption.Numeric -> booleanArrayOf(false, true, false)
            Enums.RandomStringOption.Symbols -> booleanArrayOf(false, false, true)
            Enums.RandomStringOption.Alphanumeric -> booleanArrayOf(true, true, false)
            Enums.RandomStringOption.AlphanumericWithSymbols -> booleanArrayOf(true, true, true)
        }

        // TODO: Discard append with direct initialize string?
        val alphabetsBuilder = StringBuilder()
        when (caseOpt) {
            Enums.CaseOption.LowerOnly -> alphabetsBuilder.append(Consts.lowerAlphabets)
            Enums.CaseOption.UpperOnly -> alphabetsBuilder.append(Consts.upperAlphabets)
            Enums.CaseOption.Mixed -> {
                alphabetsBuilder.append(Consts.lowerAlphabets)
                alphabetsBuilder.append(Consts.upperAlphabets)
            }
        }

        val alphabetsCharset = alphabetsBuilder.toString().toCharArray()
        val strBuilder = StringBuilder()
        if (optToggle[0]) {
            strBuilder.append(alphabetsCharset)
        }
        if (optToggle[1]) {
            strBuilder.append(Consts.numbers)
        }
        if (optToggle[2]) {
            strBuilder.append(Consts.symbols)
        }

        val charset = strBuilder.toList()
        var res: String = randstr(size, charset)
        while (getCharStream(res.toCharArray()).noneMatch { ch: Char ->
                String(alphabetsCharset).contains(ch.toString()) && optToggle[0]
                        || String(Consts.numbers).contains(ch.toString()) && optToggle[1]
                        || String(Consts.symbols).contains(ch.toString()) && optToggle[2]
            }
        ) {
            res = randstr(size, charset)
        }
        return res
    }

    @JvmStatic fun getRandomEmail() =
        "${randomString()}@${randomString()}.com"

    @JvmStatic fun randomDate(dateAfter: LocalDate, dateBefore: LocalDate): LocalDate {
        val randomTime = ThreadLocalRandom.current().nextLong(dateAfter.toEpochDay(), dateBefore.toEpochDay())
        return LocalDate.ofEpochDay(randomTime)
    }

    @JvmStatic private fun dateToLocalDate(date: Date): LocalDate {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    }

    @JvmStatic fun randomDateString(dateAfter: LocalDate, dateBefore: LocalDate, pattern: String): String {
        val randomTime = ThreadLocalRandom.current().nextLong(dateAfter.toEpochDay(), dateBefore.toEpochDay())
        return DateTimeFormatter.ofPattern(pattern).format(LocalDate.ofEpochDay(randomTime))
    }

    @JvmStatic @JvmOverloads
    fun randomDateString(dateAfter: String, dateBefore: String, pattern: String = "dd-MM-yyyy"): String {
        val dateParser = SimpleDateFormat(pattern)
        var res = ""
        try {
            res = randomDateString(
                dateToLocalDate(dateParser.parse(dateAfter)),
                dateToLocalDate(dateParser.parse(dateBefore)),
                pattern
            )
        } catch (ex: ParseException) {
            // Throw new Exception("Date invalid!");
            println("Exception while handling invalid date")
        }
        return res
    }

    @JvmStatic fun randomNumber(min: Int, max: Int) = rand.nextInt(max - min) + min

    @JvmStatic fun randomNumberString(min: Int, max: Int): String =
        (rand.nextInt(max - min) + min).toString(10)

    @JvmStatic fun <T> getRandomElement(arr: Array<T>): T =
        arr[rand.nextInt(arr.size)]

    @JvmStatic fun <T> coalesce(vararg params: T): T? {
        for (param in params) {
            if (param != null) {
                return param
            }
        }
        return null
    }

    @Deprecated("Function was ambiguous with both name and function", replaceWith = ReplaceWith("getElementContainingString()"), level = DeprecationLevel.ERROR)
    @JvmStatic fun getElementByText(text: String, tag: String = "*"): By =
        By.xpath("//$tag[contains(text(), '$text')]");
}