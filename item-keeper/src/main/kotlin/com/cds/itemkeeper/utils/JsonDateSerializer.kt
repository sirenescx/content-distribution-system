package com.cds.itemkeeper.utils

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import org.springframework.stereotype.Component
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


@Component
class JsonDateSerializer : JsonSerializer<Date?>() {
    @Throws(IOException::class, JsonProcessingException::class)
    override fun serialize(value: Date?, gen: JsonGenerator?, serializers: SerializerProvider?) {
        gen!!.writeString(dateFormat.format(value))
    }

    companion object {
        private val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    }
}