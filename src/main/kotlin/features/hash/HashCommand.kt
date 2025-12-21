package org.jack.features.hash

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.optional
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.enum
import org.jack.features.hash.services.HashAlgorithm
import org.jack.features.hash.services.HashService

const val HASH_COMMAND_NAME = "hash"
const val HASH_HELP = "Compute hash of a string"
const val HASH_INPUT_HELP = "The string to hash"
const val HASH_ALGORITHM_NAME = "--algorithm"
const val HASH_ALGORITHM_NAME_SHORT = "-a"
const val HASH_ALGORITHM_HELP = "The algorithm to use"

class HashCommand(
    private val hashService: HashService,
) : CliktCommand(name = HASH_COMMAND_NAME) {
    val input by argument(help = HASH_INPUT_HELP).optional()

    val algorithm: HashAlgorithm by option(
        HASH_ALGORITHM_NAME,
        HASH_ALGORITHM_NAME_SHORT,
        help = HASH_ALGORITHM_HELP,
    ).enum<HashAlgorithm>().default(HashAlgorithm.SHA256)

    override fun help(context: Context) = HASH_HELP

    override fun run() {
        val text = input ?: System.`in`.bufferedReader().readText()
        val result = hashService.hash(text, algorithm)
        echo(result)
    }
}
