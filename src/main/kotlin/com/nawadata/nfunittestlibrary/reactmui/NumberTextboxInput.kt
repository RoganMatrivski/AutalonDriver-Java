package com.nawadata.nfunittestlibrary.reactmui

import com.nawadata.nfunittestlibrary.WebDriverExtended
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

/**
 * The type Number textbox input class.
 */
class NumberTextboxInput(
    private val driver: WebDriver,
    private val driverExt: WebDriverExtended,
    private val element: WebElement,
    private val componentId: String = element.getAttribute("data-componentid"),
) : BasicInputClass(
    driver,
    driverExt,
    element,
    element.getAttribute("data-componentid")
) {
    @JvmOverloads
    fun <T> sendText(textObj: T, ignoreErrors: Boolean = false): NumberTextboxInput =
        sendText(textObj.toString(), ignoreErrors)

    @JvmOverloads
    fun sendText(text: String, ignoreErrors: Boolean = false): NumberTextboxInput {
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
