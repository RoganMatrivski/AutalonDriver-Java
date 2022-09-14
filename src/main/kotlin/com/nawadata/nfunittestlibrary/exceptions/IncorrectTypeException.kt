package com.nawadata.nfunittestlibrary.exceptions

class IncorrectTypeException : Exception {
    constructor() : super()
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(cause: Throwable?) : super(cause)
    companion object {
        private const val serialVersionUID = -7268745837041516685L
    }
}
