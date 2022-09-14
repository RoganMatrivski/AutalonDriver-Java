package com.nawadata.nfunittestlibrary

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

class WebElementExtended(
    private val driver: WebDriver,
    private val driverExt: WebDriverExtended = WebDriverExtended(driver),
    private val webElement: WebElement?
) {
    constructor(driver: WebDriver) : this(driver, WebDriverExtended(driver), null)
    constructor(driver: WebDriver, driverExt: WebDriverExtended) : this(driver, driverExt, null)

    fun getWebElement() = webElement!!

    fun shouldBe() = ShouldBe(driver, driverExt, webElement!!) // If there's no webElement, just throw exception

    fun getInputFromLabel(label: String): WebElementExtended {
        val xpathRoot = if (webElement == null) "descendant::" else "//"
        val el = webElement ?: driver
        return WebElementExtended(
            driver, driverExt, el.findElement(
                By.xpath(
                    xpathRoot + "span[text() = '" + label
                            + "']/ancestor::label/../descendant::*[@data-ref='inputEl']"
                )
            )
        )
    }

    fun getIFrameFromLabel(label: String): WebElementExtended {
        val xpathRoot = if (webElement == null) "descendant::" else "//"
        val el = webElement ?: driver
        return WebElementExtended(
            driver, driverExt, el
                .findElement(
                    By.xpath(
                        xpathRoot + "span[text() = '" + label
                                + "']/ancestor::label/../descendant::*[@data-ref='iframeEl']"
                    )
                )
        )
    }

    fun getWindowFromTitle(title: String): WebElementExtended {
        val xpathRoot = if (webElement == null) "descendant::" else "//"
        val el = webElement ?: driver
        return WebElementExtended(
            driver, driverExt, el
                .findElement(
                    By.xpath(
                        xpathRoot + "div[text()='" + title
                                + "' and @data-ref='textEl']/"
                                + "ancestor::*[contains(@class, 'x-window x-layer x-window-default')]"
                    )
                )
        )
    }

    fun getGroupFromTitle(title: String): WebElementExtended {
        val xpathRoot = if (webElement == null) "descendant::" else "//"
        val el = webElement ?: driver
        return WebElementExtended(
            driver, driverExt, el
                .findElement(
                    By.xpath(
                        xpathRoot + "div[text()='" + title
                                + "']//ancestor::div[contains(@class, 'x-panel x-panel-default')]"
                    )
                )
        )
    }
}
