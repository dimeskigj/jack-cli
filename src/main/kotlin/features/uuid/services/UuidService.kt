package org.jack.features.uuid.services

import java.util.UUID

interface UuidService {
    fun randomUuid(): UUID

    fun randomUlid(): String

    fun validateUuid(value: String): Boolean

    fun validateUlid(value: String): Boolean
}
