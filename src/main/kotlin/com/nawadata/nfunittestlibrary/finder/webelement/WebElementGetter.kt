package com.nawadata.nfunittestlibrary.finder.webelement

import com.nawadata.nfunittestlibrary.Consts
import org.openqa.selenium.By
import org.openqa.selenium.TimeoutException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

class WebElementGetter(
    val driver: WebDriver,
    val by: By,
) {
    fun now(): WebElement = driver.findElement(by)

    fun untilElementVisible(): WebElement {
        return try {
            WebDriverWait(driver, Consts.defaultTimeout.seconds)
                .until(ExpectedConditions.visibilityOfElementLocated(by))
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

    fun untilElementInteractable(): WebElement {
        return try {
            WebDriverWait(driver, Consts.defaultTimeout.seconds)
                .until(ExpectedConditions.elementToBeClickable(by))
        } catch (ex: TimeoutException) {
            throw ex
        }
    }
}