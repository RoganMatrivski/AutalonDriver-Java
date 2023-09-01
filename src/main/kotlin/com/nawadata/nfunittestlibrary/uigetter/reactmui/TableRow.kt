package com.nawadata.nfunittestlibrary.uigetter.reactmui

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

class TableRow (
    private val driver: WebDriver,
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
        val xpathColQuery = "//*[text() = '$columnName']/ancestor::th/preceding-sibling::*"
        val columnIndex = driver.findElements(By.xpath(xpathColQuery)).size + 1

        return getCellElementByIndex(columnIndex)
    }
}