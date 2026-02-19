package org.jack.features.cron.services

interface CronService {
    fun humanize(cron: String): String

    fun nextExecution(cron: String): List<String>
}
