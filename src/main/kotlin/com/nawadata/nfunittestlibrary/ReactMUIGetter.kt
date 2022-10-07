package com.nawadata.nfunittestlibrary

import com.nawadata.nfunittestlibrary.finder.webelementext.WebElementExtGetter
import com.nawadata.nfunittestlibrary.reactmui.*
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

class ReactMUIGetter(
    private val driver: WebDriver,
    private val driverExt: WebDriverExtended = WebDriverExtended(driver),
    private val webElement: WebElement?
) {
    constructor(driver: WebDriver) : this(driver, WebDriverExtended(driver), null)
    constructor(driver: WebDriver, driverExt: WebDriverExtended) : this(driver, driverExt, null)

    fun shouldBe() = ShouldBe(driver, driverExt, webElement!!) // If there's no webElement, just throw exception

    private fun getInputFromLabel(label: String): WebElementExtended {
        val xpathRoot = if (webElement != null) "descendant::" else "//"
        return WebElementExtGetter(driver, driverExt,
            By.xpath(
                "$xpathRoot*[text()='$label']/ancestor::*[contains(@class, 'MuiFormControl-root')][position()=1]/descendant::*[contains(@class, 'MuiInputBase-input')]"
            )).untilElementInteractable()
    }

    fun getTextboxFromLabel(label: String): TextboxInput {
        return TextboxInput(driver, driverExt, getInputFromLabel(label).highlightAndGetElement())
    }

    fun getDropdownFromLabel(label: String): DropdownInput {
        return DropdownInput(driver, driverExt, driverExt.getElementExtended().byXPath("//label[text() = '$label']").untilElementInteractable().highlightAndGetElement())
    }

    fun getRadioFromLabel(label: String): RadioInput {
        return RadioInput(
            driver,
            driverExt,
            driverExt.getElementExtended().byXPath("//legend[text() = '$label']").untilElementInteractable().highlightAndGetElement()
        )
    }
    fun getDateFromLabel(label: String): DateInput {
        return DateInput(
            driver,
            driverExt,
            driverExt.getElementExtended().byXPath("//label[text() = '$label']").untilElementInteractable().highlightAndGetElement()
        )
    }

    fun getTimeFromLabel(label: String): TimeInput {
        return TimeInput(
            driver,
            driverExt,
            driverExt.getElementExtended().byXPath("//label[text() = '$label']").untilElementInteractable().highlightAndGetElement()
        )
    }

    fun getHTMLFromLabel(label: String): HtmlInput {
        return HtmlInput(
            driver,
            driverExt,
            driverExt.getElementExtended().byXPath("//p[text() = '$label']").untilElementInteractable().highlightAndGetElement()
        )
    }
}