package com.matthewbrown.accounting_one_api.dtos

import java.math.BigDecimal
import java.time.ZonedDateTime

data class UpdateAccountEntryCommand (
    var id: Long,
    var value: BigDecimal,
    var name: String,
    var entryDate: ZonedDateTime,
    var accountId: Long
)
