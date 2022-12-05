package com.nawadata.nfunittestlibrary.extui

import com.nawadata.nfunittestlibrary.WebDriverExtended
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

class TableRow (
    private val driver: WebDriver,
    private val driverExt: WebDriverExtended,
    private val element: WebElement,
) {
    fun getElement() = element
    fun getCellElementByIndex(nonZeroIndex: Int): WebElement {
        if (nonZeroIndex == 0) {
            throw Exception("Index is at zero")
        }

        val xpathIndex = if (nonZeroIndex < 0) {
            "last() - $nonZeroIndex"
        } else {
            nonZeroIndex
        }

        return element.findElement(By.xpath("descendant::td[$xpathIndex]"))
    }

    fun getCellElementByColumnName(columnName: String): WebElement {
        val xpathColumnQuery = "" +
                "//*[text() = '$columnName']" +
                "/ancestor::div[contains(@class, 'x-column-header ')]" // <== THIS SPACE IS IMPORTANT
        val columnHeader = driver.findElement(By.xpath(xpathColumnQuery))
        val xpathColumnIndexQuery = "preceding-sibling::div[@aria-hidden = 'false']"

        val columnIndex = columnHeader.findElements(By.xpath(xpathColumnIndexQuery)).size + 1

        return getCellElementByIndex(columnIndex)
    }
}