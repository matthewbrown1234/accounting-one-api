package com.matthewbrown.accounting_one_api.repositories

import com.matthewbrown.accounting_one_api.entities.Account
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository: PagingAndSortingRepository<Account, Long>, CrudRepository<Account, Long> {
    fun existsByAccountName(accountName: String): Boolean
    fun existsByAccountNameAndIdNot(accountName: String, id: Long): Boolean
    fun existsByAccountId(accountId: String): Boolean
    fun existsByAccountIdAndIdNot(accountId: String, id: Long): Boolean
}
