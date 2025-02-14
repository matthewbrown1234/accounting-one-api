package com.matthewbrown.accounting_one_api.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.NotBlank

@Entity
class Account (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @NotBlank
    @Column(nullable = false)
    var accountId: String,
    @Column(nullable = false)
    var accountName: String,
    @Column(nullable = false)
    var accountType: String
)
