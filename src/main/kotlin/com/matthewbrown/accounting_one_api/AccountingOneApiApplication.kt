package com.matthewbrown.accounting_one_api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.web.config.EnableSpringDataWebSupport

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
class AccountingOneApiApplication

fun main(args: Array<String>) {
	runApplication<AccountingOneApiApplication>(*args)
}
