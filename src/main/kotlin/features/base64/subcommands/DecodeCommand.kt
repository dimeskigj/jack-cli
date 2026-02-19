package org.jack.features.base64.subcommands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.optional
import org.jack.features.base64.BASE64_DECODE_COMMAND_NAME
import org.jack.features.base64.BASE64_DECODE_HELP
import org.jack.features.base64.BASE64_DECODE_INPUT_HELP
import org.jack.features.base64.BASE64_ERROR_NO_INPUT
import org.jack.features.base64.services.Base64Service

class DecodeCommand(
    private val base64Service: Base64Service,
) : CliktCommand(name = BASE64_DECODE_COMMAND_NAME) {
    override fun help(context: Context) = BASE64_DECODE_HELP

    private val input by argument(help = BASE64_DECODE_INPUT_HELP).optional()

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
        try {
            echo(base64Service.decode(text))
        } catch (e: IllegalArgumentException) {
            echo("${org.jack.features.base64.BASE64_DECODE_ERROR_PREFIX}${e.message}", err = true)
        }
    }
}
