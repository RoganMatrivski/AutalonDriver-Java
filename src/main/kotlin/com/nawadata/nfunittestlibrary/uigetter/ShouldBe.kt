package com.nawadata.nfunittestlibrary.uigetter

import com.nawadata.nfunittestlibrary.exceptions.IncorrectTypeException
import com.nawadata.nfunittestlibrary.uigetter.extui.DateInput
import com.nawadata.nfunittestlibrary.uigetter.extui.DropdownInput
import com.nawadata.nfunittestlibrary.uigetter.extui.HtmlInput
import com.nawadata.nfunittestlibrary.uigetter.extui.NumberTextboxInput
import com.nawadata.nfunittestlibrary.uigetter.extui.RadioInput
import com.nawadata.nfunittestlibrary.uigetter.extui.TextboxInput
import org.openqa.selenium.By
import org.openqa.selenium.NoSuchElementException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

/**
 * The type Should be class.
 */
class ShouldBe(
    private val driver: WebDriver,
    private val element: WebElement
) {
    /**
     * Cast input into radio input class.
     *
     * @return the radio input class
     * @throws IncorrectTypeException the incorrect type exception
     */
    @Throws(IncorrectTypeException::class)
    fun asRadio(): RadioInput {
        return try {
            val radioInputEl = element.findElement(
                By.xpath("ancestor::div[contains(@class, 'x-container x-form-checkboxgroup')]")
            )
            RadioInput(driver, radioInputEl)
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
    fun asDropdown(): DropdownInput {
        return try {
            val dropdownInputEl = element.findElement(
                By.xpath(
                    "../input["
                            + "@role='combobox'"
                            + " and contains(@aria-owns,'-picker-listEl')"
                            + " and @aria-autocomplete='list']"
                )
            )
            DropdownInput(driver, dropdownInputEl)
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
    fun asTextbox(): TextboxInput {
        return try {
            val textboxInputEl = element.findElement(
                By.xpath("(../input[@role='textbox' and not(@aria-owns)] | ../textarea)")
            )
            TextboxInput(driver, textboxInputEl)
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
    fun asNumberTextbox(): NumberTextboxInput {
        return try {
            val numberTextboxInputEl =
                element.findElement(By.xpath("../input[@role='spinbutton']"))
            NumberTextboxInput(driver, numberTextboxInputEl)
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
    fun asDate(): DateInput {
        return try {
            val dateInputEl = element.findElement(
                By.xpath(
                    "../input[@role='combobox'"
                            + " and contains(@aria-owns,'-picker-eventEl')"
                            + " and @aria-autocomplete='none']"
                )
            )
            DateInput(driver, dateInputEl)
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
    fun asHtmlEditor(): HtmlInput {
        return try {
            val htmlIframe = driver.switchTo().frame(element)
            val htmlInputEl = htmlIframe.findElement(
                By.xpath(
                    "//body"
                )
            )
            HtmlInput(htmlIframe, htmlInputEl)
        } catch (e: NoSuchElementException) {
            throw IncorrectTypeException("Input element is not a date", e)
        }
    }
}
