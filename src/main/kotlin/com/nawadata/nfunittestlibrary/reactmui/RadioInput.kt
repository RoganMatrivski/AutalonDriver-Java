package com.nawadata.nfunittestlibrary.reactmui

import com.nawadata.nfunittestlibrary.Tools
import com.nawadata.nfunittestlibrary.WebDriverExtended
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

class RadioInput(
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
        driverExt.scrollToElement(selected)
        selected.click()
        return this
    }
}
