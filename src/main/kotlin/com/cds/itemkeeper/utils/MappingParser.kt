package com.cds.itemkeeper.utils

import java.io.FileInputStream
import javax.json.Json
import javax.json.JsonObject


class MappingParser {
    companion object {
        fun parseMapping(): HashMap<String, HashMap<String, ArrayList<String>>> {
            val jsonReader = Json.createReader(FileInputStream("src/main/resources/mapping_configs.json"))
            val rawMapping = jsonReader.read().asJsonObject()
            return parseJsonObject(rawMapping)
        }

        private fun parseJsonObject(jsonObject: JsonObject): HashMap<String, HashMap<String, ArrayList<String>>> {
            val result = HashMap<String, HashMap<String, ArrayList<String>>>()
            for (outerKey in jsonObject.keys) {
                val outerValue = jsonObject.getJsonObject(outerKey)
                val innerMap = HashMap<String, ArrayList<String>>()
                for (innerKey in outerValue.keys) {
                    val innerValue = outerValue.getJsonObject(innerKey)
                    val arrayList = ArrayList<String>()
                    for (item in innerValue.getJsonArray("map")) {
                        if (item is JsonObject && item.containsKey("var")) {
                            arrayList.add("/" + item.getString("var").replace(".", "/"))
                        }
                    }
                    innerMap[innerKey] = arrayList
                }
                result[outerKey] = innerMap
            }
            return result
        }
    }
}