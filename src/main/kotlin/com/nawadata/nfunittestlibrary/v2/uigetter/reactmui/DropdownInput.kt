package com.nawadata.nfunittestlibrary.v2.uigetter.reactmui

import com.nawadata.nfunittestlibrary.Tools
import com.nawadata.nfunittestlibrary.v2.*

import org.openqa.selenium.By
import org.openqa.selenium.StaleElementReferenceException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

class DropdownInput (
    private val driver: WebDriver,
    private val element: WebElement,
    private val componentId: String = element.getAttribute("for"),
    ) : BasicInputClass(
        driver,
    element,
    element.getAttribute("for")
) {
    private val options: Array<WebElement>
        get() = driver.findElements(By.xpath("//ul[@id = '$componentId-popup']/li")).toTypedArray()

    private fun pickOption(index: Int): WebElement {
        val options = options
        if (index >= options.size) {
            throw IndexOutOfBoundsException(
                "This function tries to access index " + index
                        + " from an array of " + options.size + " elements."
            )
        }
        val selectedOption: WebElement = if (index < 0) {
            Tools.getRandomElement(options)
        } else {
            options[index]
        }
        driver.scrollToElement(selectedOption)
        return selectedOption
    }

    fun selectRandomElement(): DropdownInput {
        element.findElement(By.xpath(
            "following::*[" +
                    Tools.xpathInexactContains("@class", "endAdornment") +
                    "]/button[@title = 'Open']"))
            .click()
        var tries = 5

        while (tries-- > 0) {
            try {
                driver.waitUntilVisible(By.id("$componentId-popup"))
                val selectedOption = pickOption(-1)
                selectedOption.click()
                break
            } catch (_: StaleElementReferenceException) {
            }
        }
        return this
    }

    @Suppress("unused")
    fun selectElementOnIndex(index: Int): DropdownInput {
        element.findElement(By.xpath(
            "following::*[" +
                    Tools.xpathInexactContains("@class", "endAdornment") +
                    "]/button[@title = 'Open']"))
            .click()

        var tries = 5

        while (tries-- > 0) {
            try {
                driver.waitUntilVisible(By.id("$componentId-popup"))
                val selectedOption = pickOption(index)
                driver.scrollToElement(selectedOption)
                selectedOption.click()
                break
            } catch (_: StaleElementReferenceException) {
            }
        }
        return this
    }

    @Suppress("unused")
    fun clearInput(): DropdownInput {
        try {
            element.findElement(By.xpath(
                "following::*[" +
                        Tools.xpathInexactContains("@class", "endAdornment") +
                        "]/button[@title = 'Clear']"))
                .click()
        } catch (e: Exception) {
            //TODO: handle exception
        }
        return this
    }

    // TODO: Add Inexact variant
    fun selectElementFromText(text: String): DropdownInput {
        element.findElement(By.xpath(
            "following::*[" +
                    Tools.xpathInexactContains("@class", "endAdornment") +
                    "]/button[@title = 'Open']"))
            .click()

        var tries = 5

        while (tries-- > 0) {
            try {
                driver.waitUntilVisible(By.id("$componentId-popup"))
                val options = element.findElements(
                    By.xpath(
                        "//ul[@id = '$componentId-popup']/li[contains(text(), '$text')]"
                    )
                )
                require(options.isNotEmpty()) { "Element search returns empty." }
                val selected = options[0]

                println(selected.getAttribute("id"))
                driver.scrollToElement(selected)
                selected.click()

                break
            } catch (_: StaleElementReferenceException) {
            }
        }
        return this
    }
}