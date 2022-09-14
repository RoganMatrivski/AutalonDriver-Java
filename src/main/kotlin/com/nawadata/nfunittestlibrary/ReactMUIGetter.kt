package com.nawadata.nfunittestlibrary

import com.nawadata.nfunittestlibrary.reactmui.TextboxInput
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import javax.xml.soap.Text

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
        val el = webElement ?: driver
        return WebElementExtended(
            driver, driverExt, el.findElement(
                By.xpath(
                    "$xpathRoot*[text()='$label']/ancestor::*[contains(@class, 'MuiFormControl-root')][position()=1]/descendant::*[contains(@class, 'MuiInputBase-input')]"
                )
            )
        )
    }

    fun getTextboxFromLabel(label: String): com.nawadata.nfunittestlibrary.reactmui.TextboxInput {
        return TextboxInput(driver, driverExt, getInputFromLabel(label).getWebElement())
    }
}