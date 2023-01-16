package com.nawadata.nfunittestlibrary.v2

import org.openqa.selenium.By
import org.openqa.selenium.StaleElementReferenceException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

class RefreshableWebElement(val driver: WebDriver, val by: By, private val prevElement: RefreshableWebElement? = null) {
    val element: WebElement = driver.findElement(by)

    fun refreshElement() {
        prevElement?.let {
            prevElement.refreshElement()
        }

        var retryCounter = 5;
        while (retryCounter > 0 && driver.isElementStale(element)) {
            driver.findElement(by)
            driver.waitSeconds(1)
            retryCounter -= 1
        }

        if (retryCounter <= 0) {
            throw StaleElementReferenceException("Element not found after $retryCounter tries")
        }
    }

//    fun findElement(by: By): RefreshableWebElement{
//        // If there's no prevElement, use driver as root
//        val root = if (prevElement != null) {
//            prevElement.element
//        } else {
//            driver
//        }
//
//    }

    fun getRefreshedElement(): WebElement {
        this.refreshElement()
        return element
    }
}