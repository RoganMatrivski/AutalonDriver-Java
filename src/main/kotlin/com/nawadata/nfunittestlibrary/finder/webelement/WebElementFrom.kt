package com.nawadata.nfunittestlibrary.finder.webelement

import com.nawadata.nfunittestlibrary.Enums
import com.nawadata.nfunittestlibrary.Tools
import com.nawadata.nfunittestlibrary.finder.webelementext.WebElementExtGetter
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

class WebElementFrom(
    private val driver: WebDriver,
) {
    fun by(by: By) = WebElementGetter(driver, by)

    @JvmOverloads
    @Deprecated(
        "Function name was ambiguous with the other. Use byString with exactText param.",
        ReplaceWith("byString()"),
        DeprecationLevel.WARNING
    )
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
    @Deprecated(
        "Function name was ambiguous with the other. Use byString with exactText param.",
        ReplaceWith("byString()"),
        DeprecationLevel.WARNING
    )
    fun containingStringExact(
        string: String,
        by: Enums.ByOption = Enums.ByOption.Text,
        tag: String = "*"
    ) =
        WebElementGetter(
            driver,
            Tools.getElementContainingStringExact(string, by, tag)
        )

    fun byString(
        string: String,
        by: Enums.ByOption = Enums.ByOption.Text,
        tag: String = "*",
        exactText: Boolean = false
    ) = WebElementGetter(
        driver,
        if (exactText)
            Tools.getElementContainingStringExact(string, by, tag)
        else
            Tools.getElementContainingString(string, by, tag)
    )
}