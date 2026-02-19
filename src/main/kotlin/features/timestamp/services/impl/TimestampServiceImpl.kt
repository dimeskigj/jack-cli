package org.jack.features.timestamp.services.impl

import kotlinx.datetime.Clock
import org.jack.features.timestamp.services.TimestampService
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class TimestampServiceImpl : TimestampService {
    override fun nowEpochTimeInSeconds(): Long = Clock.System.now().toEpochMilliseconds() / 1000

    override fun nowEpochTimeInMilliseconds(): Long = Clock.System.now().toEpochMilliseconds()

    override fun parseToEpochSeconds(input: String): Long {
        // Try strict ISO-8601 first
        try {
            return Instant.parse(input).epochSecond
        } catch (e: Exception) {
            // Fallback: try parsing as simple date (YYYY-MM-DD) in UTC
            try {
                return java.time.LocalDate
                    .parse(input)
                    .atStartOfDay(ZoneId.of("UTC"))
                    .toEpochSecond()
            } catch (e2: Exception) {
                throw IllegalArgumentException(
                    "Could not parse timestamp: '$input'. Supported formats: ISO-8601 (e.g. 2023-01-01T00:00:00Z) or YYYY-MM-DD.",
                )
            }
        }
    }

    override fun formatEpochSeconds(
        seconds: Long,
        format: String,
    ): String {
        val instant = Instant.ofEpochSecond(seconds)
        return when (format.uppercase()) {
            "ISO" -> DateTimeFormatter.ISO_INSTANT.format(instant)
            else -> DateTimeFormatter.ofPattern(format).withZone(ZoneId.of("UTC")).format(instant)
        }
    }
}
