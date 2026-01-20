package org.jack.features.json.services

import kotlinx.serialization.json.JsonElement

interface JsonService {
    fun parse(input: String): JsonElement

    fun format(
        element: JsonElement,
        indent: Int = 4,
    ): String

    fun minify(element: JsonElement): String

    fun query(
        element: JsonElement,
        queryExpression: String,
    ): JsonElement
}
