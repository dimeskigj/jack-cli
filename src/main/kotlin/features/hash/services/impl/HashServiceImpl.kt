package org.jack.features.hash.services.impl

import org.jack.features.hash.services.HashAlgorithm
import org.jack.features.hash.services.HashService
import java.security.MessageDigest

class HashServiceImpl : HashService {
    override fun hash(
        input: String,
        algorithm: HashAlgorithm,
    ): String {
        val digest = MessageDigest.getInstance(algorithm.toJavaName())
        val bytes = digest.digest(input.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

    private fun HashAlgorithm.toJavaName(): String =
        when (this) {
            HashAlgorithm.MD5 -> "MD5"
            HashAlgorithm.SHA1 -> "SHA-1"
            HashAlgorithm.SHA256 -> "SHA-256"
            HashAlgorithm.SHA512 -> "SHA-512"
        }
}
