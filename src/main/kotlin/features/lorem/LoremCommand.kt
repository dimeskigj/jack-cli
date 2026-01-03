package org.jack.features.lorem

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.validate
import com.github.ajalt.clikt.parameters.types.int
import org.jack.features.lorem.services.LoremIpsumService

const val LOREM_COMMAND_NAME = "lorem"
const val LOREM_COMMAND_HELP = "Generate dummy text"
const val LOREM_COUNT_NAME = "--count"
const val LOREM_COUNT_NAME_SHORT = "-c"
const val LOREM_COUNT_HELP = "Word count of the generated text"
const val LOREM_COUNT_MUST_BE_POSITIVE_INTEGER = "Must be a positive integer"
const val DEFAULT_LOREM_COUNT = 10

class LoremCommand(
    private val loremIpsumService: LoremIpsumService,
) : CliktCommand(name = LOREM_COMMAND_NAME) {
    val count by option(
        LOREM_COUNT_NAME,
        LOREM_COUNT_NAME_SHORT,
        help = LOREM_COUNT_HELP,
    ).int().default(DEFAULT_LOREM_COUNT).validate {
        require(it > 0) {
            LOREM_COUNT_MUST_BE_POSITIVE_INTEGER
        }
    }

    override fun help(context: Context): String = LOREM_COMMAND_HELP

    override fun run() {
        echo(loremIpsumService.generateLoremIpsumText(count))
    }
}
