package com.nawadata.nfunittestlibrary.uigetter

import com.nawadata.nfunittestlibrary.Consts
import com.nawadata.nfunittestlibrary.DefaultConfigs
import com.nawadata.nfunittestlibrary.Tools
import com.nawadata.nfunittestlibrary.getElement
import com.nawadata.nfunittestlibrary.uigetter.extui.TableRow
import com.nawadata.nfunittestlibrary.uigetter.extui.TextboxInput
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

class ExtUIGetter(
    private val driver: WebDriver,
    private val configs: DefaultConfigs
) {

    @JvmOverloads
    fun getInputFromLabel(label: String, inexactLabel: Boolean = false, xpathRoot: String = "//", instantReturn: Boolean = false): ShouldBe {
        val elementGetterXPath =
            if (inexactLabel)
                Tools.xpathInexactContains("text()", label)
            else
                "text() = '$label'"

        return if (instantReturn) {
            ShouldBe(driver,
                driver.getElement(configs)
                    .byXPath(xpathRoot + "span[$elementGetterXPath]/ancestor::label/../descendant::*[@data-ref='inputEl']")
                    .now()
            )
        } else {
            ShouldBe(driver,
                driver.getElement(configs)
                    .byXPath(xpathRoot + "span[$elementGetterXPath]/ancestor::label/../descendant::*[@data-ref='inputEl']")
                    .untilElementInteractable().getWebElement()
            )
        }
    }

    @JvmOverloads
    fun getIFrameFromLabel(label: String, inexactLabel: Boolean = false, xpathRoot: String = "//"): ShouldBe {
        val elementGetterXPath =
            if (inexactLabel)
                Tools.xpathInexactContains("text()", label)
            else
                "text() = '$label'"

        return ShouldBe(driver,
            driver.getElement(configs)
                .byXPath(xpathRoot + "span[$elementGetterXPath]/ancestor::label/../descendant::*[@data-ref='iframeEl']")
                .untilElementInteractable().getWebElement()
        )
    }

    @JvmOverloads
    fun getWindowFromTitle(title: String, inexactLabel: Boolean = false, xpathRoot: String = "//"): ShouldBe {
        val elementGetterXPath =
            if (inexactLabel)
                Tools.xpathInexactContains("text()", title)
            else
                "text() = '$title'"

        return ShouldBe(driver,
            driver.getElement(configs)
                .byXPath(xpathRoot +
                        "div[$elementGetterXPath and @data-ref='textEl']/"
                        + "ancestor::*[contains(@class, 'x-window x-layer x-window-default')]")
                .untilElementInteractable().getWebElement()
        )
    }

    @JvmOverloads
    fun getGroupFromTitle(title: String, inexactLabel: Boolean = false, xpathRoot: String = "//"): ShouldBe {
        val elementGetterXPath =
            if (inexactLabel)
                Tools.xpathInexactContains("text()", title)
            else
                "text() = '$title'"

        return ShouldBe(driver,
            driver.getElement(configs)
                .byXPath(xpathRoot + "div[$elementGetterXPath]//ancestor::div[contains(@class, 'x-panel x-panel-default')]")
                .untilElementInteractable().getWebElement()
        )
    }

    @JvmOverloads
    fun getFilterColumnInputByName(columnName: String, inexactLabel: Boolean = false, xpathRoot: String = "//"): TextboxInput {
        val textElementGetter =
            if (inexactLabel)
                Tools.xpathInexactContains("text()", columnName)
            else
                "text() = '$columnName'"

        val xpathQuery = "" +
                "$xpathRoot*[contains(@class, 'x-grid-header-ct')]" +
                "//div[contains(@class, 'x-column-header')]" +
                "//span[$textElementGetter]" +
                "/ancestor::div[@role='columnheader']" +
                "//input"

        val elementSearch = driver.getElement().byXPath(xpathQuery).untilElementInteractable().highlightAndGetElement()

        return TextboxInput(driver, elementSearch)
    }

    fun getRowElementByIndex(nonZeroIndex: Int, xpathRoot: String = "//"): TableRow {
        if (nonZeroIndex == 0) {
            throw Exception("Index is at zero")
        }

        val xpathIndex = if (nonZeroIndex < 0) {
            "last() + 1 $nonZeroIndex"
        } else {
            nonZeroIndex
        }

        val xpathQuery = "$xpathRoot*[contains(@class, 'x-grid-view')]//table[$xpathIndex]"

        val elementSearch = driver.getElement().byXPath(xpathQuery).untilElementInteractable().highlightAndGetElement()

        return TableRow(driver, elementSearch)
    }
}