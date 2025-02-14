package com.matthewbrown.accounting_one_api.services

import com.matthewbrown.accounting_one_api.dtos.CreateAccountEntryCommand
import com.matthewbrown.accounting_one_api.dtos.UpdateAccountEntryCommand
import com.matthewbrown.accounting_one_api.entities.AccountEntry
import com.matthewbrown.accounting_one_api.exceptions.NotFoundException
import com.matthewbrown.accounting_one_api.repositories.AccountEntryRepository
import com.matthewbrown.accounting_one_api.repositories.AccountRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class AccountEntryService(
    private val accountEntryRepository: AccountEntryRepository,
    private val accountRepository: AccountRepository,
): IAccountEntryService {
    @Transactional
    override fun handle(command: CreateAccountEntryCommand): AccountEntry {
        accountRepository.findById(command.accountId).orElseThrow { NotFoundException("Account was not found") }
        return accountEntryRepository.save(
            AccountEntry(
                accountId = command.accountId,
                entryDate = command.entryDate,
                name = command.name,
                value = command.value
            )
        )
    }

    @Transactional
    override fun handle(command: UpdateAccountEntryCommand): AccountEntry {
        val accountEntry = accountEntryRepository.findById(command.id).orElseThrow { NotFoundException("Account Entry was not found") }
        accountRepository.findById(command.accountId).orElseThrow { NotFoundException("Account was not found") }
        accountEntry.accountId = command.accountId
        accountEntry.entryDate = command.entryDate
        accountEntry.name = command.name
        accountEntry.value = command.value
        return accountEntryRepository.save(accountEntry)
    }
}
