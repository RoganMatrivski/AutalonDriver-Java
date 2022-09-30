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
import kotlin.math.abs

@Suppress("unused")
object Tools {
    private val rand = kotlin.random.Random
    @JvmStatic fun getVersion(): String = Tools.javaClass.`package`.implementationVersion

    @JvmStatic fun xpathToLower(attribute: String) = String.format(
        "translate(%s, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')", attribute
    )

    @JvmStatic fun xpathInexactContains(a: String, b: String) =
        String.format("contains(%s, '%s')", xpathToLower(a), b.lowercase(Locale.getDefault()))

    @JvmStatic @JvmOverloads
    fun getElementContainingStringExact(text: String, by: Enums.ByOption, tag: String = "*"): By =
        By.xpath(String.format("//%s[%s = '%s']", tag, by.strName, text))

    @JvmStatic @JvmOverloads
    fun getElementContainingString(text: String, by: Enums.ByOption, tag: String = "*"): By =
        By.xpath(String.format("//%s[%s]", tag, xpathInexactContains(by.strName, text)))

    @JvmStatic fun toUInt32(bytes: ByteArray, offset: Int): Long {
        var result = java.lang.Byte.toUnsignedLong(bytes[offset + 3])
        result = result or (java.lang.Byte.toUnsignedLong(bytes[offset + 2]) shl 8)
        result = result or (java.lang.Byte.toUnsignedLong(bytes[offset + 1]) shl 16)
        result = result or (java.lang.Byte.toUnsignedLong(bytes[offset]) shl 24)
        return result
    }

    @JvmStatic private fun randstr(size: Int, charset: CharArray): String {
        val data = ByteArray(4 * size)
        rand.nextBytes(data)
        val result = StringBuilder(size)
        for (i in 0 until size) {
            // Java have no concept of unsigned.
            // I don't know how this will behave here, so I add abs() for a good measure.
            val rnd: Long = abs(toUInt32(data, i * 4))
            val idx = (rnd % charset.size).toInt()
            result.append(charset[idx])
        }
        return result.toString()
    }

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

        val charset = strBuilder.toString().toCharArray()
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
        String.format("%s@%s.com", randomString(), randomString())

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

    @JvmStatic fun randomDateString(dateAfter: String, dateBefore: String, pattern: String): String {
        val dateParser = SimpleDateFormat("dd-MM-yyyy")
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
        By.xpath(String.format("//%s[contains(text(), '%s')]", tag, text))
}