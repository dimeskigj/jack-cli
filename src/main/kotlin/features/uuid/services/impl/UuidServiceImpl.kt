package org.jack.features.uuid.services.impl

import org.jack.features.uuid.services.UuidService
import ulid.ULID
import java.util.UUID

class UuidServiceImpl : UuidService {
    override fun randomUuid(): UUID = UUID.randomUUID()

    override fun randomUlid(): String = ULID.randomULID()

    override fun validateUuid(value: String): Boolean = UUID_REGEX.matches(value.trim())

    override fun validateUlid(value: String): Boolean = ULID_REGEX.matches(value.trim())

    companion object {
        private val UUID_REGEX =
            Regex(
                "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$",
                RegexOption.IGNORE_CASE,
            )

        private val ULID_REGEX =
            Regex(
                "^[0-9A-HJ-NP-TV-Z]{26}$",
                RegexOption.IGNORE_CASE,
            )
    }
}
