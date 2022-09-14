package com.nawadata.nfunittestlibrary.finder.webelementext

import com.nawadata.nfunittestlibrary.Enums
import com.nawadata.nfunittestlibrary.Tools
import com.nawadata.nfunittestlibrary.WebDriverExtended
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

class WebElementExtFrom(
    private val driver: WebDriver,
    private val driverExt: WebDriverExtended,
) {
    fun by(by: By) = WebElementExtGetter(driver, driverExt, by)

    @JvmOverloads
    fun withText(string: String, tag: String = "*") =
        WebElementExtGetter(driver, driverExt, Tools.getElementContainingStringExact(string, Enums.ByOption.Text, tag))

    @JvmOverloads
    fun containingText(string: String, tag: String = "*") =
        WebElementExtGetter(
            driver, driverExt,
            Tools.getElementContainingString(string, Enums.ByOption.Text, tag)
        )

    @JvmOverloads
    fun containingString(
        string: String,
        by: Enums.ByOption = Enums.ByOption.Text,
        tag: String = "*"
    ) =
        WebElementExtGetter(
            driver, driverExt,
            Tools.getElementContainingString(string, by, tag)
        )

    @JvmOverloads
    fun withString(
        string: String,
        by: Enums.ByOption = Enums.ByOption.Text,
        tag: String = "*"
    ) =
        WebElementExtGetter(
            driver, driverExt,
            Tools.getElementContainingString(string, by, tag)
        )
}