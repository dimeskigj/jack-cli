package org.jack.features.uuid.services

import java.util.*

interface UuidService {
    fun randomUuid(): UUID
    fun randomUlid(): String
}