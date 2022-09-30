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
    @Deprecated(
        "Function name was ambiguous with the other. Use byString with exactText param.",
        ReplaceWith("byString"),
        DeprecationLevel.WARNING
    )
    fun withText(string: String, tag: String = "*") =
        WebElementExtGetter(driver, driverExt, Tools.getElementContainingStringExact(string, Enums.ByOption.Text, tag))

    @JvmOverloads
    @Deprecated(
        "Function name was ambiguous with the other. Use byString with exactText param.",
        ReplaceWith("byString"),
        DeprecationLevel.WARNING
    )
    fun containingText(string: String, tag: String = "*") =
        WebElementExtGetter(
            driver, driverExt,
            Tools.getElementContainingString(string, Enums.ByOption.Text, tag)
        )

    @JvmOverloads
    @Deprecated(
        "Function name was ambiguous with the other. Use byString with exactText param.",
        ReplaceWith("byString"),
        DeprecationLevel.WARNING
    )
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
    @Deprecated(
        "Function name was ambiguous with the other. Use byString with exactText param.",
        ReplaceWith("byString"),
        DeprecationLevel.WARNING
    )
    fun withString(
        string: String,
        by: Enums.ByOption = Enums.ByOption.Text,
        tag: String = "*"
    ) =
        WebElementExtGetter(
            driver, driverExt,
            Tools.getElementContainingString(string, by, tag)
        )

    fun byString(
        string: String,
        by: Enums.ByOption = Enums.ByOption.Text,
        tag: String = "*",
        exactText: Boolean = false
    ) = WebElementExtGetter(
        driver, driverExt,
        if (exactText)
            Tools.getElementContainingStringExact(string, by, tag)
        else
            Tools.getElementContainingString(string, by, tag)
    )
}