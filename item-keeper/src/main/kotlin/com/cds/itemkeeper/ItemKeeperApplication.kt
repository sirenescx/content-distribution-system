package com.cds.itemkeeper

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ItemKeeperApplication

fun main(args: Array<String>) {
	runApplication<ItemKeeperApplication>(*args)
}
