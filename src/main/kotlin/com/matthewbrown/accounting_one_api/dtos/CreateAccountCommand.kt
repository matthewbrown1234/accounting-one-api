package com.matthewbrown.accounting_one_api.dtos

data class CreateAccountCommand (
    var accountId: String,
    var accountName: String,
    var accountType: String
)
