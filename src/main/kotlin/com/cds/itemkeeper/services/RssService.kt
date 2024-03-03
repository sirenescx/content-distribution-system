package com.cds.itemkeeper.services

import com.google.gson.Gson;
import com.cds.itemkeeper.models.RssItem
import com.cds.itemkeeper.repositories.RssItemRepository
import com.cds.itemkeeper.utils.Mapping
import com.cds.itemkeeper.utils.MappingParser
import com.rometools.rome.io.XmlReader
import org.json.XML
import org.springframework.stereotype.Service
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.json.*
import kotlin.reflect.KType
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.javaType


@Service
class RssService(private val rssItemRepository: RssItemRepository) {
    fun getJsonFromRss(feedUrl: String): JsonValue? {
        val feed = XML.toJSONObject(XmlReader(URI(feedUrl).toURL())).toString().byteInputStream()
        val jsonReader = Json.createReader(feed)
        val jsonStructure = jsonReader.read()
        jsonReader.close()

        val mapping = MappingParser.parseMapping()
        val rssItems = mutableListOf<RssItem>()
        for (json in convertRssToListOfJSONs(mapping, jsonStructure)) {
            rssItems.add(Gson().fromJson(json, RssItem::class.java))
        }

        rssItemRepository.saveAll(rssItems)
        return null
    }

    private fun getValueFromJsonUsingPointers(pointers: List<String>, json: JsonStructure): JsonValue {
        var mutableJson = json
        for (pointer in pointers) {
            mutableJson = if (mutableJson.valueType == JsonValue.ValueType.ARRAY) {
                val jsonArrayBuilder = Json.createArrayBuilder()
                mutableJson.asJsonArray().map {
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

    private fun getJsonArray(pointer: String, json: JsonStructure): JsonStructure {
        return Json.createPointer(pointer).getValue(json).asJsonArray()
    }

    private fun cast(value: JsonValue, returnType: KType): Any? {
        val typeName = returnType.javaType.typeName
        val type: Class<*> = if (typeName.contains("List")) {
            Class.forName("java.util.List")
        } else {
            Class.forName(returnType.javaType.typeName)
        }

        if (String::class.java.isAssignableFrom(type)) {
            return type.cast(value.toString().trim('"'))
        }
        if (Date::class.java.isAssignableFrom(type)) {
            val dateFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z")
            val utcTimeZone = TimeZone.getTimeZone("UTC")
            dateFormat.timeZone = utcTimeZone
            return dateFormat.parse(value.toString().trim('"'))
        }
        if (List::class.java.isAssignableFrom(type)) {
            val subtype = Class.forName(extractClassName(typeName))
            return if (value.valueType == JsonValue.ValueType.ARRAY) {
                value.asJsonArray().map { subtype.cast(it.toString().trim('"')) }
            } else {
                listOf(subtype.cast(value.toString().trim('"')))
            }
        }
        return null
    }

    private fun List<List<Any?>>.transpose(action: (List<Any?>) -> RssItem): List<RssItem> =
        if (isEmpty() || this[0].isEmpty()) emptyList() else map { action.invoke(it) }

    private fun extractClassName(genericType: String): String? {
        val pattern: Pattern = Pattern.compile("<([^>]+)>")
        val matcher: Matcher = pattern.matcher(genericType)
        return if (matcher.find()) {
            matcher.group(1)
        } else {
            null
        }
    }

    private fun convertRssToListOfJSONs(mapping: Mapping, rawJson: JsonStructure): List<String> {
        val rawItems = getJsonArray(mapping.rootExpression, rawJson)
        val innerExpressionsKeys = mapping.innerExpressions.keys
        val parsedFields = mutableMapOf<String, List<Any?>>()

        for (field in RssItem::class.memberProperties) {
            val fieldName = field.name
            if (innerExpressionsKeys.contains(fieldName)) {
                val pointers = mapping.innerExpressions[fieldName]!!
                parsedFields[fieldName] = getValueFromJsonUsingPointers(pointers, rawItems).asJsonArray().map {
                    cast(it, field.returnType)
                }
            }
        }

        val jsons = mutableListOf<String>()
        val gson = Gson()
        for (index in 0..<rawItems.asJsonArray().size) {
            val json = mutableMapOf<String, Any>()
            for (key in parsedFields.keys) {
                json[key] = parsedFields[key]!![index]!!
            }
            jsons.add(gson.toJson(json))
        }

        return jsons
    }
}