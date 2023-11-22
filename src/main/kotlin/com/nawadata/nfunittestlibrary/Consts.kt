package com.nawadata.nfunittestlibrary

import java.time.Duration

object Consts {
    @JvmStatic var lowerAlphabets = "abcdefghijklmnopqrstuvwxyz".toCharArray()
    @JvmStatic var upperAlphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray()
    @JvmStatic var numbers = "1234567890".toCharArray()
    @JvmStatic var symbols = "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~".toCharArray()
    @JvmStatic var defaultTimeout: Duration = Duration.ofSeconds(10)
    @JvmStatic var defaultPolling: Duration = Duration.ofMillis(100)
}