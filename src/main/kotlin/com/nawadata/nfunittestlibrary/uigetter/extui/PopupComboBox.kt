package com.nawadata.nfunittestlibrary.uigetter.extui

import com.nawadata.nfunittestlibrary.*
import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions
import java.lang.IllegalArgumentException
import java.time.Duration

class PopupComboBox (
    private val driver: WebDriver,
    private val element: WebElement,
    private val componentId: String = element.getAttribute("data-componentid"),
) : BasicInputClass(
    driver,
    element,
    element.getAttribute("data-componentid")
) {
    private val windowXPath = "//*[@id = '${componentId.replace("ComboBox1", "Window")}']"

    //
    fun selectRandomElement(): PopupComboBox {
        // TODO
        return this
    }

    fun clearInput(): PopupComboBox {
        try {
            val clearBtn = driver.getElement().byXPath("//*[@id='$componentId-trigger-_trigger1']").untilElementInteractable(5).highlightAndGetElement()
            clearBtn.click()
            clearBtn.click()
        } catch (_: TimeoutException) {}

        return this
    }

    fun selectElementFromColumns(column: String): PopupComboBox =
        this.selectElementFromColumns(arrayOf(column))

    fun selectElementFromColumns(columns: Array<String>): PopupComboBox {
        val (uiGetter, elGetter) = Pair(driver.uiGetter().extUI(), driver.getElement())

        try {
            elGetter.byXPath(windowXPath).untilElementVisible(2)
        } catch (ex: TimeoutException) {
            elGetter.byXPath("//*[@id = '$componentId-trigger-picker']").clickAwait()
        }

        // Wait until rows shows
        elGetter.byXPath(windowXPath).untilElementVisible()
        elGetter.byXPath("$windowXPath/descendant::*[@role = 'columnheader']/descendant::input").untilElementInteractable()

        val filtersEl = driver.findElements(By.xpath("$windowXPath/descendant::*[@role = 'columnheader']/descendant::input"))

        for ((str, input) in columns.zip(filtersEl)) {
            if (input.getAttribute("value").isNotEmpty()) {
                val clearBtn = input.findElement(By.xpath("following::*[@class = 'x-clear-button']"))
                driver.highlightElement(clearBtn)

                Actions(driver).moveToElement(input).moveToElement(clearBtn).click().perform()
            }

            driver.highlightElement(input)
            Actions(driver).moveToElement(input).click().sendKeys(str).perform()

            driver.uiGetter().extUI().getSpinnerAndWait(rootXPath = windowXPath)
        }

        val targetRowXPath = Tools.getPopupRowXpath(columns, windowXPath)
        elGetter.byXPath(targetRowXPath).untilElementInteractable().highlightAndGetElement().click()

        return this
    }
}