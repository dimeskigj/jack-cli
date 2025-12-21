package org.jack.features.hash.services

enum class HashAlgorithm {
    MD5,
    SHA1,
    SHA256,
    SHA512,
}

interface HashService {
    fun hash(
        input: String,
        algorithm: HashAlgorithm,
    ): String
}
