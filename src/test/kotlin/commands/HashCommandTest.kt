package commands

import com.github.ajalt.clikt.testing.test
import org.jack.features.hash.HashCommand
import org.jack.features.hash.services.impl.HashServiceImpl
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.io.TempDir
import java.io.File
import kotlin.test.assertEquals

class HashCommandTest {
    @TempDir
    lateinit var tempDir: File

    private val hashService = HashServiceImpl()
    private val command = HashCommand(hashService)

    @Test
    fun `when input passed expect no error`() {
        val result = assertDoesNotThrow { command.test("hello") }
        assert(result.stderr.isEmpty())
    }

    @Test
    fun `when file passed expect correct sha256 hash`() {
        val file = tempDir.resolve("test.txt")
        file.writeText("hello")
        val result = command.test(listOf("--file", file.absolutePath))
        // SHA-256 of "hello"
        val expected = "2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824"
        assertEquals(expected, result.stdout.trim())
    }

    @Test
    fun `when input passed expect correct sha256 hash`() {
        val result = command.test("hello")
        // SHA-256 of "hello"
        val expected = "2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824"
        assertEquals(expected, result.stdout.trim())
    }

    @Test
    fun `when input and algorithm passed expect correct md5 hash`() {
        val result = command.test("hello --algorithm MD5")
        // MD5 of "hello"
        val expected = "5d41402abc4b2a76b9719d911017c592"
        assertEquals(expected, result.stdout.trim())
    }

    @Test
    fun `when input and algorithm passed expect correct sha1 hash`() {
        val result = command.test("hello -a SHA1")
        // SHA-1 of "hello"
        val expected = "aaf4c61ddcc5e8a2dabede0f3b482cd9aea9434d"
        assertEquals(expected, result.stdout.trim())
    }

    @Test
    fun `when no argument passed expect input from stdin`() {
        val input = "hello"
        val result = command.test(emptyList(), stdin = input, inputInteractive = false)
        // SHA-256 of "hello"
        val expected = "2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824"
        assertEquals(expected, result.stdout.trim())
    }
}
