package com.nawadata.nfunittestlibrary.reactmui

import com.nawadata.nfunittestlibrary.WebDriverExtended
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

/**
 * The type Basic input class.
 */
open class BasicInputClass(
    driver: WebDriver,
    driverExt: WebDriverExtended,
    element: WebElement,
    componentId: String = ""
)
