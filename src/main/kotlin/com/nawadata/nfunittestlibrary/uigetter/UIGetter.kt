package com.nawadata.nfunittestlibrary.uigetter

import com.nawadata.nfunittestlibrary.DefaultConfigs
import org.openqa.selenium.WebDriver

class UIGetter (val driver: WebDriver, private val configs: DefaultConfigs) {
    fun extUI() = ExtUIGetter(driver, configs)
    fun reactMUI() = ReactMUIGetter(driver, configs)
}