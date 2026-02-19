package org.jack.features.base64.services.impl

import org.jack.features.base64.services.Base64Service
import java.util.Base64

class Base64ServiceImpl : Base64Service {
    override fun encode(input: String): String = Base64.getEncoder().encodeToString(input.toByteArray())

    override fun decode(input: String): String =
        try {
            String(Base64.getDecoder().decode(input))
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException("Invalid Base64 input", e)
        }
}
