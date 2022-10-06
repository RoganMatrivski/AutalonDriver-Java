package com.nawadata.nfunittestlibrary

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

class WebElementExtended(
    private val driver: WebDriver,
    private val driverExt: WebDriverExtended = WebDriverExtended(driver),
    private val webElement: WebElement?
) {
    fun getWebElement() = webElement!!

    fun highlightAndGetElement(): WebElement {
        driverExt.jsExecutor.executeScript("arguments[0].style.border='3px solid red'", webElement!!)
        return webElement
    }

    fun click() {
        var retryCount = 5
        var lastException: Exception = Exception("Dummy exception")

        while (retryCount > 0) {
            try {
                highlightAndGetElement().click()
                break
            } catch (e: Exception) {
                lastException = e
                retryCount--
            }
        }

        if (retryCount == 0) throw lastException
    }
    fun sendKeys(str: String) {
        var retryCount = 5
        var lastException: Exception = Exception("Dummy exception")

        while (retryCount > 0) {
            try {
                highlightAndGetElement().sendKeys(str)
                break
            } catch (e: Exception) {
                lastException = e
                retryCount--
            }
        }

        if (retryCount == 0) throw lastException
    }
}
