package com.nawadata.nfunittestlibrary

import java.time.Duration

class DefaultConfigs (
    var defaultTimeout: Duration = Consts.defaultTimeout,
    var defaultPolling: Duration = Consts.defaultPolling,
) {
    fun setDefaultTimeoutOfSeconds(seconds: Long): DefaultConfigs {
        this.defaultTimeout = Duration.ofSeconds(seconds)
        return this
    }
}