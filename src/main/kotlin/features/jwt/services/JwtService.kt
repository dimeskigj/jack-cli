package org.jack.features.jwt.services

interface JwtService {
    fun decode(
        token: String,
        secret: String? = null,
    ): String
}
