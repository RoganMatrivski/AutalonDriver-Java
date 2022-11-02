package com.nawadata.nfunittestlibrary

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

    @JvmOverloads
    fun getTextboxFromLabel(label: String, inexactLabel: Boolean = false): TextboxInput {
        val xpathGet =
            if (inexactLabel)
                "//*[${Tools.xpathInexactContains("text()", label)}]"
            else
                "//*[text() = '$label']"

        return TextboxInput(
            driver,
            driverExt,
            driverExt.getElementExtended()
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
            driverExt,
            driverExt.getElementExtended().byString(label, tag = "label", exactText = !inexactLabel)
                .untilElementInteractable().highlightAndGetElement()
        )
    }

    @JvmOverloads
    fun getRadioFromLabel(label: String, inexactLabel: Boolean = false): RadioInput {
        return RadioInput(
            driver,
            driverExt,
            driverExt.getElementExtended().byString(label, tag = "legend", exactText = !inexactLabel)
                .untilElementInteractable().highlightAndGetElement()
        )
    }

    @JvmOverloads
    fun getDateFromLabel(label: String, inexactLabel: Boolean = false): DateInput {
        return DateInput(
            driver,
            driverExt,
            driverExt.getElementExtended().byString(label, tag = "label", exactText = !inexactLabel)
                .untilElementInteractable().highlightAndGetElement()
        )
    }

    @JvmOverloads
    fun getTimeFromLabel(label: String, inexactLabel: Boolean = false): TimeInput {
        return TimeInput(
            driver,
            driverExt,
            driverExt.getElementExtended().byString(label, tag = "label", exactText = !inexactLabel)
                .untilElementInteractable().highlightAndGetElement()
        )
    }

    @JvmOverloads
    fun getHTMLFromLabel(label: String, inexactLabel: Boolean = false): HtmlInput {
        return HtmlInput(
            driver,
            driverExt,
            driverExt.getElementExtended().byString(label, tag = "p", exactText = !inexactLabel)
                .untilElementInteractable().highlightAndGetElement()
        )
    }

    @JvmOverloads
    fun getCheckboxFromLabel(label: String, inexactLabel: Boolean = false): CheckboxInput {
        return CheckboxInput(
            driver,
            driverExt,
            driverExt.getElementExtended().byString(label, tag = "span", exactText = !inexactLabel)
                .untilElementInteractable().highlightAndGetElement()
        )
    }
}