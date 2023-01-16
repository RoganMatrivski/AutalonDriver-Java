package com.nawadata.nfunittestlibrary.v2.uigetter.reactmui


import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions

class HtmlInput(
    private val driver: WebDriver,
    private val element: WebElement,
) : BasicInputClass(
    driver,
    element,
) {
    fun clearText(): HtmlInput {
        val element = this.element.findElement(By.xpath("../descendant::*[@class = 'sun-editor']/descendant::textarea"))
        val codeViewBtn = this.element.findElement(By.xpath("../descendant::*[@class = 'sun-editor']/descendant::button[@data-command = 'codeView']"))

        Actions(driver)
            .click(codeViewBtn)

            .click(element)
            .keyDown(Keys.CONTROL)
            .sendKeys("a")
            .keyUp(Keys.CONTROL)
            .sendKeys(Keys.BACK_SPACE)

            .click(codeViewBtn)

            .perform()

        return this
    }

    fun sendText(text: String): HtmlInput {
        val element = this.element.findElement(By.xpath("../descendant::*[@class = 'sun-editor']/descendant::textarea"))

        element.sendKeys(text)
        return this
    }

    fun sendRawText(text: String): HtmlInput {
        val element = this.element.findElement(By.xpath("../descendant::*[@class = 'sun-editor']/descendant::textarea"))
        val codeViewBtn = this.element.findElement(By.xpath("../descendant::*[@class = 'sun-editor']/descendant::button[@data-command = 'codeView']"))

        Actions(driver)
            .click(codeViewBtn)

            .click(element)
            .keyDown(Keys.CONTROL)
            .sendKeys("a")
            .keyUp(Keys.CONTROL)
            .sendKeys(Keys.BACK_SPACE)

            .click(element)
            .sendKeys(text)

            .click(codeViewBtn)

            .perform()

        return this
    }
}

