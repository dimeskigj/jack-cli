package commands

import com.github.ajalt.clikt.testing.test
import org.factotum.features.lorem.LoremCommand
import org.factotum.features.lorem.services.impl.LoremIpsumServiceImpl
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class LoremCommandTest {
    private val loremIpsumService = LoremIpsumServiceImpl()
    private val command = LoremCommand(loremIpsumService)

    @Test
    fun `when no arguments passed expect no error`() {
        val result = assertDoesNotThrow { command.test() }

        assert(result.stderr.isEmpty())
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