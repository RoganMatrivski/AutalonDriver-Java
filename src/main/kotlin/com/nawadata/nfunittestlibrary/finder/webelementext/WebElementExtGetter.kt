package com.nawadata.nfunittestlibrary.finder.webelementext

import com.nawadata.nfunittestlibrary.Consts
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

    fun untilElementVisible(): WebElementExtended {
        return try {
            WebElementExtended(
                driver, driverExt, WebDriverWait(driver, Consts.defaultTimeout.seconds)
                    .until(ExpectedConditions.visibilityOfElementLocated(by))
            )
        } catch (ex: TimeoutException) {
            throw ex
        }
    }

    fun untilElementInvisible(): Boolean {
        return try {
            WebDriverWait(driver, Consts.defaultTimeout.seconds)
                .until(ExpectedConditions.invisibilityOfElementLocated(by))
        } catch (ex: TimeoutException) {
            throw ex
        }
    }

    fun untilElementInteractable(): WebElementExtended {
        return try {
            WebElementExtended(
                driver, driverExt, WebDriverWait(driver, Consts.defaultTimeout.seconds)
                    .until(ExpectedConditions.elementToBeClickable(by))
            )
        } catch (ex: TimeoutException) {
            throw ex
        }
    }
}