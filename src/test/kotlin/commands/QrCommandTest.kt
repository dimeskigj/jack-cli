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
        val result = command.test("--input test --output ${tempFile.resolve("qr.png")}")
        assertEquals("", result.stdout)
        assertEquals("", result.stderr)
        assertEquals(0, result.statusCode)
    }

    @Test
    fun `should fail when required parameters are missing`() {
        val result = command.test()
        assertEquals(1, result.statusCode)
        assert(result.stderr.contains("Missing option --input", ignoreCase = true))
        assert(result.stderr.contains("Missing option --output", ignoreCase = true))
    }

    @Test
    fun `should use default colors when not specified`() {
        val outputFile = tempFile.resolve("qr.png")
        command.test("--input test --output $outputFile")

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
        command.test("--input test --output $outputFile --backgroundColor 00FF00 --foregroundColor FF0000FF")

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
        val result = command.test("--input test --output $outputFile --backgroundColor invalid")

        assertEquals(1, result.statusCode)
        assert(result.stderr.contains("invalid value for --backgroundColor", ignoreCase = true))
    }

    @Test
    fun `should delegate QR generation to service`() {
        val outputFile = tempFile.resolve("qr.png")
        command.test("--input test --output $outputFile")

        verify(mockQrCodeWriterService).writeQrCode(
            content = "test",
            outputFile = outputFile,
            backgroundColorRgba = 0xFFFFFFFF.toInt(),
            foregroundColorRgba = 0xFF000000.toInt(),
        )
    }
}
