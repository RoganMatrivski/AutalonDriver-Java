package com.nawadata.nfunittestlibrary.reactmui

import com.nawadata.nfunittestlibrary.WebDriverExtended
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import java.time.Duration
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DateInput(
    private val driver: WebDriver,
    private val driverExt: WebDriverExtended,
    private val element: WebElement,
    private val componentId: String = element.getAttribute("data-componentid"),
) : BasicInputClass(
    driver,
    driverExt,
    element,
    element.getAttribute("data-componentid")
) {
    companion object {
        @JvmStatic
        fun fillDate(
            driver: WebDriver,
            driverExt: WebDriverExtended,
            dateField: WebElement,
            randomDate: LocalDate
        ) {
            val randomYear = randomDate.year
            // final Month randomMonth = randomDate.getMonth();
            val randomDay = randomDate.dayOfMonth
            val formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy")
            val randDate = formatter.format(randomDate)
            val componentid = dateField.getAttribute("data-componentid")
            driver.findElement(By.id(String.format("%s-trigger-picker", componentid)))
                .click()
            val datePickerComponent =
                driver.findElement(By.id(String.format("%s-picker", componentid)))
            datePickerComponent.findElement(
                By.xpath("descendant::*[@data-ref='middleBtnEl']/descendant::*[@data-ref='btnInnerEl']")
            ).click()
            val monthPickerComponent =
                datePickerComponent.findElement(By.xpath("*[contains(@id, 'monthpicker')]"))
            val yearPickerComponent =
                monthPickerComponent.findElement(By.xpath("descendant::*[@data-ref='yearEl']"))
            val yearPickerNavPrevComponent =
                yearPickerComponent.findElement(By.xpath("descendant::*[@data-ref='prevEl']"))
            val yearPickerNavNextComponent =
                yearPickerComponent.findElement(By.xpath("descendant::*[@data-ref='nextEl']"))

            // Wait for the elements to be interactable
            driverExt.wait(Duration.ofMillis(400))
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
            driverExt.wait(Duration.ofMillis(400))
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

    fun sendText(text: String?, ignoreErrors: Boolean?): DateInput {
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
        fillDate(driver, driverExt, element, dateText)
        return this
    }
}