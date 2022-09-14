package com.nawadata.nfunittestlibrary.reactmui

import com.nawadata.nfunittestlibrary.WebDriverExtended
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

/**
 * The type Textbox input class.
 */
class TextboxInput(
    val driver: WebDriver,
    val driverExt: WebDriverExtended,
    val element: WebElement
) : BasicInputClass(
    driver,
    driverExt,
    element,
) {
    fun clearText(): TextboxInput {
        element.clear()
        return this
    }

    @JvmOverloads
    fun <T> sendText(textObj: T, ignoreErrors: Boolean = false): TextboxInput =
        sendText(textObj.toString(), ignoreErrors)

    @JvmOverloads
    fun sendText(
        text: String,
        ignoreErrors: Boolean = false
    ): TextboxInput {
        element.sendKeys(text)
        return this
    }
}
