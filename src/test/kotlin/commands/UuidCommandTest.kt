package commands

import com.github.ajalt.clikt.testing.test
import org.factotum.features.uuid.UuidCommand
import org.factotum.features.uuid.services.impl.UuidServiceImpl
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
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
}