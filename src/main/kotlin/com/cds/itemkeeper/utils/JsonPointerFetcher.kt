package com.cds.itemkeeper.utils

import com.cds.itemkeeper.models.RssItem
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.json.Json
import javax.json.JsonObject
import javax.json.JsonStructure
import javax.json.JsonValue
import kotlin.reflect.KType
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.javaType

class JsonPointerFetcher {
    companion object {
        fun fetchDataFromRss(mapping: Mapping, rawJson: JsonStructure): List<String> {
            val rawItems = getJsonArray(mapping.rootExpression, rawJson)
            val innerExpressionsKeys = mapping.innerExpressions.keys
            val parsedFields = mutableMapOf<String, List<Any?>>()

            for (field in RssItem::class.memberProperties) {
                val fieldName = field.name
                if (innerExpressionsKeys.contains(fieldName)) {
                    val pointers = mapping.innerExpressions[fieldName]!!
                    parsedFields[fieldName] = fetchDataFromJsonUsingJsonPointers(pointers, rawItems).asJsonArray().map {
                        cast(it, field.returnType)
                    }
                }
            }

            return convertParsedDataToJsonList(parsedFields, rawItems.asJsonArray().size)
        }

        private fun convertParsedDataToJsonList(
            parsedFields: Map<String, List<Any?>>,
            objectsCount: Int
        ): List<String> {
            val gson = Gson()
            val jsons = mutableListOf<String>()

            for (index in 0..<objectsCount) {
                val json = mutableMapOf<String, Any>()
                for (key in parsedFields.keys) {
                    json[key] = parsedFields[key]!![index]!!
                }
                jsons.add(gson.toJson(json))
            }

            return jsons
        }

        private fun fetchDataFromJsonUsingJsonPointers(pointers: List<String>, json: JsonStructure): JsonValue {
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
            try {
                val typeName = returnType.javaType.typeName
                val type: Class<*> = if (typeName.contains("[]")) {
                    Class.forName("java.util.Arrays")
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
                if (Arrays::class.java.isAssignableFrom(type)) {
                    val subtype = Class.forName(extractClassName(typeName))
                    return if (value.valueType == JsonValue.ValueType.ARRAY) {
                        value.asJsonArray().map { subtype.cast(it.toString().trim('"')) }
                    } else {
                        arrayOf(subtype.cast(value.toString().trim('"')))
                    }
                }
            } catch (ex: Exception) {
                println()
            }
            return null
        }

        private fun extractClassName(genericType: String): String? {
            val pattern: Pattern = Pattern.compile("java\\.lang\\.(\\w+)\\[")
            val matcher: Matcher = pattern.matcher(genericType)
            return if (matcher.find()) {
                "java.lang." + matcher.group(1)
            } else {
                null
            }
        }
    }
}