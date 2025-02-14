package com.matthewbrown.accounting_one_api.validators

import com.matthewbrown.accounting_one_api.dtos.CreateAccountCommand
import com.matthewbrown.accounting_one_api.dtos.DeleteAccountCommand
import com.matthewbrown.accounting_one_api.dtos.UpdateAccountCommand

interface IAccountValidator {
    fun validate(command: CreateAccountCommand)
    fun validate(command: UpdateAccountCommand)
    fun validate(command: DeleteAccountCommand)
}
