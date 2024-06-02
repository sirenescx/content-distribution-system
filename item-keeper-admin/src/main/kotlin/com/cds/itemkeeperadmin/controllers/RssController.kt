package com.cds.itemkeeperadmin.controllers

import com.cds.generated.inputs.RssItemInput
import com.cds.generated.inputs.SourceInput
import com.cds.itemkeeperadmin.models.RssItem
import com.cds.itemkeeperadmin.models.Source
import com.cds.itemkeeperadmin.services.ItemKeeperService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.util.*


@RestController
@RequestMapping("/rss")
class RssController(private val itemKeeperService: ItemKeeperService) {
    @GetMapping("/getItem")
    fun getRssItem(id: String): RssItem? {
        return itemKeeperService.getRssItemById(id)
    }

    @GetMapping("/getItems")
    fun getRssItems(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "100") size: Int
    ): List<RssItem>? {
        return itemKeeperService.getRssItems(page, size)
    }

    @GetMapping("/getItemsBySource")
    fun getRssItemsBySource(
        sourceId: String,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "100") size: Int
    ): List<RssItem>? {
        return itemKeeperService.getRssItemsBySourceId(sourceId, page, size)
    }

    @GetMapping("/getItemsByPublicationDate")
    fun getRssItemsByPublicationDate(
        @DateTimeFormat(pattern = "yyyy-MM-dd") publicationDate: Date,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "100") size: Int
    ): List<RssItem>? {
        return itemKeeperService.getRssItemsByPublicationDate(publicationDate, page, size)
    }

    @GetMapping("/getItemsBeforeDate")
    fun getRssItemsBeforeDate(
        @DateTimeFormat(pattern = "yyyy-MM-dd") date: Date,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "100") size: Int
    ): List<RssItem>? {
        return itemKeeperService.getRssItemsBeforeDate(date, page, size)
    }

    @GetMapping("/getItemsAfterDate")
    fun getRssItemsAfterDate(
        @DateTimeFormat(pattern = "yyyy-MM-dd") date: Date,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "100") size: Int
    ): List<RssItem>? {
        return itemKeeperService.getRssItemsAfterDate(date, page, size)
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

    @DeleteMapping("/updateSource")
    fun updateSource(id: String, sourceInput: SourceInput): Source? {
        return itemKeeperService.updateSource(id, sourceInput)
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
