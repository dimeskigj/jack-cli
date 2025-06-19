package org.jack.features.timestamp.services.impl

import kotlinx.datetime.Clock
import org.jack.features.timestamp.services.TimestampService

class TimestampServiceImpl : TimestampService {
    override fun nowEpochTimeInSeconds(): Long = Clock.System.now().toEpochMilliseconds() / 1000

    override fun nowEpochTimeInMilliseconds(): Long = Clock.System.now().toEpochMilliseconds()
}