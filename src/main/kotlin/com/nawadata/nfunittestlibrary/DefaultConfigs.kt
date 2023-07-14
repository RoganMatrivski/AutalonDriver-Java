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
        defaultTimeout = Duration.ofSeconds(seconds)
        return this
    }
}