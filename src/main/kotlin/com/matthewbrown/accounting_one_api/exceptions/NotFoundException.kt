package com.matthewbrown.accounting_one_api.exceptions

class NotFoundException: RuntimeException {
    constructor(message: String): super(message)
    constructor(message: String, cause: Throwable): super(message, cause)
    constructor(cause: Throwable): super(cause)
}
