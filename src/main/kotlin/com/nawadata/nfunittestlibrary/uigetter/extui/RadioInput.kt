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
    element
) {
//    private val componentid = element.findElement(By.xpath("descendant::input[@data-componentid]")).getAttribute("data-componentid")

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

    @JvmOverloads
    fun selectElementFromText(text: String, exact : Boolean = false): RadioInput {
        val inputXPath = "descendant::label[${
            if (exact) {"text() = '$text'"} else {"contains(text(), '$text')"}
        }]/../span/input"

        println(inputXPath)
        val options = element.findElements(By.xpath(inputXPath))
        require(options.isNotEmpty()) { "Element search returns empty." }
        val selected = options[0]
        driver.scrollToElement(selected)
        selected.click()
        return this
    }
}
