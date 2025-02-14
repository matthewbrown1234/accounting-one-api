package com.matthewbrown.accounting_one_api.validators

import com.matthewbrown.accounting_one_api.dtos.CreateAccountCommand
import com.matthewbrown.accounting_one_api.dtos.DeleteAccountCommand
import com.matthewbrown.accounting_one_api.dtos.UpdateAccountCommand
import com.matthewbrown.accounting_one_api.exceptions.ConflictValidationException
import com.matthewbrown.accounting_one_api.repositories.AccountEntryRepository
import com.matthewbrown.accounting_one_api.repositories.AccountRepository
import org.springframework.stereotype.Component

@Component
class AccountValidator(
    private val accountRepository: AccountRepository,
    private val accountEntryRepository: AccountEntryRepository
) : IAccountValidator {
    override fun validate(command: CreateAccountCommand) {
        val conflicts = mutableMapOf<String, String>()
        if (accountRepository.existsByAccountId(command.accountId)) {
            conflicts["accountId"] =
                "Account with accountId ${command.accountId} already exists"
        }
        if (accountRepository.existsByAccountName(command.accountName)) {
            conflicts["accountName"] =
                "Account with accountName ${command.accountName} already exists"
        }
        if (conflicts.isNotEmpty()) {
            throw ConflictValidationException(conflicts)
        }
    }

    override fun validate(command: UpdateAccountCommand) {
        val conflicts = mutableMapOf<String, String>()
        if (accountRepository.existsByAccountIdAndIdNot(
                command.accountId,
                command.id
            )
        ) {
            conflicts["accountId"] =
                "Account with accountId ${command.accountId} already exists"
        }
        if (accountRepository.existsByAccountNameAndIdNot(
                command.accountName,
                command.id
            )
        ) {
            conflicts["accountName"] =
                "Account with accountName ${command.accountName} already exists"
        }
        if (conflicts.isNotEmpty()) {
            throw ConflictValidationException(conflicts)
        }
    }

    override fun validate(command: DeleteAccountCommand) {
        if (accountEntryRepository.existsByAccountId(command.id)) {
            throw ConflictValidationException(
                mapOf("accountEntries" to "This account has account entries and cannot be deleted")
            )
        }
    }
}
