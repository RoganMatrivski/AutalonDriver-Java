package com.nawadata.nfunittestlibrary.uigetter.reactmui


import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

/**
 * The type Textbox input class.
 */
class TextboxInput(
    val driver: WebDriver,
    val element: WebElement
) : BasicInputClass(
    driver,
    element,
) {
    fun clearText(): TextboxInput {
        element.sendKeys(Keys.CONTROL, "a")
        element.sendKeys(Keys.BACK_SPACE)
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
