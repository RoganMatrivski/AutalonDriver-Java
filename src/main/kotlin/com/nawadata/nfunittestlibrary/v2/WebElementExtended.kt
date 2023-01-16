package com.nawadata.nfunittestlibrary.v2

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import java.time.Duration

class WebElementExtended(
    private val driver: WebDriver,
    private val webElement: WebElement?
) {
    fun getWebElement() = webElement!!

    fun highlightAndGetElement(): WebElement {
        driver.getJsExecutor().executeScript("arguments[0].style.outline='4px solid red'", webElement!!)
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
                driver.wait(Duration.ofMillis(200))
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
                driver.wait(Duration.ofMillis(200))
            }
        }

        if (retryCount == 0) throw lastException
    }
}
