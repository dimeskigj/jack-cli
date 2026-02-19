package org.jack.features.cron.services.impl

import com.cronutils.descriptor.CronDescriptor
import com.cronutils.model.CronType
import com.cronutils.model.definition.CronDefinitionBuilder
import com.cronutils.model.time.ExecutionTime
import com.cronutils.parser.CronParser
import kotlinx.datetime.Clock
import kotlinx.datetime.toJavaInstant
import org.jack.features.cron.services.CronService
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.Locale

class CronServiceImpl : CronService {
    private val cronDefinition = CronDefinitionBuilder.instanceDefinitionFor(CronType.UNIX)
    private val parser = CronParser(cronDefinition)
    private val descriptor = CronDescriptor.instance(Locale.US)

    override fun humanize(cron: String): String =
        try {
            val quartzDefinition = CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ)
            val quartzParser = CronParser(quartzDefinition)
            val parsedCron = quartzParser.parse(cron)
            descriptor.describe(parsedCron)
        } catch (e: Exception) {
            // Fallback to UNIX if QUARTZ fails, though QUARTZ is more common for complex expressions
            try {
                val parsedCron = parser.parse(cron)
                descriptor.describe(parsedCron)
            } catch (e2: Exception) {
                "Invalid cron expression: ${e.message}"
            }
        }

    override fun nextExecution(cron: String): List<String> {
        val parsedCron =
            try {
                val quartzDefinition = CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ)
                val quartzParser = CronParser(quartzDefinition)
                quartzParser.parse(cron)
            } catch (e: Exception) {
                parser.parse(cron)
            }

        val executionTime = ExecutionTime.forCron(parsedCron)
        val now =
            Clock.System
                .now()
                .toJavaInstant()
                .atZone(ZoneId.systemDefault())

        val dates = mutableListOf<ZonedDateTime>()
        var next = executionTime.nextExecution(now)

        repeat(5) {
            if (next.isPresent) {
                dates.add(next.get())
                next = executionTime.nextExecution(next.get())
            }
        }

        return dates.map { it.toString() }
    }
}
