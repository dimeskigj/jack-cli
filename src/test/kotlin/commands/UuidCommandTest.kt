package commands

import com.github.ajalt.clikt.testing.test
import org.jack.features.uuid.UuidCommand
import org.jack.features.uuid.services.impl.UuidServiceImpl
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import ulid.ULID
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UuidCommandTest {
    private val uuidService = UuidServiceImpl()
    private val command = UuidCommand(uuidService)

    @Test
    fun `when no arguments passed expect no error`() {
        val result = assertDoesNotThrow { command.test() }

        assert(result.stderr.isEmpty())
    }

    @Test
    fun `when no arguments passed expect one uuid`() {
        val result = assertDoesNotThrow { command.test() }

        assert(result.stdout.contains("Usage: uuid"))
    }

    @Test
    fun `when generate subcommand with no arguments passed expect no error`() {
        val result = assertDoesNotThrow { command.test("generate") }

        assert(result.stderr.isEmpty())
    }

    @Test
    fun `when generate subcommand with no arguments passed expect one uuid`() {
        val result = assertDoesNotThrow { command.test("generate") }

        val uuidCount =
            result.stdout
                .split("\n")
                .filter { it.isNotEmpty() }
                .count()
        assertEquals(1, uuidCount)
    }

    @Test
    fun `when generate subcommand with '--count' argument passed with positive value expect no error`() {
        val result = assertDoesNotThrow { command.test("generate --count 1") }

        assert(result.stderr.isEmpty())
    }

    @Test
    fun `when generate subcommand with '-c' argument passed with positive value expect no error`() {
        val result = assertDoesNotThrow { command.test("generate -c 1") }

        assert(result.stderr.isEmpty())
    }

    @Test
    fun `when generate subcommand with '--count' argument passed with value 25 expect 25 uuids`() {
        val count = 25

        val result = assertDoesNotThrow { command.test("generate --count $count") }

        val uuidCount =
            result.stdout
                .split("\n")
                .filter { it.isNotEmpty() }
                .count()
        assertEquals(count, uuidCount)
    }

    @Test
    fun `when generate subcommand with '--count' argument passed with negative value expect error`() {
        val result = command.test("generate --count -1")

        assert(result.stderr.isNotEmpty())
    }

    @Test
    fun `when generate subcommand with '-c' argument passed with negative value expect error`() {
        val result = command.test("generate -c -1")

        assert(result.stderr.isNotEmpty())
    }

    @Test
    fun `when generate subcommand with no '--type' argument passed expect UUID to be generated`() {
        val result = command.test("generate").stdout
        val trimmedResult = result.trim()

        assertDoesNotThrow { UUID.fromString(trimmedResult) }
    }

    @Test
    fun `when generate subcommand with '--type' argument passed with value 'uuid' expect UUID to be generated`() {
        val result = command.test("generate --type uuid").stdout
        val trimmedResult = result.trim()

        assertDoesNotThrow { UUID.fromString(trimmedResult) }
    }

    @Test
    fun `when generate subcommand with '--type' argument passed with value 'ulid' expect ULID to be generated`() {
        val result = command.test("generate --type ulid").stdout
        val trimmedResult = result.trim()

        assertDoesNotThrow { ULID.parseULID(trimmedResult) }
    }

    @Test
    fun `when generate subcommand with '--count' argument passed with value 25 expect 25 ulids`() {
        val count = 25

        val result = assertDoesNotThrow { command.test("generate --type ulid --count $count") }

        val ulidCount =
            result.stdout
                .split("\n")
                .filter { it.isNotEmpty() }
                .count()
        assertEquals(count, ulidCount)
    }

    @Test
    fun `validate subcommand with valid uuid prints Status Valid`() {
        val result = command.test("validate 3fa85f64-5717-4562-b3fc-2c963f66afa6")
        assertTrue(result.stdout.contains("Status: Valid"))
    }

    @Test
    fun `validate subcommand with invalid uuid prints Status Invalid`() {
        val result = command.test("validate not-a-uuid-da")
        assertTrue(result.stderr.contains("Status: Invalid"))
    }

    @Test
    fun `validate subcommand with valid ulid prints Status Valid`() {
        val result = command.test("validate --type ulid 01ARYZ6S41TSV4RRFFQ69G5FAV")
        assertTrue(result.stdout.contains("Status: Valid"))
    }

    @Test
    fun `validate subcommand with invalid ulid prints Status Invalid`() {
        val result = command.test("validate --type ulid gibberish-thats-not-ulid-67")
        assertTrue(result.stderr.contains("Status: Invalid"))
    }
}
