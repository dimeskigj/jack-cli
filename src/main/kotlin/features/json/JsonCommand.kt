package org.jack.features.json

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.core.terminal
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.help
import com.github.ajalt.clikt.parameters.arguments.optional
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.help
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.file
import com.github.ajalt.clikt.parameters.types.int
import org.jack.features.json.services.JsonService
import org.jack.utils.readArgumentOrStdin

const val JSON_COMMAND_NAME = "json"
const val JSON_HELP = "Verify and pretty print JSON input"
const val JSON_INPUT_HELP = "The JSON string to format"
const val JSON_FILE_NAME = "--file"
const val JSON_FILE_NAME_SHORT = "-f"
const val JSON_FILE_HELP = "The JSON file to format"
const val JSON_INDENT_NAME = "--indent"
const val JSON_INDENT_NAME_SHORT = "-i"
const val JSON_INDENT_HELP = "Number of spaces for indentation"
const val DEFAULT_JSON_INDENT = 4

class JsonCommand(
    private val jsonService: JsonService,
) : CliktCommand(
        name = JSON_COMMAND_NAME,
    ) {
    private val input by argument().help(JSON_INPUT_HELP).optional()
    private val file by option(JSON_FILE_NAME, JSON_FILE_NAME_SHORT).file(mustExist = true, canBeDir = false).help(JSON_FILE_HELP)
    private val indent by option(JSON_INDENT_NAME, JSON_INDENT_NAME_SHORT).int().default(DEFAULT_JSON_INDENT).help(JSON_INDENT_HELP)

    override fun help(context: Context) = JSON_HELP

    override fun run() {
        try {
            val jsonText =
                when {
                    file != null -> file!!.readText()
                    else -> readArgumentOrStdin(input)
                }

            if (jsonText == null || jsonText.isBlank()) {
                echo("Error: No JSON input provided", err = true)
                return
            }

            val formatted = jsonService.format(jsonText, indent)
            echo(formatted)
        } catch (e: Exception) {
            echo("Error: ${e.message}", err = true)
        }
    }
}
