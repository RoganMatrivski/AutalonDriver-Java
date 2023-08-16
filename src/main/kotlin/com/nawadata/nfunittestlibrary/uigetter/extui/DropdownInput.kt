package com.nawadata.nfunittestlibrary.uigetter.extui

import com.nawadata.nfunittestlibrary.*
import org.openqa.selenium.By
import org.openqa.selenium.StaleElementReferenceException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions

class DropdownInput (
    private val driver: WebDriver,
    private val element: WebElement,
    private val componentId: String = element.getAttribute("data-componentid"),
) : BasicInputClass(
    driver,
    element,
    element.getAttribute("data-componentid")
) {
    private val options: List<WebElement>
        get() = driver.findElements(By.xpath("//ul[@id = '$componentId-picker-listEl']/li"))

    private fun pickOption(index: Int): WebElement {
        val options = options
        if (index >= options.size) {
            throw IndexOutOfBoundsException(
                "This function tries to access index " + index
                        + " from an array of " + options.size + " elements."
            )
        }
        val selectedOption: WebElement = if (index < 0) {
            Tools.getRandomElement<Any>(options.toTypedArray()) as WebElement
        } else {
            options[index]
        }
        driver.scrollToElement(selectedOption)
        return selectedOption
    }

    fun selectRandomElement(): DropdownInput {
        driver.findElement(By.id("$componentId-trigger-picker")).click()
        var tries = 5

        while (tries-- > 0) {
            try {
                driver.waitUntilVisible(By.id("$componentId-picker-listEl"))
                val selectedOption = pickOption(-1)
                selectedOption.click()
                break
            } catch (_: StaleElementReferenceException) {
            }
        }
        return this
    }

    fun selectElementOnIndex(index: Int): DropdownInput {
        driver.findElement(By.id("$componentId-trigger-picker")).click()

        var tries = 5

        while (tries-- > 0) {
            try {
                driver.waitUntilVisible(By.id("$componentId-picker-listEl"))
                val selectedOption = pickOption(index)
                driver.scrollToElement(selectedOption)
                selectedOption.click()
                break
            } catch (_: StaleElementReferenceException) {
            }
        }
        return this
    }

    fun clearInput(): DropdownInput {
        try {
            driver.findElement(By.id("$componentId-trigger-_trigger1")).click()
        } catch (e: Exception) {
            //TODO: handle exception
        }
        return this
    }

    // TODO: Add Exact variant
    @JvmOverloads
    fun selectElementFromText(text: String, fillTextboxFirst: Boolean = true): DropdownInput {
        @Suppress("NAME_SHADOWING") val text = text.trim()

        driver.scrollToElementJS(element, ScrollAlignment.Start)

        if (fillTextboxFirst) {
            element.click()
            element.sendKeys(text)
        } else {
            val pickerElExt = driver.getElement().byXPath("//*[@id = '$componentId-trigger-picker']").untilElementExist().click()
        }

        var tries = 5

        while (tries-- > 0) {
            try {
                println("Finding $componentId-picker-listEl")
                driver.waitUntilVisible(By.id("$componentId-picker-listEl"))

                val optionXPath = if (fillTextboxFirst) {
                    "//ul[@id = '$componentId-picker-listEl']/li[contains(text(), '$text')]"
                } else {
                    "//ul[@id = '$componentId-picker-listEl']/li[text() = '$text']"
                }

                println("Searching $optionXPath")
                val selected = driver.getElement().byXPath(optionXPath).untilElementExist().highlightAndGetElement()

                println(selected.getAttribute("id"))
                driver.scrollToElement(selected)
                selected.click()

                break
            } catch (_: Exception) {
            }
        }
        return this
    }
}