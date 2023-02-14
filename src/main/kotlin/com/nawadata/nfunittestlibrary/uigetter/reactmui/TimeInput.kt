package com.nawadata.nfunittestlibrary.uigetter.reactmui

import com.nawadata.nfunittestlibrary.Tools
import com.nawadata.nfunittestlibrary.getElement

import org.openqa.selenium.By
import org.openqa.selenium.Point
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.math.cos
import kotlin.math.sin

class TimeInput(
    private val driver: WebDriver,
    private val element: WebElement,
) : BasicInputClass(
    driver,
    element,
) {
    private fun getElementCenterCoordinate(element: WebElement): Point {
        val location = element.location
        val size = element.size

        val xLoc = location.x + size.width - (size.width / 2)
        val yLoc = location.y + size.height - (size.height / 2)

        return Point(xLoc, yLoc)
    }

    private fun getDegreeFromMinute(min: Int): Float = ((360 - (min * 6) + 90) % 360).toFloat()

    private fun degToRad(deg: Float): Float = (deg * (kotlin.math.PI / 180.0)).toFloat()
    private fun getCoordinateRelativeToDegree(deg: Float, radius: Float = 30.0f): Point {
        val x = radius * cos(degToRad(deg))
        val y = radius * sin(degToRad(deg)) * -1 // Invert y coordinate

        return Point(x.toInt(), y.toInt() - 1) // Don't click on the borders
    }

    fun getText(): String {
        val el = element.findElement(By.xpath(".//ancestor::*[${Tools.xpathInexactContains("@class", "MuiFormControl-root")}]/descendant::input"));

        return el.getAttribute("value");
    }

    private fun fillTime(time: LocalTime) {
        // Click input
        element.findElement(By.xpath(".//ancestor::*[${Tools.xpathInexactContains("@class", "MuiFormControl-root")}]/descendant::input")).click()

        // Base dialog path
        val dateDialogXPath = "//*[@class = 'MuiDialog-root']"

        // Select hour
        driver.getElement().byXPath("$dateDialogXPath/descendant::*[contains(@class, 'MuiToolbar-root')]/descendant::h2").untilElementInteractable().click()
        val hourElement = driver.getElement().byXPath("$dateDialogXPath/descendant::*[@class =  'MuiPickersClock-clock']/span[text() = '${if (time.hour == 0) {"00"} else {time.hour}}']").untilElementInteractable()
        hourElement.highlightAndGetElement()
        Actions(driver).moveToElement(hourElement.getWebElement()).click().perform()

        // Get center pin coordinates
        val centerPin = driver.getElement().byXPath("//*[@class = 'MuiPickersClock-pin']").now()
        val centerPinPos = getElementCenterCoordinate(centerPin)

        // Get position to click
        val minDegree = getDegreeFromMinute(time.minute)
        val relativePosition = getCoordinateRelativeToDegree(minDegree)
        centerPinPos.moveBy(relativePosition.x, relativePosition.y)

        Actions(driver)
            .moveToElement(centerPin)
            .moveByOffset(relativePosition.x, relativePosition.y)
            .click()
            .perform()

        driver.getElement().byXPath("$dateDialogXPath/descendant::button/span[text() = 'OK']").untilElementInteractable().click()
    }

    fun sendText(text: String): TimeInput {
        val dateText: LocalTime = try {
            val formatter = DateTimeFormatter.ofPattern("H:m[:s]")
            LocalTime.parse(text, formatter)
        } catch (e: NumberFormatException) {
            throw IllegalArgumentException(
                "Text argument didn't follow the pattern of 'H:m[:s]'. Please recheck your input",
                e
            )
        }
        fillTime(dateText)
        return this
    }
}