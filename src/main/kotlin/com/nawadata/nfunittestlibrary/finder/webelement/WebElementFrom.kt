package com.nawadata.nfunittestlibrary.finder.webelement

import com.nawadata.nfunittestlibrary.Enums
import com.nawadata.nfunittestlibrary.Tools
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

class WebElementFrom(
    private val driver: WebDriver,
) {
    fun by(by: By) = WebElementGetter(driver, by)

    @JvmOverloads
    fun containingString(
        string: String,
        by: Enums.ByOption = Enums.ByOption.Text,
        tag: String = "*"
    ) =
        WebElementGetter(
            driver,
            Tools.getElementContainingString(string, by, tag)
        )

    @JvmOverloads
    fun containingStringExact(
        string: String,
        by: Enums.ByOption = Enums.ByOption.Text,
        tag: String = "*"
    ) =
        WebElementGetter(
            driver,
            Tools.getElementContainingStringExact(string, by, tag)
        )
}