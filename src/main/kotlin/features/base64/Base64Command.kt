package org.jack.features.base64

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context
import org.jack.features.base64.services.Base64Service

const val BASE64_COMMAND_NAME = "base64"
const val BASE64_HELP_DESC = "Base64 encode/decode utilities"

const val BASE64_ENCODE_COMMAND_NAME = "encode"
const val BASE64_ENCODE_HELP = "Encode string to Base64"
const val BASE64_ENCODE_INPUT_HELP = "Input string"

const val BASE64_DECODE_COMMAND_NAME = "decode"
const val BASE64_DECODE_HELP = "Decode Base64 string"
const val BASE64_DECODE_INPUT_HELP = "Base64 string"

const val BASE64_ERROR_NO_INPUT = "Error: No input provided"
const val BASE64_DECODE_ERROR_PREFIX = "Error: "

class Base64Command(
    private val base64Service: Base64Service,
) : CliktCommand(name = BASE64_COMMAND_NAME) {
    override fun help(context: Context) = BASE64_HELP_DESC

    override fun run() = Unit
}
