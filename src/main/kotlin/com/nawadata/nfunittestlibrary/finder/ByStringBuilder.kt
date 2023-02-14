package com.nawadata.nfunittestlibrary.finder

import com.nawadata.nfunittestlibrary.Enums
import com.nawadata.nfunittestlibrary.Tools
import org.openqa.selenium.WebDriver

//class ByStringBuilder {
//    private var string: String = ""
//    private var by: Enums.ByOption = Enums.ByOption.Text
//    private var tag: String = "*"
//    private var exactText: Boolean = false
//    private var index: Int = 1
//
//    fun string(string: String) = apply { this.string = string }
//    fun by(by: Enums.ByOption) = apply { this.by = by }
//    fun tag(tag: String) = apply { this.tag = tag }
//    fun exactText(exactText: Boolean) = apply { this.exactText = exactText }
//    fun index(index: Int) = apply { this.index = index }
//
//    fun build() = byString(string, by, tag, exactText, index)
//}

class ByStringBuilder private constructor(
    val driver: WebDriver,
    val string: String,
    val by: Enums.ByOption = Enums.ByOption.Text,
    val tag: String = "*",
    val exactText: Boolean = false,
    val index: Int = 1,
) {
    data class Builder(
        val driver: WebDriver,
        var string: String,
    ) {
        var by: Enums.ByOption = Enums.ByOption.Text
        var tag: String = "*"
        var exactText: Boolean = false
        var index: Int = 1

        fun string(string: String) = apply { this.string = string }
        fun by(by: Enums.ByOption) = apply { this.by = by }
        fun tag(tag: String) = apply { this.tag = tag }
        fun exactText(exactText: Boolean) = apply { this.exactText = exactText }
        fun index(index: Int) = apply { this.index = index }
        fun search() = ByStringBuilder(
            driver,
            string,
            by,
            tag,
            exactText,
            index
        ).search()
    }

    fun search() = Getter(
        driver,
        if (exactText)
            Tools.getElementContainingStringExact(string, by, tag, index)
        else
            Tools.getElementContainingString(string, by, tag, index)
    )
}