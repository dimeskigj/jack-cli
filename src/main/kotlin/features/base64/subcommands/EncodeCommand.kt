package org.jack.features.base64.subcommands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.optional
import org.jack.features.base64.BASE64_ENCODE_COMMAND_NAME
import org.jack.features.base64.BASE64_ENCODE_HELP
import org.jack.features.base64.BASE64_ENCODE_INPUT_HELP
import org.jack.features.base64.BASE64_ERROR_NO_INPUT
import org.jack.features.base64.services.Base64Service

class EncodeCommand(
    private val base64Service: Base64Service,
) : CliktCommand(name = BASE64_ENCODE_COMMAND_NAME) {
    override fun help(context: Context) = BASE64_ENCODE_HELP

    private val input by argument(help = BASE64_ENCODE_INPUT_HELP).optional()

    override fun run() {
        val text =
            input ?: System.`in`
                .bufferedReader()
                .readText()
                .trim()
        if (text.isEmpty()) {
            echo(BASE64_ERROR_NO_INPUT, err = true)
            return
        }
        echo(base64Service.encode(text))
    }
}
