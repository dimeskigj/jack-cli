package org.jack.features.timestamp

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.enum
import org.jack.features.timestamp.services.TimestampService

const val TIMESTAMP_COMMAND_NAME = "timestamp"
const val UNIT_OPTION_NAME = "--unit"
const val UNIT_OPTION_SHORT = "-u"
const val UNIT_OPTION_HELP = "The units to use for the timestamp"
const val HELP_TEXT = "Get a timestamp"

enum class EpochUnits { SECONDS, MILLISECONDS }

class TimestampCommand(
    private val timestampService: TimestampService,
) : CliktCommand(name = TIMESTAMP_COMMAND_NAME) {
    val type: EpochUnits by option(
        UNIT_OPTION_NAME,
        UNIT_OPTION_SHORT,
        help = UNIT_OPTION_HELP,
    ).enum<EpochUnits>()
        .default(EpochUnits.MILLISECONDS)

    override fun help(context: Context) = HELP_TEXT

    override fun run() {
        val epoch =
            when (type) {
                EpochUnits.SECONDS -> timestampService.nowEpochTimeInSeconds()
                EpochUnits.MILLISECONDS -> timestampService.nowEpochTimeInMilliseconds()
            }

        echo(epoch)
    }
}
