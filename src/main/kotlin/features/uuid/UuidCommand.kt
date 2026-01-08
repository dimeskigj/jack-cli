package org.jack.features.uuid

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context

enum class UuidType { UUID, ULID }

const val UUID_COMMAND_NAME = "uuid"
const val UUID_HELP = "Work with UUIDs and ULIDs"
const val UUID_COUNT_NAME = "--count"
const val UUID_COUNT_NAME_SHORT = "-c"
const val UUID_COUNT_HELP = "Number of unique identifiers"
const val UUID_COUNT_MUST_BE_POSITIVE_INTEGER = "Count must be a positive integer"
const val UUID_TYPE_NAME = "--type"
const val UUID_TYPE_NAME_SHORT = "-t"
const val UUID_TYPE_HELP = "The type of the unique identifier"
const val DEFAULT_UUID_COUNT = 1
const val UUID_GENERATE_COMMAND_NAME = "generate"
const val UUID_GENERATE_HELP = "Generate random unique identifiers"
const val UUID_VALIDATE_COMMAND_NAME = "validate"
const val UUID_VALIDATE_HELP = "Validate a UUID/ULID value"
const val UUID_VALUE_ARGUMENT_NAME = "value"
const val UUID_VALUE_HELP = "The UUID/ULID value to validate"
const val UUID_VALID_STATUS = "Status: Valid"
const val UUID_INVALID_STATUS = "Status: Invalid"

class UuidCommand : CliktCommand(name = UUID_COMMAND_NAME) {
    override fun help(context: Context) = UUID_HELP

    override fun run() = Unit
}
