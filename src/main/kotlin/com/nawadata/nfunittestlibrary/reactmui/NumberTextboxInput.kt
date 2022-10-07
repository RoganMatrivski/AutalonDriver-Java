package com.nawadata.nfunittestlibrary.reactmui

import com.nawadata.nfunittestlibrary.WebDriverExtended
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

/**
 * The type Number textbox input class.
 */
class NumberTextboxInput(
    driver: WebDriver,
    driverExt: WebDriverExtended,
    private val element: WebElement,
) : BasicInputClass(
    driver,
    driverExt,
    element,
    element.getAttribute("data-componentid")
) {
    fun <T> sendText(textObj: T): NumberTextboxInput =
        sendText(textObj.toString())

    fun sendText(text: String): NumberTextboxInput {
        val element = element
        val intText: Int = try {
            text.toInt()
        } catch (e: NumberFormatException) {
            throw IllegalArgumentException(
                "Can't read text argument as number. Please recheck your input",
                e
            )
        }
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
