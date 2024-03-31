package com.cds.itemkeeper.services

import com.google.gson.Gson;
import com.cds.itemkeeper.models.RssItem
import com.cds.itemkeeper.repositories.RssItemRepository
import com.cds.itemkeeper.utils.JsonPointerFetcher
import com.cds.itemkeeper.utils.MappingParser
import com.rometools.rome.io.XmlReader
import org.json.XML
import org.springframework.stereotype.Service
import java.net.URI
import javax.json.*


@Service
class RssService(private val rssItemRepository: RssItemRepository) {
    fun getJsonFromRss(feedUrl: String) {
        val feed = XML.toJSONObject(XmlReader(URI(feedUrl).toURL())).toString().byteInputStream()
        val jsonReader = Json.createReader(feed)
        val jsonStructure = jsonReader.read()
        jsonReader.close()

        val mapping = MappingParser.parseMapping()
        val rssItems = mutableListOf<RssItem>()
        for (json in JsonPointerFetcher.fetchDataFromRss(mapping, jsonStructure)) {
            rssItems.add(Gson().fromJson(json, RssItem::class.java))
        }

        rssItemRepository.saveAll(rssItems)
    }

    fun getAllItems(): List<RssItem> {
        return rssItemRepository.findAll()
    }
}