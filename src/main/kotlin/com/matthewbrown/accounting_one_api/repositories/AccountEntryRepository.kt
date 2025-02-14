package com.matthewbrown.accounting_one_api.repositories

import com.matthewbrown.accounting_one_api.entities.AccountEntry
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountEntryRepository : PagingAndSortingRepository<AccountEntry, Long>,
    CrudRepository<AccountEntry, Long> {
    fun findAllByAccountId(accountId: Long, pageable: Pageable): Page<AccountEntry>
    fun existsByAccountId(accountId: Long): Boolean
}
