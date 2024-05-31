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
import java.sql.Timestamp
import java.util.*
import javax.json.*


@Service
class RssItemService(private val rssItemRepository: RssItemRepository) {
    fun saveParsedItems(feedUrl: String, configurationFilename: String, sourceId: UUID) {
        try {
            val feed = XML.toJSONObject(XmlReader(URI(feedUrl).toURL())).toString().byteInputStream()
            val jsonReader = Json.createReader(feed)
            val jsonStructure = jsonReader.read()
            jsonReader.close()

            val mapping = MappingParser.parseMapping(configurationFilename)
            val rssItems = mutableListOf<RssItem>()
            for (json in JsonPointerFetcher.fetchDataFromRss(sourceId, mapping, jsonStructure)) {
                rssItems.add(Gson().fromJson(json, RssItem::class.java))
            }

            saveWithoutDuplicates(rssItems)
        } catch (e: java.io.IOException) {
            e.printStackTrace()
        } catch (e: org.json.JSONException) {
            e.printStackTrace()
        }
    }

    fun saveWithoutDuplicates(rssItems: List<RssItem>) {
        for (rssItem in rssItems) {
            val existingRssItem = rssItemRepository.findByGuid(rssItem.guid)
            if (existingRssItem == null) {
                rssItemRepository.save(rssItem)
            } else {
                existingRssItem.updatedAt = Timestamp(System.currentTimeMillis())
                rssItemRepository.save(existingRssItem)
            }
        }
    }
}