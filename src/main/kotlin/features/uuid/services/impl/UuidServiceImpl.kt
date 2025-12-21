package org.jack.features.uuid.services.impl

import org.jack.features.uuid.services.UuidService
import ulid.ULID
import java.util.UUID

class UuidServiceImpl : UuidService {
    override fun randomUuid(): UUID = UUID.randomUUID()

    override fun randomUlid(): String = ULID.randomULID()
}
