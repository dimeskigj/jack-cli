package org.jack.features.jwt.services.impl

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.jack.features.jwt.services.JwtService
import java.util.Base64
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class JwtServiceImpl : JwtService {
    private val json = Json { prettyPrint = true }
    private val decoder = Base64.getUrlDecoder()
    private val encoder = Base64.getUrlEncoder().withoutPadding()

    override fun decode(
        token: String,
        secret: String?,
    ): String {
        val parts = token.split(".")
        if (parts.size < 2) {
            throw IllegalArgumentException("Invalid JWT format. Expected at least 2 parts separated by dots.")
        }

        val headerRaw = parts[0]
        val payloadRaw = parts[1]
        val signatureRaw = if (parts.size > 2) parts[2] else null

        val headerDecoded = String(decoder.decode(headerRaw))
        val payloadDecoded = String(decoder.decode(payloadRaw))

        val headerPretty =
            try {
                val jsonElement = json.parseToJsonElement(headerDecoded)
                json.encodeToString(JsonObject.serializer(), jsonElement as JsonObject)
            } catch (e: Exception) {
                headerDecoded
            }

        val payloadPretty =
            try {
                val jsonElement = json.parseToJsonElement(payloadDecoded)
                json.encodeToString(JsonObject.serializer(), jsonElement as JsonObject)
            } catch (e: Exception) {
                payloadDecoded
            }

        val alg =
            try {
                val jsonElement = json.parseToJsonElement(headerDecoded) as? JsonObject
                jsonElement?.get("alg")?.jsonPrimitive?.content
            } catch (e: Exception) {
                null
            }

        return buildString {
            appendLine("--- HEADER ---")
            appendLine(headerPretty)
            appendLine()
            appendLine("--- PAYLOAD ---")
            appendLine(payloadPretty)

            if (signatureRaw != null) {
                appendLine()
                appendLine("--- SIGNATURE ---")
                if (secret != null) {
                    if (alg == "HS256") {
                        val isValid = verifyHS256(headerRaw, payloadRaw, signatureRaw, secret)
                        if (isValid) {
                            appendLine("Status: Valid")
                        } else {
                            appendLine("Status: Invalid")
                        }
                    } else {
                        appendLine("Status: [Protected] (Verification only supported for HS256)")
                    }
                } else {
                    appendLine("Status: [Protected]")
                }
            }
        }
    }

    private fun verifyHS256(
        header: String,
        payload: String,
        signature: String,
        secret: String,
    ): Boolean =
        try {
            val data = "$header.$payload"
            val hmac = Mac.getInstance("HmacSHA256")
            val secretKey = SecretKeySpec(secret.toByteArray(), "HmacSHA256")
            hmac.init(secretKey)
            val res = hmac.doFinal(data.toByteArray())
            val expectedSignature = encoder.encodeToString(res)
            expectedSignature == signature
        } catch (e: Exception) {
            false
        }
}
