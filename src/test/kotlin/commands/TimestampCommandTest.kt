package commands

import com.github.ajalt.clikt.testing.test
import org.jack.features.timestamp.TimestampCommand
import org.jack.features.timestamp.services.impl.TimestampServiceImpl
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class TimestampCommandTest {
    private val timestampService = TimestampServiceImpl()
    private val command = TimestampCommand(timestampService)

    @Test
    fun `when no arguments passed expect no error`() {
        val result = assertDoesNotThrow { command.test() }

        assert(result.stderr.isEmpty())
    }

    @Test
    fun `when '--unit' argument passed with value 'seconds' expect valid epoch in seconds`() {
        val result = assertDoesNotThrow { command.test("--unit SECONDS") }

        val epochSeconds = result.stdout.trim().toLongOrNull()
        assert(epochSeconds != null)
    }

    @Test
    fun `when '--unit' argument passed with value 'milliseconds' expect valid epoch in milliseconds`() {
        val result = assertDoesNotThrow { command.test("--unit MILLISECONDS") }

        val epochMilliseconds = result.stdout.trim().toLongOrNull()
        assert(epochMilliseconds != null)
    }

    @Test
    fun `when '--unit' argument passed with an invalid value expect error`() {
        val result = command.test("--unit INVALID_UNIT")

        assert(result.stderr.isNotEmpty())
    }

    @Test
    fun `when no '--unit' argument passed expect default unit to be milliseconds`() {
        val result = assertDoesNotThrow { command.test() }

        val epochMilliseconds = result.stdout.trim().toLongOrNull()
        assert(epochMilliseconds != null)
    }
}
