package com.nawadata.nfunittestlibrary

import com.nawadata.nfunittestlibrary.extui.TableRow
import com.nawadata.nfunittestlibrary.extui.TextboxInput
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

class ExtUIGetter(
    private val driver: WebDriver,
    private val driverExt: WebDriverExtended = WebDriverExtended(driver),
    private val webElement: WebElement?
) {
    constructor(driver: WebDriver) : this(driver, WebDriverExtended(driver), null)
    constructor(driver: WebDriver, driverExt: WebDriverExtended) : this(driver, driverExt, null)

    fun shouldBe() = ShouldBe(driver, driverExt, webElement!!) // If there's no webElement, just throw exception

    @JvmOverloads
    fun getInputFromLabel(label: String, inexactLabel: Boolean = false): ExtUIGetter {
        val elementGetterXPath =
            if (inexactLabel)
                Tools.xpathInexactContains("text()", label)
            else
                "text() = '$label'"

        val xpathRoot = if (webElement == null) "descendant::" else "//"
        val el = webElement ?: driver
        return ExtUIGetter(
            driver, driverExt, el.findElement(
                By.xpath(
                    xpathRoot + "span[$elementGetterXPath]/ancestor::label/../descendant::*[@data-ref='inputEl']"
                )
            )
        )
    }

    @JvmOverloads
    fun getIFrameFromLabel(label: String, inexactLabel: Boolean = false): ExtUIGetter {
        val elementGetterXPath =
            if (inexactLabel)
                Tools.xpathInexactContains("text()", label)
            else
                "text() = '$label'"

        val xpathRoot = if (webElement == null) "descendant::" else "//"
        val el = webElement ?: driver
        return ExtUIGetter(
            driver, driverExt, el
                .findElement(
                    By.xpath(
                        xpathRoot + "span[$elementGetterXPath]/ancestor::label/../descendant::*[@data-ref='iframeEl']"
                    )
                )
        )
    }

    @JvmOverloads
    fun getWindowFromTitle(title: String, inexactLabel: Boolean = false): ExtUIGetter {
        val elementGetterXPath =
            if (inexactLabel)
                Tools.xpathInexactContains("text()", title)
            else
                "text() = '$title'"

        val xpathRoot = if (webElement == null) "descendant::" else "//"
        val el = webElement ?: driver
        return ExtUIGetter(
            driver, driverExt, el
                .findElement(
                    By.xpath(
                        xpathRoot +
                            "div[$elementGetterXPath and @data-ref='textEl']/"
                            + "ancestor::*[contains(@class, 'x-window x-layer x-window-default')]"
                    )
                )
        )
    }

    @JvmOverloads
    fun getGroupFromTitle(title: String, inexactLabel: Boolean = false): ExtUIGetter {
        val elementGetterXPath =
            if (inexactLabel)
                Tools.xpathInexactContains("text()", title)
            else
                "text() = '$title'"

        val xpathRoot = if (webElement == null) "descendant::" else "//"
        val el = webElement ?: driver
        return ExtUIGetter(
            driver, driverExt, el
                .findElement(
                    By.xpath(
                        xpathRoot + "div[$elementGetterXPath]//ancestor::div[contains(@class, 'x-panel x-panel-default')]"
                    )
                )
        )
    }

    @JvmOverloads
    fun getFilterColumnInputByName(columnName: String, inexactLabel: Boolean = false): TextboxInput {
        val textElementGetter =
            if (inexactLabel)
                Tools.xpathInexactContains("text()", columnName)
            else
                "text() = '$columnName'"

        val xpathRoot = if (webElement == null) "descendant::" else "//"
        val el = webElement ?: driver
        val xpathQuery = "" +
                "$xpathRoot*[contains(@class, 'x-grid-header-ct')]" +
                "//div[contains(@class, 'x-column-header')]" +
                "//span[$textElementGetter]" +
                "/ancestor::div[@role='columnheader']" +
                "//input"

        val elementSearch = el.findElement(By.xpath(xpathQuery))
        WebDriverWait(driver, Consts.defaultTimeout.seconds)
            .until(ExpectedConditions.elementToBeClickable(elementSearch))

        return TextboxInput(driver, driverExt, elementSearch)
    }

    fun getRowElementByIndex(nonZeroIndex: Int): TableRow {
        if (nonZeroIndex == 0) {
            throw Exception("Index is at zero")
        }

        val xpathIndex = if (nonZeroIndex < 0) {
            "last() $nonZeroIndex"
        } else {
            nonZeroIndex
        }

        val xpathRoot = if (webElement != null) "descendant::" else "//"
        val el = webElement ?: driver
        val xpathQuery = "$xpathRoot*[contains(@class, 'x-grid-view')]//table[$xpathIndex]"

        val elementSearch = el.findElement(By.xpath(xpathQuery))
        WebDriverWait(driver, Consts.defaultTimeout.seconds)
            .until(ExpectedConditions.visibilityOf(elementSearch))

        return TableRow(driver, driverExt, elementSearch)
    }
}