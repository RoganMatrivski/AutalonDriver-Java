package com.nawadata.nfunittestlibrary.uigetter.extui

import com.nawadata.nfunittestlibrary.*
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
import java.time.Duration
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DateInput(
    private val driver: WebDriver,
    private val element: WebElement,
) : BasicInputClass(
    driver,
    element,
    element.getAttribute("data-componentid")
) {
    companion object {
        @JvmStatic
        fun fillDate(
            driver: WebDriver,
            dateField: WebElement,
            randomDate: LocalDate
        ) {
            val randomYear = randomDate.year
            // final Month randomMonth = randomDate.getMonth();
            val randomDay = randomDate.dayOfMonth
            val formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy")
            formatter.format(randomDate)
            val componentid = dateField.getAttribute("data-componentid")

            driver.scrollToElementJS(dateField, ScrollAlignment.Start)

            // Fix popups (probably)
            // Click trigger, click to the side, and then click the trigger again.
            // Dumb AF, but it works, so why not.
            // Blame old framework version that forces me to do this.
            driver.findElement(By.id(String.format("%s-trigger-picker", componentid)))
                .click()
            driver.wait(Duration.ofMillis(500))
            // Actions(driver).moveByOffset(200, 0).click().perform()
            driver.getElement().byXPath("//*[@id='$componentid']/descendant::label").clickAwait()
            driver.wait(Duration.ofMillis(500))

            val elGetter = driver.getElement()

            elGetter.byXPath("//*[@id='$componentid-trigger-picker']").clickAwait()

            val datePickerXpath = "//*[@id='$componentid-picker']"
            val datePickerComponent =
                elGetter.byXPath(datePickerXpath).untilElementVisible().getWebElement()
            elGetter.byXPath("$datePickerXpath/descendant::*[@data-ref='middleBtnEl']/descendant::*[@data-ref='btnInnerEl']")
                .clickAwait()
            val monthPickerXpath = "$datePickerXpath/*[contains(@id, 'monthpicker')]"
            val yearPickerXpath = "$monthPickerXpath/descendant::*[@data-ref='yearEl']"
            val yearPickerComponent =
                elGetter.byXPath(yearPickerXpath).untilElementVisible().getWebElement()
            val yearPickerNavPrevComponent =
                elGetter.byXPath("${yearPickerXpath}/descendant::*[@data-ref='prevEl']").untilElementVisible()
                    .getWebElement()
            val yearPickerNavNextComponent =
                elGetter.byXPath("${yearPickerXpath}/descendant::*[@data-ref='nextEl']").untilElementVisible()
                    .getWebElement()

            // Wait for the elements to be interactable
            driver.wait(Duration.ofMillis(400))
            while (true) {
                val earliestYearAvailableStr = yearPickerComponent.findElement(
                    By.xpath("*/*[@class='x-monthpicker-item-inner']")
                ).text
                val earliestYearAvailable = earliestYearAvailableStr.toInt()
                if (randomYear < earliestYearAvailable) {
                    yearPickerNavPrevComponent.click()
                    continue
                }
                if (randomYear >= earliestYearAvailable + 10) {
                    yearPickerNavNextComponent.click()
                    continue
                }
                break
            }
            yearPickerComponent.findElement(
                By.xpath(String.format("descendant::*[text()='%s']", randomYear))
            ).click()
            val monthListParent = datePickerComponent.findElement(
                By.xpath("*[contains(@id, 'monthpicker')]/descendant::*[@data-ref='monthEl']")
            )
            monthListParent.findElement(
                By.xpath(
                    String.format(
                        "descendant::*[text()='%s']",
                        DateTimeFormatter.ofPattern("MMM").format(randomDate)
                    )
                )
            ).click()
            datePickerComponent.findElement(
                By.xpath("*[contains(@id, 'monthpicker')]/descendant::span[text()='OK']")
            ).click()

            // Wait for the elements to be interactable
            driver.wait(Duration.ofMillis(400))
            val dateListParent = datePickerComponent.findElement(
                By.xpath("descendant::*[@data-ref='innerEl']/table/tbody")
            )
            dateListParent.findElement(
                By.xpath(
                    String.format(
                        "descendant::*[contains(@class, 'x-datepicker-active')]/*[text()='%s']",
                        randomDay
                    )
                )
            ).click()
        }
    }

    fun sendText(text: String?): DateInput {
        val element = element
        val dateText: LocalDate = try {
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            LocalDate.parse(text, formatter)
        } catch (e: NumberFormatException) {
            throw IllegalArgumentException(
                "Text argument didn't follow the pattern of 'dd-MM-yyyy'. Please recheck your input",
                e
            )
        }
        fillDate(driver, element, dateText)
        return this
    }
}