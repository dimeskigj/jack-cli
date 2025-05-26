package org.factotum.features.uuid.services.impl

import org.factotum.features.uuid.services.UuidService
import java.util.UUID

class UuidServiceImpl : UuidService {
    override fun randomUuid(): UUID {
        return UUID.randomUUID()
    }
}