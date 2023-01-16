package com.nawadata.nfunittestlibrary.v2.uigetter

import com.nawadata.nfunittestlibrary.Tools
import com.nawadata.nfunittestlibrary.v2.uigetter.reactmui.*
import com.nawadata.nfunittestlibrary.v2.*
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

class ReactMUIGetter(
    private val driver: WebDriver,
    private val webElement: WebElement?
) {
    constructor(driver: WebDriver) : this(driver, null)

    @JvmOverloads
    fun getTextboxFromLabel(label: String, inexactLabel: Boolean = false): TextboxInput {
        val xpathGet =
            if (inexactLabel)
                "//*[${Tools.xpathInexactContains("text()", label)}]"
            else
                "//*[text() = '$label']"

        return TextboxInput(
            driver,
            driver.getElement()
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
            driver.getElement().byString(label, tag = "label", exactText = !inexactLabel)
                .untilElementInteractable().highlightAndGetElement()
        )
    }

    @JvmOverloads
    fun getRadioFromLabel(label: String, inexactLabel: Boolean = false): RadioInput {
        return RadioInput(
            driver,
            driver.getElement().byString(label, tag = "legend", exactText = !inexactLabel)
                .untilElementInteractable().highlightAndGetElement()
        )
    }

    @JvmOverloads
    fun getDateFromLabel(label: String, inexactLabel: Boolean = false): DateInput {
        return DateInput(
            driver,
            driver.getElement().byString(label, tag = "label", exactText = !inexactLabel)
                .untilElementInteractable().highlightAndGetElement()
        )
    }

    @JvmOverloads
    fun getTimeFromLabel(label: String, inexactLabel: Boolean = false): TimeInput {
        return TimeInput(
            driver,
            driver.getElement().byString(label, tag = "label", exactText = !inexactLabel)
                .untilElementInteractable().highlightAndGetElement()
        )
    }

    @JvmOverloads
    fun getHTMLFromLabel(label: String, inexactLabel: Boolean = false): HtmlInput {
        return HtmlInput(
            driver,
            driver.getElement().byString(label, tag = "p", exactText = !inexactLabel)
                .untilElementInteractable().highlightAndGetElement()
        )
    }

    @JvmOverloads
    fun getCheckboxFromLabel(label: String, inexactLabel: Boolean = false): CheckboxInput {
        return CheckboxInput(
            driver,
            driver.getElement().byString(label, tag = "span", exactText = !inexactLabel)
                .untilElementInteractable().highlightAndGetElement()
        )
    }
}