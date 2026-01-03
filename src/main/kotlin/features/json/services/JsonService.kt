package org.jack.features.json.services

interface JsonService {
    fun format(
        input: String,
        indent: Int = 4,
    ): String
}
