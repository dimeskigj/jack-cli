package org.jack.features.json.services.impl

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import org.jack.features.json.services.JsonService

class JsonServiceImpl : JsonService {
    @OptIn(ExperimentalSerializationApi::class)
    override fun format(
        input: String,
        indent: Int,
    ): String {
        val json =
            Json {
                prettyPrint = true
                prettyPrintIndent = " ".repeat(indent)
                isLenient = true
                ignoreUnknownKeys = true
                allowTrailingComma = true
            }
        return try {
            val jsonElement = json.parseToJsonElement(input)
            json.encodeToString(JsonElement.serializer(), jsonElement)
        } catch (e: Exception) {
            throw IllegalArgumentException("Invalid JSON input: ${e.message}")
        }
    }
}
