package com.matthewbrown.accounting_one_api.dtos

import com.matthewbrown.accounting_one_api.entities.Account
import java.math.BigDecimal
import java.time.ZonedDateTime

data class CreateAccountEntryCommand (
    var value: BigDecimal,
    var name: String,
    var entryDate: ZonedDateTime,
    var accountId: Long
)
