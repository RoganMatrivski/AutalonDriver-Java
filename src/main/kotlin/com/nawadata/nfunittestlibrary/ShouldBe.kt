package com.nawadata.nfunittestlibrary

import com.nawadata.nfunittestlibrary.exceptions.IncorrectTypeException
import com.nawadata.nfunittestlibrary.extui.*
import org.openqa.selenium.By
import org.openqa.selenium.NoSuchElementException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

/**
 * The type Should be class.
 */
class ShouldBe(
    private val driver: WebDriver,
    private val driverExt: WebDriverExtended,
    private val element: WebElement
) {
    /**
     * Cast input into radio input class.
     *
     * @return the radio input class
     * @throws IncorrectTypeException the incorrect type exception
     */
    @Throws(IncorrectTypeException::class)
    fun radio(): RadioInput {
        return try {
            val radioInputEl = element.findElement(
                By.xpath("ancestor::div[contains(@class, 'x-container x-form-checkboxgroup')]")
            )
            RadioInput(driver, driverExt, radioInputEl)
        } catch (e: NoSuchElementException) {
            throw IncorrectTypeException("Input element is not a radio", e)
        }
    }

    /**
     * Cast input into dropdown input class.
     *
     * @return the dropdown input class
     * @throws IncorrectTypeException the incorrect type exception
     */
    @Throws(IncorrectTypeException::class)
    fun dropdown(): DropdownInput {
        return try {
            val dropdownInputEl = element.findElement(
                By.xpath(
                    "../input["
                            + "@role='combobox'"
                            + " and contains(@aria-owns,'-picker-listEl')"
                            + " and @aria-autocomplete='list']"
                )
            )
            DropdownInput(driver, driverExt, dropdownInputEl)
        } catch (e: NoSuchElementException) {
            throw IncorrectTypeException("Input element is not a dropdown", e)
        }
    }

    /**
     * Cast input into textbox input class.
     *
     * @return the textbox input class
     * @throws IncorrectTypeException the incorrect type exception
     */
    @Throws(IncorrectTypeException::class)
    fun textbox(): TextboxInput {
        return try {
            val textboxInputEl = element.findElement(
                By.xpath("(../input[@role='textbox' and not(@aria-owns)] | ../textarea)")
            )
            TextboxInput(driver, driverExt, textboxInputEl)
        } catch (e: NoSuchElementException) {
            throw IncorrectTypeException("Input element is not a textbox", e)
        }
    }

    /**
     * Cast input into number textbox input class.
     *
     * @return the number textbox input class
     * @throws IncorrectTypeException the incorrect type exception
     */
    @Throws(IncorrectTypeException::class)
    fun numberTextbox(): NumberTextboxInput {
        return try {
            val numberTextboxInputEl =
                element.findElement(By.xpath("../input[@role='spinbutton']"))
            NumberTextboxInput(driver, driverExt, numberTextboxInputEl)
        } catch (e: NoSuchElementException) {
            throw IncorrectTypeException("Input element is not a number", e)
        }
    }

    /**
     * Cast input into date input class.
     *
     * @return the date input class
     * @throws IncorrectTypeException the incorrect type exception
     */
    @Throws(IncorrectTypeException::class)
    fun date(): DateInput {
        return try {
            val dateInputEl = element.findElement(
                By.xpath(
                    "../input[@role='combobox'"
                            + " and contains(@aria-owns,'-picker-eventEl')"
                            + " and @aria-autocomplete='none']"
                )
            )
            DateInput(driver, driverExt, dateInputEl)
        } catch (e: NoSuchElementException) {
            throw IncorrectTypeException("Input element is not a date", e)
        }
    }

    /**
     * Cast input into HTML editor input class.
     *
     * @return the date input class
     * @throws IncorrectTypeException the incorrect type exception
     */
    @Throws(IncorrectTypeException::class)
    fun htmlEditor(): HtmlInput {
        return try {
            val htmlIframe = driver.switchTo().frame(element)
            val htmlIframeExt = WebDriverExtended(htmlIframe)
            val htmlInputEl = htmlIframe.findElement(
                By.xpath(
                    "//body"
                )
            )
            HtmlInput(htmlIframe, htmlIframeExt, htmlInputEl)
        } catch (e: NoSuchElementException) {
            throw IncorrectTypeException("Input element is not a date", e)
        }
    }
}
