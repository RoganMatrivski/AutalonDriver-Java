package com.nawadata.nfunittestlibrary.reactmui

import com.nawadata.nfunittestlibrary.Tools
import com.nawadata.nfunittestlibrary.WebDriverExtended
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

class RadioInput(
    driver: WebDriver,
    private val driverExt: WebDriverExtended,
    private val element: WebElement,
) : BasicInputClass(
    driver,
    driverExt,
    element,
) {

    private val options: Array<WebElement>
        get() = element.findElements(By.xpath(".//../descendant::*[contains(@class, 'MuiFormGroup-root')]/label")).toTypedArray()

    fun selectRandomElement(): RadioInput {
        val selectedOption =
            (Tools.getRandomElement(options))

        driverExt.highlightElement(selectedOption)
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
        val selectedOption = options[index]
        selectedOption.click()
        return this
    }

    fun selectElementFromText(text: String): RadioInput {
        val option = options.find { webElement ->
            webElement.findElements(By.xpath("descendant::*[text()='$text']")).isNotEmpty()
        }!!
        driverExt.scrollToElement(option)
        option.click()
        return this
    }
}
