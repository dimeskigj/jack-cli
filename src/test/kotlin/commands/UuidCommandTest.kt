package commands

import com.github.ajalt.clikt.testing.test
import org.jack.features.uuid.UuidCommand
import org.jack.features.uuid.UuidType
import org.jack.features.uuid.services.impl.UuidServiceImpl
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import ulid.ULID
import java.util.UUID
import kotlin.test.assertEquals

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

        val uuidCount = result.stdout.split("\n").filter { it.isNotEmpty() }.count()
        assertEquals(1, uuidCount)
    }

    @Test
    fun `when '--count' argument passed with positive value expect no error`() {
        val result = assertDoesNotThrow { command.test("--count 1") }

        assert(result.stderr.isEmpty())
    }

    @Test
    fun `when '-c' argument passed with positive value expect no error`() {
        val result = assertDoesNotThrow { command.test("-c 1") }

        assert(result.stderr.isEmpty())
    }

    @Test
    fun `when '--count' argument passed with value 25 expect 25 uuids`() {
        val count = 25

        val result = assertDoesNotThrow { command.test("--count $count") }

        val uuidCount = result.stdout.split("\n").filter { it.isNotEmpty() }.count()
        assertEquals(count, uuidCount)
    }

    @Test
    fun `when '--count' argument passed with negative value expect error`() {
        val result = command.test("--count -1")

        assert(result.stderr.isNotEmpty())
    }

    @Test
    fun `when '-c' argument passed with negative value expect error`() {
        val result = command.test("-c -1")

        assert(result.stderr.isNotEmpty())
    }

    @Test
    fun `when no '--type' argument passed 'type' member defaults to UUID`() {
        val result = command.test().run { command }

        assert(result.type == UuidType.UUID)
    }

    @Test
    fun `when '--type' argument passed with value 'uuid' type option is set to correct enum member`() {
        val result = command.test("--type uuid").run { command }

        assert(result.type == UuidType.UUID)
    }

    @Test
    fun `when '--type' argument passed with value 'ulid' type option is set to correct enum member`() {
        val result = command.test("--type ulid").run { command }

        assert(result.type == UuidType.ULID)
    }

    @Test
    fun `when '--count' argument passed with value 25 expect 25 ulids`() {
        val count = 25

        val result = assertDoesNotThrow { command.test("--type ulid --count $count") }

        val ulidCount = result.stdout.split("\n").filter { it.isNotEmpty() }.count()
        assertEquals(count, ulidCount)
    }

    @Test
    fun `when '--type' argument passed with value 'uuid' expect a valid UUID`() {
        val result = command.test("--type uuid").stdout
        val trimmedResult = result.trim()

        assertDoesNotThrow {  UUID.fromString(trimmedResult)}
    }

    @Test
    fun `when '--type' argument passed with value 'ulid' expect a valid ULID`() {
        val result = command.test("--type ulid").stdout
        val trimmedResult = result.trim()

        assertDoesNotThrow {  ULID.parseULID(trimmedResult)}
    }
}