package com.cds.itemkeeperadmin.controllers

import com.cds.generated.inputs.RssItemInput
import com.cds.itemkeeperadmin.models.RssItem
import com.cds.itemkeeperadmin.models.Source
import com.cds.itemkeeperadmin.services.ItemKeeperService
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/rss")
class RssController(private val itemKeeperService: ItemKeeperService) {
    @GetMapping("/getItem")
    fun getRssItem(id: String): RssItem? {
        return itemKeeperService.getRssItemById(id)
    }

    @GetMapping("/getItems")
    fun getRssItems(): List<RssItem>? {
        return itemKeeperService.getRssItems()
    }

    @GetMapping("/getItemsBySource")
    fun getRssItemsBySource(sourceId: String): List<RssItem>? {
        return itemKeeperService.getRssItemsBySourceId(sourceId)
    }

    @GetMapping("/getItemsByPublicationDate")
    fun getRssItemsByPublicationDate(publicationDate: Date): List<RssItem>? {
        return itemKeeperService.getRssItemsByPublicationDate(publicationDate)
    }

    @GetMapping("/getItemsBeforeDate")
    fun getRssItemsBeforeDate(date: Date): List<RssItem>? {
        return itemKeeperService.getRssItemsBeforeDate(date)
    }

    @GetMapping("/getItemsAfterDate")
    fun getRssItemsAfterDate(date: Date): List<RssItem>? {
        return itemKeeperService.getRssItemsAfterDate(date)
    }

    @PostMapping("/updateRssItem")
    fun updateRssItem(id: String, rssItemInput: RssItemInput): RssItem? {
        return itemKeeperService.updateRssItem(id, rssItemInput)
    }

    @DeleteMapping("/deleteRssItem")
    fun deleteRssItem(id: String): RssItem? {
        return itemKeeperService.deleteRssItem(id)
    }

    @GetMapping("/getSource")
    fun getSourceById(id: String): Source {
        return itemKeeperService.getSourceById(id)
    }

    @GetMapping("/getSourceByName")
    fun getSourceByName(name: String): Source {
        return itemKeeperService.getSourceByName(name)
    }

    @GetMapping("/getSources")
    fun getSources(): List<Source?>? {
        return itemKeeperService.getSources()
    }

    @GetMapping("/getBannedSources")
    fun getBannedSources(): List<Source?>? {
        return itemKeeperService.getBannedSources()
    }

    @PostMapping("/createSource")
    fun createSource(name: String, link: String, configurationFilename: String): Source? {
        return itemKeeperService.createSource(name, link, configurationFilename)
    }

    @DeleteMapping("/deleteSource")
    fun deleteSource(id: String): Source? {
        return itemKeeperService.deleteSource(id)
    }

    @PostMapping("/banSource")
    fun banSource(id: String): Source? {
        return itemKeeperService.banSource(id)
    }

    @PostMapping("/unbanSource")
    fun unbanSource(id: String): Source? {
        return itemKeeperService.unbanSource(id)
    }
}
