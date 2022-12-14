package com.nawadata.nfunittestlibrary.finder.webelementext

import com.nawadata.nfunittestlibrary.WebDriverExtended
import com.nawadata.nfunittestlibrary.WebElementExtended
import org.openqa.selenium.By
import org.openqa.selenium.TimeoutException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

class WebElementExtGetter(
    private val driver: WebDriver,
    private val driverExt: WebDriverExtended,
    private val by: By,
) {
    fun now(): WebElement = driver.findElement(by)

    @JvmOverloads
    fun untilElementVisible(customTimeout: Long = driverExt.timeout): WebElementExtended {
        return try {
            WebElementExtended(
                driver, driverExt, WebDriverWait(driver, customTimeout)
                    .until(ExpectedConditions.visibilityOfElementLocated(by))
            )
        } catch (ex: TimeoutException) {
            throw ex
        }
    }

    @JvmOverloads
    fun untilElementInvisible(customTimeout: Long = driverExt.timeout): Boolean {
        return try {
            WebDriverWait(driver, customTimeout)
                .until(ExpectedConditions.invisibilityOfElementLocated(by))
        } catch (ex: TimeoutException) {
            throw ex
        }
    }

    @JvmOverloads
    fun untilElementInteractable(customTimeout: Long = driverExt.timeout): WebElementExtended {
        return try {
            WebElementExtended(
                driver, driverExt, WebDriverWait(driver, customTimeout)
                    .until(ExpectedConditions.elementToBeClickable(by))
            )
        } catch (ex: TimeoutException) {
            throw ex
        }
    }

    fun clickAwait() = this.untilElementInteractable().click()
    fun sendKeysAwait(str: String) = this.untilElementInteractable().sendKeys(str)
}