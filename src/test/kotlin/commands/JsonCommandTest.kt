package commands

import com.github.ajalt.clikt.testing.test
import org.jack.features.json.JsonCommand
import org.jack.features.json.services.impl.JsonServiceImpl
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class JsonCommandTest {
    private val jsonService = JsonServiceImpl()
    private val command = JsonCommand(jsonService)

    @Test
    fun `should format valid json object`() {
        val input = "{\"name\":\"John\",\"age\":30}"
        val result = command.test(listOf(input))

        assertTrue(result.stdout.contains("\"name\": \"John\""))
        assertTrue(result.stdout.contains("\"age\": 30"))
    }

    @Test
    fun `should format valid json array`() {
        val input = "[1,2,3]"
        val result = command.test(listOf(input))

        assertTrue(result.stdout.contains("1"))
        assertTrue(result.stdout.contains("2"))
        assertTrue(result.stdout.contains("3"))
    }

    @Test
    fun `should respect custom indent`() {
        val input = "{\"a\":1}"
        val result = command.test(listOf("--indent", "2", input))

        assertTrue(result.stdout.contains("\n  \"a\""))
    }

    @Test
    fun `should show error for invalid json`() {
        val input = "{\"invalid\":}"
        val result = command.test(listOf(input))

        assertTrue(result.stderr.contains("Error: Invalid JSON input"))
    }

    @Test
    fun `should handle lenient json`() {
        val input = "{name:jack,version:1}"
        val result = command.test(listOf(input))

        assertTrue(result.stdout.contains("\"name\": \"jack\""))
        assertTrue(result.stdout.contains("\"version\": 1"))
    }

    @Test
    fun `should read from stdin`() {
        val input = "{\"name\":\"jack\"}"
        val result = command.test(emptyList(), stdin = input, inputInteractive = false)

        assertTrue(result.stdout.contains("\"name\": \"jack\""))
    }
}
