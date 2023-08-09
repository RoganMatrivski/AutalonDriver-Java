package com.nawadata.nfunittestlibrary.uigetter.extui

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

/**
 * The type Number textbox input class.
 */
class NumberTextboxInput(
    driver: WebDriver,
    private val element: WebElement,
) : BasicInputClass(
    driver,
    element,
    element.getAttribute("data-componentid")
) {
    @JvmOverloads
    fun <T> sendText(textObj: T, ignoreErrors: Boolean = false): NumberTextboxInput =
        sendText(textObj.toString(), ignoreErrors)

    fun clearText(): NumberTextboxInput {
        val element = element
        element.clear()
        return this
    }

    @JvmOverloads
    fun sendText(text: String, ignoreErrors: Boolean = false): NumberTextboxInput {
        val element = element
        val numText: Double = try {
            text.toDouble()
        } catch (e: NumberFormatException) {
            throw IllegalArgumentException(
                "Can't read text argument as number. Please recheck your input",
                e
            )
        }
        val intText: Int = numText.toInt()
        try {
            val valueMin = element.getAttribute("aria-valuemin").toInt()
            require(intText >= valueMin) {
                ("The text length on the argument is smaller than the input minimum. "
                        + "If this is an expected behavior, please add a boolean on the ignoreErrors argument")
            }
        } catch (_: Exception) {
        }
        try {
            val valueMax = element.getAttribute("aria-valuemax").toInt()
            require(intText >= valueMax) {
                ("The text length on the argument is smaller than the input maximum. "
                        + "If this is an expected behavior, please add a boolean on the ignoreErrors argument")
            }
        } catch (_: Exception) {
        }
        element.sendKeys(text)
        return this
    }
}
