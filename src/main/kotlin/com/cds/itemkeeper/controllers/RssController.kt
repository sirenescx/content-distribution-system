package com.cds.itemkeeper.controllers

import com.cds.itemkeeper.services.RssService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/rss")
class RssController(private val rssService: RssService) {

    @PostMapping("/fetch")
    fun fetchRssData(@RequestParam feedUrl: String) {
        rssService.fetchDataFromRss(feedUrl)
    }

    @PostMapping("/getJson")
    fun getJsonFromRss(@RequestParam feedUrl: String): String? {
        return rssService.getJsonFromRss(feedUrl).toString()
    }
}