package org.jack.features.cron.subcommands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.parameters.arguments.argument
import org.jack.features.cron.services.CronService

const val EXPLAIN_COMMAND_NAME = "explain"
const val EXPLAIN_HELP = "Explain a cron expression"
const val CRON_ARGUMENT_NAME = "cron"
const val CRON_ARGUMENT_HELP = "The cron expression to explain"

class ExplainCommand(
    private val cronService: CronService,
) : CliktCommand(name = EXPLAIN_COMMAND_NAME) {
    val cron by argument(name = CRON_ARGUMENT_NAME, help = CRON_ARGUMENT_HELP)

    override fun help(context: Context) = EXPLAIN_HELP

    override fun run() {
        echo(cronService.humanize(cron))
    }
}
