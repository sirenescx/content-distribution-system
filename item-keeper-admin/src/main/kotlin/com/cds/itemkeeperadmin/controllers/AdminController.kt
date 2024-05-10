package com.cds.itemkeeperadmin.controllers

import com.cds.generated.allrssitems.RssItem
import com.cds.itemkeeperadmin.clients.ItemKeeperClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin")
class AdminController(private val itemKeeperClient: ItemKeeperClient) {
    @GetMapping("/getRssItems")
    fun getRssItems(): List<RssItem?>? {
        return itemKeeperClient.allRssItems()
    }
}
