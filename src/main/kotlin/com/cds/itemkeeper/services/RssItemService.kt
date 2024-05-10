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
import java.util.*
import javax.json.*


@Service
class RssItemService(private val rssItemRepository: RssItemRepository) {
    fun getItems(feedUrl: String, configurationFilename: String, sourceId: UUID) {
        val feed = XML.toJSONObject(XmlReader(URI(feedUrl).toURL())).toString().byteInputStream()
        val jsonReader = Json.createReader(feed)
        val jsonStructure = jsonReader.read()
        jsonReader.close()

        val mapping = MappingParser.parseMapping(configurationFilename)
        val rssItems = mutableListOf<RssItem>()
        for (json in JsonPointerFetcher.fetchDataFromRss(sourceId, mapping, jsonStructure)) {
            rssItems.add(Gson().fromJson(json, RssItem::class.java))
        }

        rssItemRepository.saveAll(rssItems)
    }

    fun getAllItems(): List<RssItem> {
        return rssItemRepository.findAll()
    }
}