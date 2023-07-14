package com.nawadata.nfunittestlibrary.finder

import com.nawadata.nfunittestlibrary.Consts
import com.nawadata.nfunittestlibrary.DefaultConfigs
import com.nawadata.nfunittestlibrary.WebElementExtended

import org.openqa.selenium.By
import org.openqa.selenium.TimeoutException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

class Getter (
    private val driver: WebDriver,
    private val by: By,
    private val configs: DefaultConfigs
) {
    fun now(): WebElement = driver.findElement(by)

    @JvmOverloads
    fun untilElementVisible(customTimeout: Long = configs.defaultTimeout.seconds): WebElementExtended {
        return try {
            WebElementExtended(
                driver, WebDriverWait(driver, customTimeout)
                    .until(ExpectedConditions.visibilityOfElementLocated(by))
            )
        } catch (ex: TimeoutException) {
            throw ex
        }
    }

    @JvmOverloads
    fun untilElementInvisible(customTimeout: Long = configs.defaultTimeout.seconds): Boolean {
        return try {
            WebDriverWait(driver, customTimeout)
                .until(ExpectedConditions.invisibilityOfElementLocated(by))
        } catch (ex: TimeoutException) {
            throw ex
        }
    }

    @JvmOverloads
    fun untilElementInteractable(customTimeout: Long = configs.defaultTimeout.seconds): WebElementExtended {
        return try {
            WebElementExtended(
                driver, WebDriverWait(driver, customTimeout)
                    .until(ExpectedConditions.elementToBeClickable(by))
            )
        } catch (ex: TimeoutException) {
            throw ex
        }
    }

    fun clickAwait() = this.untilElementInteractable().click()
    fun sendKeysAwait(str: String) = this.untilElementInteractable().sendKeys(str)
}