package com.matthewbrown.accounting_one_api.entities

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.math.BigDecimal
import java.time.ZonedDateTime

@Entity
class AccountEntryView (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    var value: BigDecimal,
    var name: String,
    var entryDate: ZonedDateTime,
    var accountId: Long,
    var accountName: String
)
