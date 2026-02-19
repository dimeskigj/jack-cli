package org.jack.features.timestamp

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.optional
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.enum
import org.jack.features.timestamp.services.TimestampService

const val TIMESTAMP_COMMAND_NAME = "timestamp"
const val UNIT_OPTION_NAME = "--unit"
const val UNIT_OPTION_SHORT = "-u"
const val UNIT_OPTION_HELP = "The units to use for the timestamp"
const val HELP_TEXT = "Get a timestamp"
const val FORMAT_OPTION_NAME = "--format"
const val FORMAT_OPTION_SHORT = "-f"
const val FORMAT_OPTION_HELP = "Format for output (ISO or pattern)"
const val TIMESTAMP_INPUT_HELP = "Timestamp (epoch or date) to process"
const val TIMESTAMP_ERROR_PREFIX = "Error: "
val DEFAULT_EPOCH_UNIT = EpochUnits.SECONDS

enum class EpochUnits { SECONDS, MILLISECONDS }

class TimestampCommand(
    private val timestampService: TimestampService,
) : CliktCommand(name = TIMESTAMP_COMMAND_NAME) {
    val type: EpochUnits by option(
        UNIT_OPTION_NAME,
        UNIT_OPTION_SHORT,
        help = UNIT_OPTION_HELP,
    ).enum<EpochUnits>()
        .default(DEFAULT_EPOCH_UNIT)

    val format: String? by option(FORMAT_OPTION_NAME, FORMAT_OPTION_SHORT, help = FORMAT_OPTION_HELP)

    val input: String? by argument(help = TIMESTAMP_INPUT_HELP).optional()

    override fun help(context: Context) = HELP_TEXT

    override fun run() {
        // Determine the epoch time in seconds
        val epochSeconds =
            if (input != null) {
                try {
                    val inputLong = input!!.toLong()
                    // If --unit is MILLISECONDS, treat input as millis, otherwise seconds
                    if (type == EpochUnits.MILLISECONDS) inputLong / 1000 else inputLong
                } catch (e: NumberFormatException) {
                    try {
                        timestampService.parseToEpochSeconds(input!!)
                    } catch (e2: Exception) {
                        echo("$TIMESTAMP_ERROR_PREFIX${e2.message}", err = true)
                        return
                    }
                }
            } else {
                timestampService.nowEpochTimeInSeconds()
            }

        // Output based on configuration
        if (format != null) {
            echo(timestampService.formatEpochSeconds(epochSeconds, format!!))
        } else {
            val result = if (type == EpochUnits.MILLISECONDS) epochSeconds * 1000 else epochSeconds
            echo(result)
        }
    }
}
