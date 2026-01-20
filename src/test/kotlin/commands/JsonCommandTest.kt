package commands

import com.github.ajalt.clikt.testing.test
import org.jack.features.json.JsonCommand
import org.jack.features.json.services.impl.JsonServiceImpl
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
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
        val input = "{\"invalid\":"
        val result = command.test(listOf(input))

        assertTrue(result.stderr.contains("Error:"))
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

    @Test
    fun `should query simple field`() {
        val input = "{\"name\":\"jack\",\"version\":1}"
        val result = command.test(listOf("--query", ".name", input))

        assertEquals("\"jack\"\n", result.stdout)
    }

    @Test
    fun `should query nested field`() {
        val input = "{\"user\":{\"name\":\"jack\",\"id\":123}}"
        val result = command.test(listOf("-q", ".user.name", input))

        assertEquals("\"jack\"\n", result.stdout)
    }

    @Test
    fun `should query array element`() {
        val input = "{\"items\":[\"a\",\"b\",\"c\"]}"
        val result = command.test(listOf("-q", ".items[1]", input))

        assertEquals("\"b\"\n", result.stdout)
    }

    @Test
    fun `should query nested array and object`() {
        val input = "{\"users\":[{\"name\":\"alice\"},{\"name\":\"bob\"}]}"
        val result = command.test(listOf("-q", ".users[0].name", input))

        assertEquals("\"alice\"\n", result.stdout)
    }

    @Test
    fun `should return identity with dot query`() {
        val input = "{\"a\":1}"
        val result = command.test(listOf("-q", ".", "-c", input))

        assertEquals("{\"a\":1}\n", result.stdout)
    }

    @Test
    fun `should output compact json`() {
        val input = "{\"name\":\"jack\",\"age\":30}"
        val result = command.test(listOf("--compact", input))

        assertEquals("{\"name\":\"jack\",\"age\":30}\n", result.stdout)
    }

    @Test
    fun `should combine query and compact`() {
        val input = "{\"data\":{\"items\":[1,2,3]}}"
        val result = command.test(listOf("-q", ".data", "-c", input))

        assertEquals("{\"items\":[1,2,3]}\n", result.stdout)
    }

    @Test
    fun `should show error for missing key`() {
        val input = "{\"name\":\"jack\"}"
        val result = command.test(listOf("-q", ".missing", input))

        assertTrue(result.stderr.contains("Error: Key not found: missing"))
    }

    @Test
    fun `should show error for array index out of bounds`() {
        val input = "[1,2,3]"
        val result = command.test(listOf("-q", ".[10]", input))

        assertTrue(result.stderr.contains("Error: Array index out of bounds"))
    }

    @Test
    fun `should show error for invalid query syntax`() {
        val input = "{\"a\":1}"
        val result = command.test(listOf("-q", ".[abc]", input))

        assertTrue(result.stderr.contains("Error: Invalid array index"))
    }
}
