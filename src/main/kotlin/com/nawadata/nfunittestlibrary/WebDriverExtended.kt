package com.nawadata.nfunittestlibrary

import com.nawadata.nfunittestlibrary.finder.webelement.WebElementFrom
import com.nawadata.nfunittestlibrary.finder.webelementext.WebElementExtFrom
import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration
import java.time.LocalDateTime
import java.util.function.Function

class   WebDriverExtended (
    private val driver: WebDriver
        ) {
    private var jsExec: JavascriptExecutor? = null

    fun wait(delay: Duration) {
        val now = LocalDateTime.now()
        val wait = WebDriverWait( driver, 60)
        wait.pollingEvery(Duration.ofMillis(100))
        wait.until {
            Duration.between(now, LocalDateTime.now()).minus(delay) > Duration.ZERO
        }
    }

    fun waitSeconds(delay: Long) =
        wait(Duration.ofSeconds(delay))

    @JvmOverloads
    fun waitForFunc(
        func: Function<WebDriver, WebElement>,
        timeout: Long = Consts.defaultTimeout.seconds
    ): WebElement = WebDriverWait(driver, timeout).until(func)

    private fun handleElementTimeoutException(ex: TimeoutException, by: By) {
        val element = driver.findElement(by) ?: throw NoSuchElementException(null)
        if (!element.isDisplayed || !element.isEnabled) {
            throw ElementNotInteractableException(null) // Invisible element from now on returns this
        }
        throw ex
    }

    fun waitUntilVisible(by: By): WebElement? {
        return try {
            WebDriverWait(driver, Consts.defaultTimeout.seconds)
                .until(ExpectedConditions.visibilityOfElementLocated(by))
        } catch (ex: TimeoutException) {
            handleElementTimeoutException(ex, by)
            null
        }
    }

    fun waitUntilInvisible(by: By): Boolean? {
        return try {
            WebDriverWait(driver, Consts.defaultTimeout.seconds)
                .until(ExpectedConditions.invisibilityOfElementLocated(by))
        } catch (ex: TimeoutException) {
            handleElementTimeoutException(ex, by)
            null
        } catch (e: Exception) {
            throw e
        }
    }

    val jsExecutor: JavascriptExecutor
        get() {
            if (jsExec == null) {
                jsExec = if (driver is JavascriptExecutor) {
                    driver
                } else {
                    throw IllegalStateException("This driver does not support JavaScript!")
                }
            }
            return jsExec as JavascriptExecutor
        }

    fun waitUntilFrameLoads(by: By): WebDriver =
        WebDriverWait(driver, Consts.defaultTimeout.seconds)
            .until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(by))

    fun scrollToElement(element: WebElement) =
        Actions(driver).moveToElement(element).perform()

    fun getElement() = WebElementFrom(driver)

    fun getElementExtended() = WebElementExtFrom(driver, this)

    fun isElementStale(element: WebElement): Boolean {
        return try {
            element.isEnabled
            false
        } catch (exception: StaleElementReferenceException) {
            true
        }
    }

    fun setWindowDimension(width: Int, height: Int) {
        driver.manage().window().size = org.openqa.selenium.Dimension(width, height)
    }
}