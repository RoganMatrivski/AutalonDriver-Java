package com.nawadata.nfunittestlibrary.finder.webelementext

import com.nawadata.nfunittestlibrary.Consts
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

    @JvmOverloads
    fun byString(
        string: String,
        by: Enums.ByOption = Enums.ByOption.Text,
        tag: String = "*",
        exactText: Boolean = false,
        index: Int = 1
    ) = WebElementExtGetter(
        driver, driverExt,
        if (exactText)
            Tools.getElementContainingStringExact(string, by, tag, index)
        else
            Tools.getElementContainingString(string, by, tag, index)
    )

    fun byXPath(
        xpath: String
    ) = WebElementExtGetter(
        driver, driverExt, By.xpath(xpath))

    @JvmOverloads
    fun waitUntilStringVisible(
        string: String,
        by: Enums.ByOption = Enums.ByOption.Text,
        tag: String = "*",
        exactText: Boolean = false,
        index: Int = 1, timeout: Long = driverExt.timeout
    ) = this.byString(string, by, tag, exactText, index).untilElementVisible(timeout).highlightAndGetElement()

    @JvmOverloads
    fun waitUntilXPathVisible(
        xpath: String, timeout: Long = driverExt.timeout
    ) = this.byXPath(xpath).untilElementVisible(timeout).highlightAndGetElement()

    @JvmOverloads
    fun waitUntilStringInteractable(
        string: String,
        by: Enums.ByOption = Enums.ByOption.Text,
        tag: String = "*",
        exactText: Boolean = false,
        index: Int = 1, timeout: Long = driverExt.timeout
    ) = this.byString(string, by, tag, exactText, index).untilElementInteractable(timeout).highlightAndGetElement()

    @JvmOverloads
    fun waitUntilXPathInteractable(
        xpath: String, timeout: Long = driverExt.timeout
    ) = this.byXPath(xpath).untilElementInteractable(timeout).highlightAndGetElement()

    @JvmOverloads
    fun waitUntilStringInvisible(
        string: String,
        by: Enums.ByOption = Enums.ByOption.Text,
        tag: String = "*",
        exactText: Boolean = false,
        index: Int = 1, timeout: Long = driverExt.timeout
    ) = this.byString(string, by, tag, exactText, index).untilElementInvisible(timeout)

    @JvmOverloads
    fun waitUntilXPathInvisible(
        xpath: String, timeout: Long = driverExt.timeout
    ) = this.byXPath(xpath).untilElementInvisible(timeout)
}