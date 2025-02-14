package com.matthewbrown.accounting_one_api.exceptions

class ConflictValidationException(
    val errors: Map<String, String>
) : RuntimeException("Validation failed for conficting data")
