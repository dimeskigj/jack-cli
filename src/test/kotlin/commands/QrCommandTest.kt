package commands

import com.github.ajalt.clikt.testing.test
import org.jack.features.qr.QrCommand
import org.jack.features.qr.services.QrCodeWriterService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import java.io.File
import kotlin.test.assertEquals

class QrCommandTest {
    @TempDir
    lateinit var tempFile: File

    private val mockQrCodeWriterService: QrCodeWriterService = mock()

    private val command = QrCommand(mockQrCodeWriterService)

    @Test
    fun `should require input and output parameters`() {
        val outputFile = tempFile.resolve("qr.png")
        val result = command.test(listOf("test", "--output", outputFile.absolutePath))
        assert(result.stdout.contains("QR code successfully generated at:"))
        assertEquals("", result.stderr)
        assertEquals(0, result.statusCode)
    }

    @Test
    fun `should fail when required parameters are missing`() {
        val result = command.test(emptyList())
        assertEquals(1, result.statusCode)
        assert(result.stderr.contains("missing option --output", ignoreCase = true))
    }

    @Test
    fun `should fail when input is missing`() {
        val outputFile = tempFile.resolve("qr.png")
        val result = command.test(listOf("--output", outputFile.absolutePath))
        assert(result.stderr.contains("Error: No content provided"))
    }

    @Test
    fun `should use default colors when not specified`() {
        val outputFile = tempFile.resolve("qr.png")
        command.test(listOf("test", "--output", outputFile.absolutePath))

        verify(mockQrCodeWriterService).writeQrCode(
            content = "test",
            outputFile = outputFile,
            backgroundColorRgba = 0xFFFFFFFF.toInt(),
            foregroundColorRgba = 0xFF000000.toInt(),
        )
    }

    @Test
    fun `should accept custom colors`() {
        val outputFile = tempFile.resolve("qr.png")
        command.test(listOf("test", "--output", outputFile.absolutePath, "-b", "00FF00", "-f", "FF0000FF"))

        verify(mockQrCodeWriterService).writeQrCode(
            content = "test",
            outputFile = outputFile,
            backgroundColorRgba = 0xFF00FF00.toInt(),
            foregroundColorRgba = 0xFF0000FF.toInt(),
        )
    }

    @Test
    fun `should validate color format`() {
        val outputFile = tempFile.resolve("qr.png")
        val result = command.test(listOf("test", "--output", outputFile.absolutePath, "-b", "invalid"))

        assertEquals(1, result.statusCode)
        assert(result.stderr.contains("invalid value for -b", ignoreCase = true))
    }

    @Test
    fun `should delegate QR generation to service`() {
        val outputFile = tempFile.resolve("qr.png")
        command.test(listOf("test", "--output", outputFile.absolutePath))

        verify(mockQrCodeWriterService).writeQrCode(
            content = "test",
            outputFile = outputFile,
            backgroundColorRgba = 0xFFFFFFFF.toInt(),
            foregroundColorRgba = 0xFF000000.toInt(),
        )
    }
}
