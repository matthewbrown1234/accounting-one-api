package com.matthewbrown.accounting_one_api.dtos

data class UpdateAccountCommand (
    var id: Long,
    var accountId: String,
    var accountName: String,
    var accountType: String
)

