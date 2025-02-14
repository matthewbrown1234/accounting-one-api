package com.matthewbrown.accounting_one_api.dtos

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class UpdateAccountRequest (
    @field:NotBlank(message = "Account Id is required")
    @field:Pattern(regexp = "^[0-9-]+$", message = "Account ID must only contain numbers and dashes")
    var accountId: String,
    @field:NotBlank(message = "Account Name is required")
    var accountName: String,
    @field:NotBlank(message = "Account Type is required")
    var accountType: String
)

