package com.nawadata.nfunittestlibrary.v2.finder

import com.nawadata.nfunittestlibrary.Enums
import com.nawadata.nfunittestlibrary.Tools
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

class From (
    private val driver: WebDriver,
) {
    fun by(by: By) = Getter(driver, by)

    @JvmOverloads
    fun byString(
        string: String,
        by: Enums.ByOption = Enums.ByOption.Text,
        tag: String = "*",
        exactText: Boolean = false,
        index: Int = 1
    ) = Getter(
        driver,
        if (exactText)
            Tools.getElementContainingStringExact(string, by, tag, index)
        else
            Tools.getElementContainingString(string, by, tag, index)
    )

    fun byStringQueryBuilder(string: String) = ByStringBuilder.Builder(driver, string)

    fun byXPath(xpath: String) = Getter(driver, By.xpath(xpath))
}