package org.jack.features.uuid

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.validate
import com.github.ajalt.clikt.parameters.types.int
import org.jack.features.uuid.services.UuidService

const val UUID_COMMAND_NAME = "uuid"
const val UUID_HELP = "Generate a random unique identifier"
const val UUID_COUNT_NAME = "--count"
const val UUID_COUNT_NAME_SHORT = "-c"
const val UUID_COUNT_HELP = "Number of unique identifiers"
const val UUID_COUNT_MUST_BE_POSITIVE_INTEGER = "Must be a positive integer"

class UuidCommand(private val uuidService: UuidService) : CliktCommand(name = UUID_COMMAND_NAME) {
    val count by option(
        UUID_COUNT_NAME, UUID_COUNT_NAME_SHORT,
        help = UUID_COUNT_HELP
    ).int().default(1).validate {
        require(it > 0) {
            UUID_COUNT_MUST_BE_POSITIVE_INTEGER
        }
    }

    override fun help(context: Context) = UUID_HELP

    override fun run() {
        repeat(times = count) {
            echo(uuidService.randomUuid())
        }
    }
}