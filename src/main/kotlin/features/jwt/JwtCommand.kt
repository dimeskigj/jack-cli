package org.jack.features.jwt

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.help
import com.github.ajalt.clikt.parameters.arguments.optional
import com.github.ajalt.clikt.parameters.options.help
import com.github.ajalt.clikt.parameters.options.option
import org.jack.features.jwt.services.JwtService
import org.jack.utils.readArgumentOrStdin

const val JWT_COMMAND_NAME = "jwt"
const val JWT_HELP = "Decode and pretty print a JWT token"
const val JWT_TOKEN_HELP = "The JWT token to decode"
const val JWT_SECRET_NAME = "--secret"
const val JWT_SECRET_NAME_SHORT = "-s"
const val JWT_SECRET_HELP = "The secret to verify the signature (HS256 only)"

class JwtCommand(
    private val jwtService: JwtService,
) : CliktCommand(
        name = JWT_COMMAND_NAME,
    ) {
    private val token by argument().help(JWT_TOKEN_HELP).optional()
    private val secret by option(JWT_SECRET_NAME, JWT_SECRET_NAME_SHORT).help(JWT_SECRET_HELP)

    override fun help(context: Context) = JWT_HELP

    override fun run() {
        try {
            val jwtToken = readArgumentOrStdin(token)

            if (jwtToken == null || jwtToken.isEmpty()) {
                echo("Error: No JWT token provided", err = true)
                return
            }

            val decoded = jwtService.decode(jwtToken, secret)
            echo(decoded)
        } catch (e: Exception) {
            echo("Error: ${e.message}", err = true)
        }
    }
}
