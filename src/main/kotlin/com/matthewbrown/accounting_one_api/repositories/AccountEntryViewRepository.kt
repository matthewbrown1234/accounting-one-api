package com.matthewbrown.accounting_one_api.repositories

import com.matthewbrown.accounting_one_api.entities.AccountEntryView
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountEntryViewRepository : PagingAndSortingRepository<AccountEntryView, Long> {
}
