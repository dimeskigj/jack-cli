package org.factotum.features.uuid.services

import java.util.UUID

interface UuidService {
    fun randomUuid(): UUID
}