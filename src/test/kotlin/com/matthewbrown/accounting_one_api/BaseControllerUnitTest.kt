package com.matthewbrown.accounting_one_api

import com.matthewbrown.accounting_one_api.repositories.AccountEntryRepository
import com.matthewbrown.accounting_one_api.repositories.AccountEntryViewRepository
import com.matthewbrown.accounting_one_api.repositories.AccountRepository
import com.ninjasquad.springmockk.MockkBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = [AccountingOneApiApplication::class]
)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = [DataSourceAutoConfiguration::class])
class BaseControllerUnitTest {
    @Autowired
    lateinit var mockMvc: MockMvc
    @MockkBean
    lateinit var accountRepository: AccountRepository
    @MockkBean
    lateinit var accountEntryRepository: AccountEntryRepository
    @MockkBean
    lateinit var accountEntryViewRepository: AccountEntryViewRepository
}
