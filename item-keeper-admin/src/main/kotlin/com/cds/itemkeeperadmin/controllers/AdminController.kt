package com.cds.itemkeeperadmin.controllers

import com.cds.itemkeeperadmin.models.RssItem
import com.cds.itemkeeperadmin.models.Source
import com.cds.itemkeeperadmin.services.ItemKeeperService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/admin")
class AdminController(private val itemKeeperService: ItemKeeperService) {
    @GetMapping("/getRssItems")
    fun getRssItems(): List<RssItem>? {
        return itemKeeperService.rssItems()
    }

    @GetMapping("/getRssItemsBySource")
    fun getRssItemsBySource(sourceId: String): List<RssItem>? {
        return itemKeeperService.rssItemsBySource(sourceId)
    }

    @GetMapping("/getSources")
    fun getSources(): List<Source?>? {
        return itemKeeperService.sources()
    }

    @GetMapping("/getSource")
    fun getSourceByName(name: String): Source {
        return itemKeeperService.source(name)
    }

    @PostMapping("/createSource")
    fun createSource(name: String, link: String, configurationFilename: String): Source? {
        return itemKeeperService.createSource(name, link, configurationFilename)
    }
}
