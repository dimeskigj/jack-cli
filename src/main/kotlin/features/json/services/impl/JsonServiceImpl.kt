package org.jack.features.json.services.impl

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import org.jack.features.json.services.JsonService

class JsonServiceImpl : JsonService {
    private val lenientJson =
        Json {
            isLenient = true
            ignoreUnknownKeys = true
            allowTrailingComma = true
        }

    override fun parse(input: String): JsonElement =
        try {
            lenientJson.parseToJsonElement(input)
        } catch (e: Exception) {
            throw IllegalArgumentException("Invalid JSON input: ${e.message}")
        }

    @OptIn(ExperimentalSerializationApi::class)
    override fun format(
        element: JsonElement,
        indent: Int,
    ): String {
        val outputJson =
            Json {
                prettyPrint = true
                prettyPrintIndent = " ".repeat(indent)
            }
        return outputJson.encodeToString(JsonElement.serializer(), element)
    }

    override fun minify(element: JsonElement): String {
        val compactJson = Json { prettyPrint = false }
        return compactJson.encodeToString(JsonElement.serializer(), element)
    }

    override fun query(
        element: JsonElement,
        queryExpression: String,
    ): JsonElement {
        if (queryExpression == ".") return element
        val tokens = parseQueryTokens(queryExpression)
        return tokens.fold(element) { current, token -> resolveToken(current, token) }
    }

    private fun parseQueryTokens(query: String): List<String> {
        val tokens = mutableListOf<String>()
        val normalized = query.removePrefix(".")
        var i = 0

        while (i < normalized.length) {
            when {
                normalized[i] == '[' -> {
                    val end = normalized.indexOf(']', i)
                    if (end == -1) throw IllegalArgumentException("Invalid query: unclosed bracket")
                    tokens.add(normalized.substring(i, end + 1))
                    i = end + 1
                }
                normalized[i] == '.' -> {
                    i++
                }
                else -> {
                    val end = normalized.indexOfAny(charArrayOf('.', '['), i).takeIf { it != -1 } ?: normalized.length
                    tokens.add(normalized.substring(i, end))
                    i = end
                }
            }
        }
        return tokens
    }

    private fun resolveToken(
        element: JsonElement,
        token: String,
    ): JsonElement =
        when {
            token.startsWith("[") && token.endsWith("]") -> {
                val indexStr = token.removeSurrounding("[", "]")
                val index =
                    indexStr.toIntOrNull()
                        ?: throw IllegalArgumentException("Invalid array index: $indexStr")
                when (element) {
                    is JsonArray -> {
                        if (index < 0 || index >= element.size) {
                            throw IllegalArgumentException("Array index out of bounds: $index")
                        }
                        element[index]
                    }
                    else -> throw IllegalArgumentException("Cannot index non-array with [$index]")
                }
            }
            else -> {
                when (element) {
                    is JsonObject -> {
                        element[token]
                            ?: throw IllegalArgumentException("Key not found: $token")
                    }
                    else -> throw IllegalArgumentException("Cannot access key '$token' on non-object")
                }
            }
        }
}
