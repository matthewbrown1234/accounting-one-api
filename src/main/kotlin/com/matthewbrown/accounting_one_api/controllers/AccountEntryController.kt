package com.matthewbrown.accounting_one_api.controllers

import com.matthewbrown.accounting_one_api.dtos.CreateAccountEntryCommand
import com.matthewbrown.accounting_one_api.dtos.CreateAccountEntryRequest
import com.matthewbrown.accounting_one_api.dtos.UpdateAccountEntryCommand
import com.matthewbrown.accounting_one_api.dtos.UpdateAccountEntryRequest
import com.matthewbrown.accounting_one_api.entities.AccountEntry
import com.matthewbrown.accounting_one_api.entities.AccountEntryView
import com.matthewbrown.accounting_one_api.repositories.AccountEntryRepository
import com.matthewbrown.accounting_one_api.repositories.AccountEntryViewRepository
import com.matthewbrown.accounting_one_api.services.AccountEntryService
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
class AccountEntryController(
    private val accountEntryRepository: AccountEntryRepository,
    private val accountEntryService: AccountEntryService,
    private val accountEntryViewRepository: AccountEntryViewRepository,
) {
    @GetMapping("/account-entries")
    fun getAllAccountEntries(
        @PageableDefault(
            size = 20,
            sort = ["entryDate"],
            direction = Sort.Direction.ASC,
            page = 0
        ) pageable: Pageable
    ): HttpEntity<Page<AccountEntryView>> {
        return ResponseEntity.ok(accountEntryViewRepository.findAll(pageable))
    }
    @GetMapping("/accounts/{accountId}/account-entries")
    fun getAccountEntriesByAccountId(
        @PathVariable accountId: Long,
        @PageableDefault(
            size = 20,
            sort = ["entryDate"],
            direction = Sort.Direction.ASC,
            page = 0
        ) pageable: Pageable
    ): HttpEntity<Page<AccountEntry>> {
        return ResponseEntity.ok(accountEntryRepository.findAllByAccountId(accountId, pageable))
    }
    @PostMapping("/accounts/{accountId}/account-entries")
    fun createAccountEntry(
        @PathVariable accountId: Long,
        @Valid @RequestBody createAccountEntryRequest: CreateAccountEntryRequest
    ): HttpEntity<AccountEntry> {
        return ResponseEntity.ok(accountEntryService.handle(
            CreateAccountEntryCommand(
                accountId = accountId,
                entryDate = createAccountEntryRequest.entryDate,
                name = createAccountEntryRequest.name,
                value = createAccountEntryRequest.value
            )
        ))
    }
    @PutMapping("/accounts/{accountId}/account-entries/{id}")
    fun updateAccountEntry(
        @PathVariable accountId: Long,
        @PathVariable id: Long,
        @Valid @RequestBody updateAccountEntryRequest: UpdateAccountEntryRequest
    ): HttpEntity<AccountEntry> {
        return ResponseEntity.ok(accountEntryService.handle(
            UpdateAccountEntryCommand(
                id = id,
                accountId = accountId,
                entryDate = updateAccountEntryRequest.entryDate,
                name = updateAccountEntryRequest.name,
                value = updateAccountEntryRequest.value
            )
        ))
    }
    @DeleteMapping("/account-entries/{id}")
    fun deleteAccountEntry(
        @PathVariable id: Long
    ): HttpEntity<Void> {
        accountEntryRepository.deleteById(id)
        return ResponseEntity.noContent().build()
    }
}
