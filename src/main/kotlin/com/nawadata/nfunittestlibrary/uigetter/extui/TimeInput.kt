package com.nawadata.nfunittestlibrary.uigetter.extui

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

/**
 * The type Textbox input class.
 */
class TimeInput(
    val driver: WebDriver,
    val element: WebElement
) : BasicInputClass(
    driver,
    element,
    element.getAttribute("data-componentid")
) {
    private val componentid = element.getAttribute("data-componentid")

    fun clearText(): TimeInput {
        val element = element
        element.clear()
        return this
    }

    @JvmOverloads
    fun <T> sendText(textObj: T, ignoreErrors: Boolean = false): TimeInput =
        sendText(textObj.toString(), ignoreErrors)

    @JvmOverloads
    fun sendText(
        text: String,
        ignoreErrors: Boolean = false
    ): TimeInput {
        val element = element
        val maxLengthStr = element.getAttribute("maxlength")
        if (maxLengthStr != null) {
            val maxLength = maxLengthStr.toInt()
            require(!(text.length > maxLength && !ignoreErrors)) {
                """
                    The text length on the argument is bigger than the input maxlength.
                    If this is an expected behavior, please add a boolean on the ignoreErrors argument
                    """.trimIndent()
            }
        }
        element.sendKeys(text)
        return this
    }
}
