package org.jack.features.timestamp.services

interface TimestampService {
    fun nowEpochTimeInSeconds(): Long

    fun nowEpochTimeInMilliseconds(): Long

    fun parseToEpochSeconds(input: String): Long

    fun formatEpochSeconds(
        seconds: Long,
        format: String,
    ): String
}
