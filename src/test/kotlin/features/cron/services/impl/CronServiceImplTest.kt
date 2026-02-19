package org.jack.features.cron.services.impl

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class CronServiceImplTest {
    private val service = CronServiceImpl()

    @Test
    fun `humanize should return readable string for valid cron`() {
        val cron = "0 0 12 * * ?"
        val result = service.humanize(cron)
        // Description might vary slightly depending on locale and cron-utils version but usually contains "at 12:00"
        assertTrue(result.contains("12:00") || result.contains("12 PM"), "Expected time description in '$result'")
    }

    @Test
    fun `humanize should handle unix cron`() {
        val cron = "*/5 * * * *"
        val result = service.humanize(cron)
        assertTrue(result.lowercase().contains("every 5 minutes"), "Expected 'every 5 minutes' in '$result'")
    }

    @Test
    fun `nextExecution should return next 5 executions`() {
        val cron = "0 0 12 * * ?"
        val result = service.nextExecution(cron)
        assertEquals(5, result.size)
    }
}
