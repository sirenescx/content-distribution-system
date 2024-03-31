package com.cds.itemkeeper.controllers

import com.cds.itemkeeper.models.RssItem
import com.cds.itemkeeper.services.RssService
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/rss")
class RssController(private val rssService: RssService) {
    @PostMapping("/getJson")
    fun getJsonFromRss(@RequestParam feedUrl: String) {
        rssService.getJsonFromRss(feedUrl)
    }

    @GetMapping("/getAllItems")
    fun getJsonFromRss(): List<RssItem> {
        return rssService.getAllItems()
    }
}