package org.jack.features.base64.services

interface Base64Service {
    fun encode(input: String): String

    fun decode(input: String): String
}
