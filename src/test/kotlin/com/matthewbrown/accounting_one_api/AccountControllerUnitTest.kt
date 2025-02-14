package com.matthewbrown.accounting_one_api

import com.google.gson.Gson
import com.matthewbrown.accounting_one_api.dtos.CreateAccountRequest
import com.matthewbrown.accounting_one_api.dtos.UpdateAccountRequest
import com.matthewbrown.accounting_one_api.entities.Account
import io.mockk.every
import java.util.*
import kotlin.test.Test
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put

class AccountControllerUnitTest : BaseControllerUnitTest() {

    @Test
    fun `get accounts returns a pageable list of Accounts`() {
        val accounts = listOf(
            Account(id = 1, accountId = "1", accountName = "Account 1", accountType = "Asset"),
            Account(id = 2, accountId = "2", accountName = "Account 2", accountType = "Asset"),
        )
        val pageable = PageRequest.of(0, 20)
        val page = PageImpl(accounts, pageable, accounts.size.toLong())
        every { accountRepository.findAll(any<Pageable>()) } returns page
        mockMvc.get("/api/accounts")
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { contentType("application/json") }
                jsonPath("$.content[0].id") { value(1) }
                jsonPath("$.content[0].accountId") { value("1") }
                jsonPath("$.content[0].accountName") { value("Account 1") }
                jsonPath("$.content[0].accountType") { value("Asset") }
                jsonPath("$.content[1].id") { value(2) }
                jsonPath("$.content[1].accountId") { value("2") }
                jsonPath("$.content[1].accountName") { value("Account 2") }
                jsonPath("$.content[1].accountType") { value("Asset") }
            }
    }

    @Test
    fun `get account returns an Account if one is found`() {
        val account =
            Account(id = 1, accountId = "1", accountName = "Account 1", accountType = "Asset")
        every { accountRepository.findById(any<Long>()) } returns Optional.of(account)
        mockMvc.get("/api/accounts/1")
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { contentType("application/json") }
                jsonPath("$.id") { value(1) }
                jsonPath("$.accountId") { value("1") }
                jsonPath("$.accountName") { value("Account 1") }
                jsonPath("$.accountType") { value("Asset") }
            }
    }

    @Test
    fun `get account returns an 404 if nothing is found`() {
        every { accountRepository.findById(any<Long>()) } returns Optional.empty()
        mockMvc.get("/api/accounts/1")
            .andDo { print() }
            .andExpect {
                status { isNotFound() }
            }
    }

    @Test
    fun `post account returns a 409 if an account exists with the same accountId and with the same accountName`() {
        every { accountRepository.existsByAccountId(any<String>()) } returns true
        every { accountRepository.existsByAccountName(any<String>()) } returns true
        mockMvc.post("/api/accounts") {
            contentType = MediaType.APPLICATION_JSON
            content = Gson().toJson(
                CreateAccountRequest(
                    accountName = "Account 1",
                    accountId = "1",
                    accountType = "Asset"
                )
            )
        }
            .andDo { print() }
            .andExpect {
                status { isConflict() }
                jsonPath("$.fields.accountId") { value("Account with accountId 1 already exists") }
                jsonPath("$.fields.accountName") { value("Account with accountName Account 1 already exists") }
            }

    }

    @Test
    fun `post account returns an ok with an account if validation succeeds`() {
        every { accountRepository.existsByAccountId(any<String>()) } returns false
        every { accountRepository.existsByAccountName(any<String>()) } returns false
        every { accountRepository.save(any<Account>()) } answers { firstArg() }
        mockMvc.post("/api/accounts") {
            contentType = MediaType.APPLICATION_JSON
            content = Gson().toJson(
                CreateAccountRequest(
                    accountName = "Account 1",
                    accountId = "1",
                    accountType = "Asset"
                )
            )
        }
            .andDo { print() }
            .andExpect {
                status { isOk() }
                jsonPath("$.accountId") { value("1") }
                jsonPath("$.accountName") { value("Account 1") }
                jsonPath("$.accountType") { value("Asset") }
            }

    }

    @Test
    fun `put account returns an ok with an account if validation succeeds`() {
        every {
            accountRepository.existsByAccountIdAndIdNot(
                any<String>(),
                any<Long>()
            )
        } returns false
        every {
            accountRepository.existsByAccountNameAndIdNot(
                any<String>(),
                any<Long>()
            )
        } returns false
        val account =
            Account(id = 1, accountId = "1", accountName = "Account 1", accountType = "Asset")
        every { accountRepository.findById(any<Long>()) } answers { Optional.of(account) }
        every { accountRepository.save(any<Account>()) } answers { firstArg() }
        mockMvc.put("/api/accounts/2") {
            contentType = MediaType.APPLICATION_JSON
            content = Gson().toJson(
                UpdateAccountRequest(
                    accountName = "Account 1",
                    accountId = "1",
                    accountType = "Savings"
                )
            )
        }
            .andDo { print() }
            .andExpect {
                status { isOk() }
                jsonPath("$.accountId") { value("1") }
                jsonPath("$.accountName") { value("Account 1") }
                jsonPath("$.accountType") { value("Savings") }
            }

    }

    @Test
    fun `put account returns a 409 if an account exists with the same accountId and with the same accountName`() {
        every {
            accountRepository.existsByAccountIdAndIdNot(
                any<String>(),
                any<Long>()
            )
        } returns true
        every {
            accountRepository.existsByAccountNameAndIdNot(
                any<String>(),
                any<Long>()
            )
        } returns true
        mockMvc.put("/api/accounts/2") {
            contentType = MediaType.APPLICATION_JSON
            content = Gson().toJson(
                UpdateAccountRequest(
                    accountName = "Account 1",
                    accountId = "1",
                    accountType = "Asset"
                )
            )
        }
            .andDo { print() }
            .andExpect {
                status { isConflict() }
                jsonPath("$.fields.accountId") { value("Account with accountId 1 already exists") }
                jsonPath("$.fields.accountName") { value("Account with accountName Account 1 already exists") }
            }

    }

    @Test
    fun `put account returns a 404 if no account is found`() {
        every {
            accountRepository.existsByAccountIdAndIdNot(
                any<String>(),
                any<Long>()
            )
        } returns false
        every {
            accountRepository.existsByAccountNameAndIdNot(
                any<String>(),
                any<Long>()
            )
        } returns false
        every { accountRepository.findById(any<Long>()) } answers { Optional.empty() }
        mockMvc.put("/api/accounts/2") {
            contentType = MediaType.APPLICATION_JSON
            content = Gson().toJson(
                UpdateAccountRequest(
                    accountName = "Account 1",
                    accountId = "1",
                    accountType = "Asset"
                )
            )
        }
            .andDo { print() }
            .andExpect {
                status { isNotFound() }
            }

    }

    @Test
    fun `Delete account returns a 409 if there are account entries`() {
        every {
            accountEntryRepository.existsByAccountId(
                any<Long>()
            )
        } returns true
        every { accountRepository.deleteById(any<Long>()) } answers { }
        mockMvc.delete("/api/accounts/2")
            .andDo { print() }
            .andExpect {
                status { isConflict() }
            }
    }

    @Test
    fun `Delete account returns a 209 if success`() {
        every {
            accountEntryRepository.existsByAccountId(
                any<Long>()
            )
        } returns false
        every { accountRepository.deleteById(any<Long>()) } answers { }
        mockMvc.delete("/api/accounts/2")
            .andDo { print() }
            .andExpect {
                status { isNoContent() }
            }
    }
}
