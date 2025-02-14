package com.matthewbrown.accounting_one_api.services

import com.matthewbrown.accounting_one_api.dtos.CreateAccountEntryCommand
import com.matthewbrown.accounting_one_api.dtos.UpdateAccountEntryCommand
import com.matthewbrown.accounting_one_api.entities.AccountEntry

interface IAccountEntryService {
    fun handle(command: CreateAccountEntryCommand): AccountEntry
    fun handle(command: UpdateAccountEntryCommand): AccountEntry
}
