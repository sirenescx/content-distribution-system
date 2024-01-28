package com.cds.itemkeeper.services

import com.cds.itemkeeper.models.RssItem
import com.cds.itemkeeper.repositories.RssItemRepository
import com.rometools.rome.io.SyndFeedInput
import com.rometools.rome.io.XmlReader
import org.springframework.stereotype.Service
import java.net.URI

@Service
class RssService(private val rssItemRepository: RssItemRepository) {

    fun fetchDataFromRss(feedUrl: String) {
        val feed = SyndFeedInput().build(XmlReader(URI(feedUrl).toURL()))
        val rssItems = feed.entries.map {
            RssItem(
                title = it.title, link = it.link, description = it.description.value
            )
        }
        rssItemRepository.saveAll(rssItems)
    }
}