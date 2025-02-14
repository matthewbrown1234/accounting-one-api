package com.matthewbrown.accounting_one_api.services

import com.matthewbrown.accounting_one_api.dtos.CreateAccountCommand
import com.matthewbrown.accounting_one_api.dtos.DeleteAccountCommand
import com.matthewbrown.accounting_one_api.dtos.UpdateAccountCommand
import com.matthewbrown.accounting_one_api.entities.Account

interface IAccountService {
    fun handle(command: CreateAccountCommand): Account
    fun handle(command: UpdateAccountCommand): Account
    fun handle(command: DeleteAccountCommand)

}
