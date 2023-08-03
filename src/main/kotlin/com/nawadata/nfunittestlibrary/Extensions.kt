package com.nawadata.nfunittestlibrary

import com.nawadata.nfunittestlibrary.finder.From
import com.nawadata.nfunittestlibrary.uigetter.UIGetter
import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration
import java.time.LocalDateTime
import java.util.function.Function

fun WebDriver.getJsExecutor(): JavascriptExecutor {
    return if (this is JavascriptExecutor) {
        this
    } else {
        throw IllegalStateException("This driver does not support JavaScript!")
    }
}

private fun WebDriver.handleElementTimeoutException(ex: TimeoutException, by: By) {
    val element = this.findElement(by) ?: throw NoSuchElementException(null)
    if (!element.isDisplayed || !element.isEnabled) {
        throw ElementNotInteractableException(null) // Invisible element from now on returns this
    }
    throw ex
}

fun WebDriver.wait(delay: Duration) {
    val now = LocalDateTime.now()
    val wait = WebDriverWait(this, delay.seconds + 30)
    wait.until {
        Duration.between(now, LocalDateTime.now()) - delay > Duration.ZERO
    }
}

fun WebDriver.waitSeconds(delay: Long) = this.wait(Duration.ofSeconds(delay))

fun WebDriver.waitForFunc(
    func: Function<WebDriver, WebElement>,
    timeout: Long = Consts.defaultTimeout.seconds
): WebElement = WebDriverWait(this, timeout).until(func)

@JvmOverloads
fun WebDriver.waitUntilVisible(by: By, customTimeout: Long = 60): WebElement? {
    return try {
        WebDriverWait(this, customTimeout)
            .until(ExpectedConditions.visibilityOfElementLocated(by))
    } catch (ex: TimeoutException) {
        this.handleElementTimeoutException(ex, by)
        null
    }
}

@JvmOverloads
fun WebDriver.waitUntilInvisible(by: By, customTimeout: Long = 60): Boolean? {
    return try {
        WebDriverWait(this, customTimeout)
            .until(ExpectedConditions.invisibilityOfElementLocated(by))
    } catch (ex: TimeoutException) {
        this.handleElementTimeoutException(ex, by)
        null
    } catch (e: Exception) {
        throw e
    }
}

@JvmOverloads
fun WebDriver.waitUntilFrameLoads(by: By, customTimeout: Long = 60): WebDriver =
    WebDriverWait(this, customTimeout)
        .until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(by))

fun WebDriver.scrollToElement(element: WebElement) {
    Actions(this).moveToElement(element).perform()
    this.getJsExecutor().executeScript("arguments[0].scrollIntoView(true);", element)
}
fun WebDriver.isElementStale(element: WebElement): Boolean {
    return try {
        element.isEnabled
        false
    } catch (exception: StaleElementReferenceException) {
        true
    }
}

fun WebDriver.setWindowDimension(width: Int, height: Int) {
    this.manage().window().size = Dimension(width, height)
}

fun WebDriver.highlightElement(element: WebElement): Any =
    this.getJsExecutor().executeScript("arguments[0].style.border='3px solid red'", element)

fun WebDriver.getExtendedElement() =
    WebElementExtended(this, null)

@JvmOverloads
fun WebDriver.getElement(configs: DefaultConfigs = DefaultConfigs()) =
    From(this, configs)

@JvmOverloads
fun WebDriver.uiGetter(configs: DefaultConfigs = DefaultConfigs()) =
    UIGetter(this, configs)

fun WebElement.toExtended(driver: WebDriver) =
    WebElementExtended(driver, this)

fun WebElement.getSelector(): By {
    //[[ChromeDriver: chrome on XP (d85e7e220b2ec51b7faf42210816285e)] -> xpath: //input[@title='Search']]
    val pathVariables: List<String> = this.toString().split("->")[1].replaceFirst("(?s)(.*)\\]", "$1" + "")
        .split(":")

    val selector = pathVariables[0].trim { it <= ' ' }
    val value = pathVariables[1].trim { it <= ' ' }

    return when (selector) {
        "id" -> By.id(value)
        "className" -> By.className(value)
        "tagName" -> By.tagName(value)
        "xpath" -> By.xpath(value)
        "cssSelector" -> By.cssSelector(value)
        "linkText" -> By.linkText(value)
        "name" -> By.name(value)
        "partialLinkText" -> By.partialLinkText(value)
        else -> throw IllegalStateException("Locator: $selector not found!")
    }
}

//fun WebDriver.findElementAsRefreshable(by: By) =
//    RefreshableWebElement(this, by)