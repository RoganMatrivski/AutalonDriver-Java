package com.nawadata.nfunittestlibrary.extui

import com.nawadata.nfunittestlibrary.Tools
import com.nawadata.nfunittestlibrary.WebDriverExtended
import org.openqa.selenium.By
import org.openqa.selenium.StaleElementReferenceException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

class DropdownInput (
    private val driver: WebDriver,
    private val driverExt: WebDriverExtended,
    private val element: WebElement,
    private val componentId: String = element.getAttribute("data-componentid"),
) : BasicInputClass(
    driver,
    driverExt,
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
        driverExt.scrollToElement(selectedOption)
        return selectedOption
    }

    fun selectRandomElement(): DropdownInput {
        driver.findElement(By.id("$componentId-trigger-picker")).click()
        var tries = 5

        while (tries-- > 0) {
            try {
                driverExt.waitUntilVisible(By.id("$componentId-picker-listEl"))
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
                driverExt.waitUntilVisible(By.id("$componentId-picker-listEl"))
                val selectedOption = pickOption(index)
                driverExt.scrollToElement(selectedOption)
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
    fun selectElementFromText(text: String): DropdownInput {
        driver.findElement(By.id("$componentId-trigger-picker")).click()

        var tries = 5

        while (tries-- > 0) {
            try {
                driverExt.waitUntilVisible(By.id("$componentId-picker-listEl"))
                val options = element.findElements(
                    By.xpath(
                        "//ul[@id = '$componentId-picker-listEl']/li[contains(text(), '$text')]"
                    )
                )
                require(options.isNotEmpty()) { "Element search returns empty." }
                val selected = options[0]

                println(selected.getAttribute("id"))
                driverExt.scrollToElement(selected)
                selected.click()

                break
            } catch (_: StaleElementReferenceException) {
            }
        }
        return this
    }
}