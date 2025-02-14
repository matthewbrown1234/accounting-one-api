package com.matthewbrown.accounting_one_api.dtos

import jakarta.validation.constraints.NotBlank
import java.math.BigDecimal
import java.time.ZonedDateTime

data class UpdateAccountEntryRequest (
    var value: BigDecimal,
    @field:NotBlank(message = "Name is required")
    var name: String,
    var entryDate: ZonedDateTime
)
