package com.nawadata.nfunittestlibrary.uigetter

import org.openqa.selenium.WebDriver

class UIGetter (val driver: WebDriver) {
    fun extUI() = ExtUIGetter(driver)
    fun reactMUI() = ReactMUIGetter(driver)
}