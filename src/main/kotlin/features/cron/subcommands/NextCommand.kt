package org.jack.features.cron.subcommands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.parameters.arguments.argument
import org.jack.features.cron.services.CronService

const val NEXT_COMMAND_NAME = "next"
const val NEXT_HELP = "Show next execution times"

class NextCommand(
    private val cronService: CronService,
) : CliktCommand(name = NEXT_COMMAND_NAME) {
    val cron by argument(name = CRON_ARGUMENT_NAME, help = CRON_ARGUMENT_HELP)

    override fun help(context: Context) = NEXT_HELP

    override fun run() {
        cronService.nextExecution(cron).forEach { echo(it) }
    }
}
