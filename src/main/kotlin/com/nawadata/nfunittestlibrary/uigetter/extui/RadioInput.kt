package com.nawadata.nfunittestlibrary.uigetter.extui

import com.nawadata.nfunittestlibrary.Tools
import com.nawadata.nfunittestlibrary.scrollToElement
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

class RadioInput(
    val driver: WebDriver,
    private val element: WebElement,
) : BasicInputClass(
    driver,
    element,
    element.getAttribute("data-componentid")
) {
    private val componentid = element.getAttribute("data-componentid")

    private val options: List<WebElement>
        get() = element.findElements(By.xpath("descendant::td"))

    fun selectRandomElement(): RadioInput {
        val selectedOption =
            (Tools.getRandomElement<Any>(options.toTypedArray()) as WebElement)
                .findElement(By.xpath("descendant::input"))
        selectedOption.click()
        return this
    }

    fun selectElementOnIndex(index: Int): RadioInput {
        val options = options
        if (index >= options.size || index < 0) {
            throw IndexOutOfBoundsException(
                "This function tries to access index " + index + " from an array of "
                        + options.size + " elements."
            )
        }
        val selectedOption =
            options[index].findElement(By.xpath("descendant::input"))
        selectedOption.click()
        return this
    }

    fun selectElementFromText(text: String): RadioInput {
        println(
            "//div[@id='" + componentid + "-containerEl']/descendant::label[contains(text(), '"
                    + text + "')]/../span/input"
        )
        val options = element.findElements(
            By.xpath(
                "//div[@id='" + componentid
                        + "-containerEl']/descendant::label[contains(text(), '" + text + "')]/../span/input"
            )
        )
        require(options.isNotEmpty()) { "Element search returns empty." }
        val selected = options[0]
        driver.scrollToElement(selected)
        selected.click()
        return this
    }
}
