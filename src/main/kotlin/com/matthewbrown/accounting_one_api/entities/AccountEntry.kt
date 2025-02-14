package com.matthewbrown.accounting_one_api.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.math.BigDecimal
import java.time.ZonedDateTime

@Entity
class AccountEntry (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(nullable = false)
    var value: BigDecimal,
    @Column(nullable = false)
    var name: String,
    @Column(nullable = false)
    var entryDate: ZonedDateTime,
    var accountId: Long
)
