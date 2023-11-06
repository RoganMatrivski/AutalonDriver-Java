package com.nawadata.nfunittestlibrary

import java.time.Duration

class DefaultConfigs (
    var defaultTimeout: Duration = Consts.defaultTimeout
) {
    fun setDefaultTimeout(duration: Duration): DefaultConfigs {
        defaultTimeout = duration
        return this
    }

    fun setDefaultTimeoutOfSeconds(seconds: Long): DefaultConfigs {
        return this.setDefaultTimeout(Duration.ofSeconds(seconds))
    }
}