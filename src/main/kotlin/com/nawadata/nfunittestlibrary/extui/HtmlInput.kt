package com.nawadata.nfunittestlibrary.extui

import com.nawadata.nfunittestlibrary.WebDriverExtended
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

class HtmlInput(
    private val driver: WebDriver,
    private val driverExt: WebDriverExtended,
    private val element: WebElement,
) : BasicInputClass(
    driver,
    driverExt,
    element,
    "",
) {
    fun clearText(): HtmlInput {
        val element = element
        element.clear()
        return this
    }
    @JvmOverloads
    fun sendText(text: String, ignoreErrors: Boolean? = false): HtmlInput {
        val element = element
        val maxLengthStr = element.getAttribute("maxlength")
        if (maxLengthStr != null) {
            val maxLength = maxLengthStr.toInt()
            require(!(text.length > maxLength && !ignoreErrors!!)) {
                """
                    The text length on the argument is bigger than the input maxlength.
                    If this is an expected behavior, please add a boolean on the ignoreErrors argument
                    """.trimIndent()
            }
        }
        driverExt.jsExecutor.executeScript(
            "arguments[0].innerHTML = arguments[1];",
            element,
            text
        )
        driver.switchTo().parentFrame()
        return this
    }
}
