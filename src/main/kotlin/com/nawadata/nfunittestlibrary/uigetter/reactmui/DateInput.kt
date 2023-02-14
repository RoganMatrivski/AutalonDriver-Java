package com.nawadata.nfunittestlibrary.uigetter.reactmui

import com.nawadata.nfunittestlibrary.Tools
import com.nawadata.nfunittestlibrary.getElement

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.abs

class DateInput(
    private val driver: WebDriver,
    private val element: WebElement,
) : BasicInputClass(
    driver,
    element,
) {
    private fun monthStringToInt(month: String) =
        SimpleDateFormat("MMMM", Locale.ENGLISH)
            .parse(month)
            .toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
            .monthValue // This is helluva complicated way to parse month string to int.

    private fun fillDate(date: LocalDate) {
        // Click input
        element.findElement(By.xpath(".//ancestor::*[${Tools.xpathInexactContains("@class", "MuiFormControl-root")}]/descendant::input")).click()

        // Base dialog path
        val dateDialogXPath = "//*[@class = 'MuiDialog-root']"

        // Select year
        driver.getElement().byXPath("$dateDialogXPath/descendant::*[contains(@class, 'MuiToolbar-root')]/descendant::h6").untilElementInteractable().click()
        driver.getElement().byXPath("$dateDialogXPath/descendant::*[@class='MuiPickersYearSelection-container']/*[text() = '${date.year}']").untilElementInteractable().click()

        // Get currently selected month
        val headerText = driver.getElement().byXPath("$dateDialogXPath/descendant::*[@class = 'MuiPickersCalendarHeader-switchHeader']/descendant::p").untilElementInteractable().getWebElement().text
        val monthText = headerText.split(" ")[0]
        val currentMonth = monthStringToInt(monthText)
        val selectedMonth = date.monthValue

        // Determine how many times to press the prev/next button
        val stepAmount = selectedMonth - currentMonth

        // Press the next/prev button
        when {
            stepAmount < 0 -> {
                repeat(abs(stepAmount)) {
                    driver.getElement().byXPath("$dateDialogXPath/descendant::*[@class = 'MuiPickersCalendarHeader-switchHeader']/button[1]").untilElementInteractable().click()
                }
            }

            stepAmount > 0 -> {
                repeat(stepAmount) {
                    driver.getElement().byXPath("$dateDialogXPath/descendant::*[@class = 'MuiPickersCalendarHeader-switchHeader']/button[2]").untilElementInteractable().click()
                }
            }
        }

        driver.getElement().byXPath("$dateDialogXPath/descendant::*[@class = 'MuiPickersCalendar-week']/descendant::p[text() = '${date.dayOfMonth}']").untilElementInteractable().click()
    }

    fun sendText(text: String): DateInput {
        val dateText: LocalDate = try {
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            LocalDate.parse(text, formatter)
        } catch (e: NumberFormatException) {
            throw IllegalArgumentException(
                "Text argument didn't follow the pattern of 'dd-MM-yyyy'. Please recheck your input",
                e
            )
        }
        fillDate(dateText)
        return this
    }
}