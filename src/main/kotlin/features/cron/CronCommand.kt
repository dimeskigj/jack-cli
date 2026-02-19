package org.jack.features.cron

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context

const val CRON_COMMAND_NAME = "cron"
const val CRON_HELP = "Work with cron expressions"

class CronCommand : CliktCommand(name = CRON_COMMAND_NAME) {
    override fun help(context: Context) = CRON_HELP

    override fun run() = Unit
}
