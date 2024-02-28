package com.cds.itemkeeper.services

import com.cds.itemkeeper.models.RssItem
import com.cds.itemkeeper.repositories.RssItemRepository
import com.cds.itemkeeper.utils.MappingParser
import com.rometools.rome.io.SyndFeedInput
import com.rometools.rome.io.XmlReader
import org.json.XML
import org.springframework.stereotype.Service
import java.net.URI
import javax.json.Json
import javax.json.JsonObject
import javax.json.JsonStructure
import javax.json.JsonValue


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

    fun getValueFromJsonUsingPointers(pointers: ArrayList<String>, json: JsonStructure): JsonValue? {
        var mutableJson = json
        for (pointer in pointers) {
            mutableJson = if (mutableJson.valueType == JsonValue.ValueType.ARRAY) {
                val jsonArrayBuilder = Json.createArrayBuilder()
                mutableJson.asJsonArray().map { it ->
                    try {
                        jsonArrayBuilder.add(Json.createPointer(pointer).getValue(it.asJsonObject()))
                    } catch (ex: Exception) {
                        jsonArrayBuilder.add(JsonObject.EMPTY_JSON_OBJECT)
                    }
                }
                jsonArrayBuilder.build()
            } else {
                try {
                    val value = Json.createPointer(pointer).getValue(mutableJson)
                    if (value.valueType == JsonValue.ValueType.OBJECT) {
                        value.asJsonObject()
                    } else {
                        value.asJsonArray()
                    }
                } catch (ex: Exception) {
                    JsonObject.EMPTY_JSON_OBJECT
                }
            }
        }
        return mutableJson
    }

    fun getJsonFromRss(feedUrl: String): JsonValue? {
        val feed = XML.toJSONObject(XmlReader(URI(feedUrl).toURL())).toString().byteInputStream()
        val jsonReader = Json.createReader(feed)
        val jsonStructure = jsonReader.read()
        jsonReader.close()

        val mapping = MappingParser.parseMapping()["rbc"]!!

        val x = jsonStructure.toString()
        return getValueFromJsonUsingPointers(mapping["tags"]!!, jsonStructure)
    }
}