package com.matthewbrown.accounting_one_api.services

import com.matthewbrown.accounting_one_api.dtos.CreateAccountCommand
import com.matthewbrown.accounting_one_api.dtos.DeleteAccountCommand
import com.matthewbrown.accounting_one_api.dtos.UpdateAccountCommand
import com.matthewbrown.accounting_one_api.entities.Account
import com.matthewbrown.accounting_one_api.exceptions.NotFoundException
import com.matthewbrown.accounting_one_api.repositories.AccountRepository
import com.matthewbrown.accounting_one_api.validators.IAccountValidator
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class AccountService(
    private val accountRepository: AccountRepository,
    private val accountValidator: IAccountValidator
): IAccountService {
    @Transactional
    override fun handle(command: CreateAccountCommand): Account {
        accountValidator.validate(command)
        return accountRepository.save(
            Account(
                accountId = command.accountId,
                accountName = command.accountName,
                accountType = command.accountType
            )
        )
    }
    @Transactional
    override fun handle(command: UpdateAccountCommand): Account {
        accountValidator.validate(command)
        val account = accountRepository.findById(command.id).orElseThrow {
            NotFoundException("Account was not found")
        }
        account.accountId = command.accountId
        account.accountName = command.accountName
        account.accountType = command.accountType

        return accountRepository.save(account)
    }

    override fun handle(command: DeleteAccountCommand) {
        accountValidator.validate(command)
        accountRepository.deleteById(command.id)
    }
}
