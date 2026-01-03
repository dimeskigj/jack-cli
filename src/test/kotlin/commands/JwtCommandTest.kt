package commands

import com.github.ajalt.clikt.testing.test
import org.jack.features.jwt.JwtCommand
import org.jack.features.jwt.services.impl.JwtServiceImpl
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.assertTrue

class JwtCommandTest {
    private val jwtService = JwtServiceImpl()
    private val command = JwtCommand(jwtService)

    @Test
    fun `should decode a valid jwt`() {
        // Example JWT from jwt.io (Header: {"alg":"HS256","typ":"JWT"}, Payload: {"sub":"1234567890","name":"John Doe","iat":1516239022})
        val token =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" +
                ".eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ" +
                ".SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"

        val result = assertDoesNotThrow { command.test(token) }

        assertTrue(result.stdout.contains("--- HEADER ---"))
        assertTrue(result.stdout.contains("\"alg\": \"HS256\""))
        assertTrue(result.stdout.contains("--- PAYLOAD ---"))
        assertTrue(result.stdout.contains("\"name\": \"John Doe\""))
        assertTrue(result.stdout.contains("Status: [Protected]"))
    }

    @Test
    fun `should verify signature with correct secret`() {
        val token =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" +
                ".eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ" +
                ".SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
        val secret = "your-256-bit-secret"

        val result = command.test("$token --secret $secret")

        assertTrue(result.stdout.contains("Status: Valid"))
    }

    @Test
    fun `should fail verification with incorrect secret`() {
        val token =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" +
                ".eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ" +
                ".SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
        val secret = "wrong-secret"

        val result = command.test("$token --secret $secret")

        assertTrue(result.stdout.contains("Status: Invalid"))
    }

    @Test
    fun `should show error for invalid jwt`() {
        val token = "invalid-token"
        val result = command.test(token)

        assertTrue(result.stderr.contains("Error: Invalid JWT format"))
    }

    @Test
    fun `should read from stdin`() {
        val token =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" +
                ".eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ" +
                ".SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"

        val result = command.test(emptyList(), stdin = token, inputInteractive = false)

        assertTrue(result.stdout.contains("--- HEADER ---"))
        assertTrue(result.stdout.contains("\"alg\": \"HS256\""))
    }
}
