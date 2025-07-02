package org.jack.features.timestamp.services

interface TimestampService {
    fun nowEpochTimeInSeconds(): Long
    fun nowEpochTimeInMilliseconds(): Long
}