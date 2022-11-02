package com.nawadata.nfunittestlibrary.reactmui

import com.nawadata.nfunittestlibrary.WebDriverExtended
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

/**
 * The type Textbox input class.
 */
class CheckboxInput(
    val driver: WebDriver,
    val driverExt: WebDriverExtended,
    val element: WebElement
) : BasicInputClass(
    driver,
    driverExt,
    element,
) {
    fun getCheckboxState(): Boolean {
        val checkboxElement = element.findElement(By.xpath(
            "ancestor::label[@class = 'MuiFormControlLabel-root']/descendant::span[contains(@class, 'MuiCheckbox')]"
        ))

        return checkboxElement.getAttribute("class").contains("Mui-checked", ignoreCase = true)
    }

    fun toggle(): CheckboxInput {
        element.click()
        return this
    }

    fun check(): CheckboxInput {
        val checkboxState = getCheckboxState()

        if (!checkboxState) toggle()
        return this
    }

    fun uncheck(): CheckboxInput {
        val checkboxState = getCheckboxState()

        if (checkboxState) toggle()
        return this
    }
}
