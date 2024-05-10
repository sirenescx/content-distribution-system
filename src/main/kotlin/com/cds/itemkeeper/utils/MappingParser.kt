package com.cds.itemkeeper.utils

import java.io.FileInputStream
import javax.json.Json
import javax.json.JsonObject
import kotlin.io.path.Path


class MappingParser {
    companion object {
        fun parseMapping(configurationFilename: String): Mapping {
            val inputStream = FileInputStream(
                Path("src/main/resources/mapping_configs", configurationFilename).toString()
            )
            val jsonReader = Json.createReader(inputStream)
            val rawMapping = jsonReader.read().asJsonObject()
            return parseJsonObject(rawMapping)
        }

        private fun parseJsonObject(jsonObject: JsonObject): Mapping {
            val root = jsonObject.getJsonObject("items")
            val rootExpression = getJsonPointerString(root.getString("expression"))
            val innerExpressions = mutableMapOf<String, List<String>>()
            for ((innerKey, innerValue) in root.getJsonObject("inner_mappings")) {
                val rawExpressions = innerValue.asJsonObject().getString("expression").split("[]")
                val expressions = mutableListOf<String>()
                for (expression in rawExpressions) {
                    expressions.add(getJsonPointerString(expression.trim('.')))
                }
                innerExpressions[innerKey] = expressions
            }
            return Mapping(rootExpression, innerExpressions)
        }

        private fun getJsonPointerString(mapping: String): String {
            val builder = StringBuilder()
            builder.append('/')
            builder.append(mapping.replace('.', '/'))
            return builder.toString()
        }
    }
}