package org.jack.features.uuid.subcommands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.optional
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.enum
import org.jack.features.uuid.UUID_INVALID_STATUS
import org.jack.features.uuid.UUID_TYPE_HELP
import org.jack.features.uuid.UUID_TYPE_NAME
import org.jack.features.uuid.UUID_TYPE_NAME_SHORT
import org.jack.features.uuid.UUID_VALIDATE_COMMAND_NAME
import org.jack.features.uuid.UUID_VALIDATE_HELP
import org.jack.features.uuid.UUID_VALID_STATUS
import org.jack.features.uuid.UUID_VALUE_ARGUMENT_NAME
import org.jack.features.uuid.UUID_VALUE_HELP
import org.jack.features.uuid.UuidType
import org.jack.features.uuid.services.UuidService

class ValidateCommand(
    private val uuidService: UuidService,
) : CliktCommand(name = UUID_VALIDATE_COMMAND_NAME) {
    val value by argument(name = UUID_VALUE_ARGUMENT_NAME, help = UUID_VALUE_HELP).optional()

    val type: UuidType by option(
        UUID_TYPE_NAME,
        UUID_TYPE_NAME_SHORT,
        help = UUID_TYPE_HELP,
    ).enum<UuidType>().default(UuidType.UUID)

    override fun help(context: Context) = UUID_VALIDATE_HELP

    override fun run() {
        val input =
            value ?: System.`in`
                .bufferedReader()
                .readText()
                .trim()

        if (input.isEmpty()) return

        val isValid =
            when (type) {
                UuidType.UUID -> uuidService.validateUuid(input)
                UuidType.ULID -> uuidService.validateUlid(input)
            }

        if (isValid) {
            echo(UUID_VALID_STATUS)
        } else {
            echo(UUID_INVALID_STATUS, err = true)
        }
    }
}
