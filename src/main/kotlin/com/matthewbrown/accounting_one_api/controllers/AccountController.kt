package com.matthewbrown.accounting_one_api.controllers

import com.matthewbrown.accounting_one_api.dtos.CreateAccountCommand
import com.matthewbrown.accounting_one_api.dtos.CreateAccountRequest
import com.matthewbrown.accounting_one_api.dtos.DeleteAccountCommand
import com.matthewbrown.accounting_one_api.dtos.UpdateAccountCommand
import com.matthewbrown.accounting_one_api.dtos.UpdateAccountRequest
import com.matthewbrown.accounting_one_api.entities.Account
import com.matthewbrown.accounting_one_api.exceptions.NotFoundException
import com.matthewbrown.accounting_one_api.repositories.AccountRepository
import com.matthewbrown.accounting_one_api.services.IAccountService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api")
class AccountController(
    private val accountRepository: AccountRepository,
    private val accountService: IAccountService
) {
    @GetMapping("/accounts")
    fun getAccounts(
        @PageableDefault(
            size = 20,
            sort = ["accountName"],
            direction = Sort.Direction.ASC,
            page = 0
        ) pageable: Pageable
    ): HttpEntity<Page<Account>> = ResponseEntity.ok(accountRepository.findAll(pageable))

    @PostMapping("/accounts")
    fun createAccount(
        @Valid
        @RequestBody account: CreateAccountRequest
    ): HttpEntity<Account> = ResponseEntity.ok(
        accountService.handle(
            CreateAccountCommand(
                accountId = account.accountId,
                accountName = account.accountName,
                accountType = account.accountType
            )
        )
    )

    @PutMapping("/accounts/{id}")
    fun updateAccount(
        @PathVariable id: Long,
        @Valid
        @RequestBody account: UpdateAccountRequest
    ): HttpEntity<Account> = ResponseEntity.ok(
        accountService.handle(
            UpdateAccountCommand(
                id = id,
                accountId = account.accountId,
                accountName = account.accountName,
                accountType = account.accountType
            )
        )
    )

    @GetMapping("/accounts/{id}")
    fun getAccount(
        @PathVariable id: Long
    ): HttpEntity<Account> {
        val account = accountRepository.findById(id)
            .orElseThrow { NotFoundException("Account was not found") }
        return ResponseEntity.ok(account)
    }

    @DeleteMapping("/accounts/{id}")
    fun deleteAccount(
        @PathVariable id: Long
    ): HttpEntity<Unit> {
        accountService.handle(DeleteAccountCommand(id))
        return ResponseEntity.noContent().build()
    }
}
