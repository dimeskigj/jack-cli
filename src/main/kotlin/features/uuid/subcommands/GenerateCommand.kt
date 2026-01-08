package org.jack.features.uuid.subcommands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.validate
import com.github.ajalt.clikt.parameters.types.enum
import com.github.ajalt.clikt.parameters.types.int
import org.jack.features.uuid.DEFAULT_UUID_COUNT
import org.jack.features.uuid.UUID_COUNT_HELP
import org.jack.features.uuid.UUID_COUNT_MUST_BE_POSITIVE_INTEGER
import org.jack.features.uuid.UUID_COUNT_NAME
import org.jack.features.uuid.UUID_COUNT_NAME_SHORT
import org.jack.features.uuid.UUID_GENERATE_COMMAND_NAME
import org.jack.features.uuid.UUID_GENERATE_HELP
import org.jack.features.uuid.UUID_TYPE_HELP
import org.jack.features.uuid.UUID_TYPE_NAME
import org.jack.features.uuid.UUID_TYPE_NAME_SHORT
import org.jack.features.uuid.UuidType
import org.jack.features.uuid.services.UuidService

class GenerateCommand(
    private val uuidService: UuidService,
) : CliktCommand(name = UUID_GENERATE_COMMAND_NAME) {
    val count by option(
        UUID_COUNT_NAME,
        UUID_COUNT_NAME_SHORT,
        help = UUID_COUNT_HELP,
    ).int().default(DEFAULT_UUID_COUNT).validate {
        require(it > 0) {
            UUID_COUNT_MUST_BE_POSITIVE_INTEGER
        }
    }

    val type: UuidType by option(
        UUID_TYPE_NAME,
        UUID_TYPE_NAME_SHORT,
        help = UUID_TYPE_HELP,
    ).enum<UuidType>().default(UuidType.UUID)

    override fun help(context: Context) = UUID_GENERATE_HELP

    override fun run() {
        val getRandomId =
            when (type) {
                UuidType.UUID -> uuidService::randomUuid
                UuidType.ULID -> uuidService::randomUlid
            }

        repeat(times = count) {
            echo(getRandomId())
        }
    }
}
