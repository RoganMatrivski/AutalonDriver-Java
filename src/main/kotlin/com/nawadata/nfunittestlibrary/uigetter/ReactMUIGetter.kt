package com.nawadata.nfunittestlibrary.uigetter

import com.nawadata.nfunittestlibrary.DefaultConfigs
import com.nawadata.nfunittestlibrary.Tools
import com.nawadata.nfunittestlibrary.getElement
import com.nawadata.nfunittestlibrary.scrollToElement
import com.nawadata.nfunittestlibrary.uigetter.reactmui.*
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

class ReactMUIGetter(
    private val driver: WebDriver,
    private val configs: DefaultConfigs,
    private val webElement: WebElement?
) {
    constructor(driver: WebDriver, configs: DefaultConfigs) : this(driver, configs, null)

    @JvmOverloads
    fun getTextboxFromLabel(label: String, inexactLabel: Boolean = false): TextboxInput {
        val xpathGet =
            if (inexactLabel)
                "//*[${Tools.xpathInexactContains("text()", label)}]"
            else
                "//*[text() = '$label']"

        return TextboxInput(
            driver,
            driver.getElement(configs)
                .byXPath(
                    "$xpathGet/" +
                            "ancestor::*[contains(@class, 'MuiFormControl-root')][1]" +
                            "/descendant::*[contains(@class, 'MuiInputBase-input')]"
                )
                .untilElementInteractable().highlightAndGetElement()
        )
    }

    @JvmOverloads
    fun getDropdownFromLabel(label: String, inexactLabel: Boolean = false): DropdownInput {
        return DropdownInput(
            driver,
            driver.getElement(configs).byString(label, tag = "label", exactText = !inexactLabel)
                .untilElementInteractable().highlightAndGetElement()
        )
    }

    @JvmOverloads
    fun getRadioFromLabel(label: String, inexactLabel: Boolean = false): RadioInput {
        return RadioInput(
            driver,
            driver.getElement(configs).byString(label, tag = "legend", exactText = !inexactLabel)
                .untilElementInteractable().highlightAndGetElement()
        )
    }

    @JvmOverloads
    fun getDateFromLabel(label: String, inexactLabel: Boolean = false): DateInput {
        return DateInput(
            driver,
            driver.getElement(configs).byString(label, tag = "label", exactText = !inexactLabel)
                .untilElementInteractable().highlightAndGetElement()
        )
    }

    @JvmOverloads
    fun getTimeFromLabel(label: String, inexactLabel: Boolean = false): TimeInput {
        return TimeInput(
            driver,
            driver.getElement(configs).byString(label, tag = "label", exactText = !inexactLabel)
                .untilElementInteractable().highlightAndGetElement()
        )
    }

    @JvmOverloads
    fun getHTMLFromLabel(label: String, inexactLabel: Boolean = false): HtmlInput {
        return HtmlInput(
            driver,
            driver.getElement(configs).byString(label, tag = "p", exactText = !inexactLabel)
                .untilElementInteractable().highlightAndGetElement()
        )
    }

    @JvmOverloads
    fun getCheckboxFromLabel(label: String, inexactLabel: Boolean = false): CheckboxInput {
        return CheckboxInput(
            driver,
            driver.getElement(configs).byString(label, tag = "span", exactText = !inexactLabel)
                .untilElementInteractable().highlightAndGetElement()
        )
    }

    @JvmOverloads
    fun getRowElementByIndex(nonZeroIndex: Int): TableRow {
        if (nonZeroIndex == 0) {
            throw Exception("Index is at zero")
        }

        val xpathIndex = if (nonZeroIndex < 0) {
            "last() + 1 $nonZeroIndex"
        } else {
            nonZeroIndex
        }

        val xpathQuery = "//div[contains(@class, 'nawatable-main-table')]/table/tbody/tr[$xpathIndex]"

        val elementSearch = driver.getElement(configs).byXPath(xpathQuery).untilElementInteractable().highlightAndGetElement()
        driver.scrollToElement(elementSearch)

        return TableRow(driver, elementSearch)
    }
}