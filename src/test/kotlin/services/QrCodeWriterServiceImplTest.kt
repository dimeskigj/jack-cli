package services

import org.jack.features.qr.services.impl.QrCodeWriterServiceImpl
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class QrCodeWriterServiceImplTest {
    @TempDir
    lateinit var tempDir: File

    @Test
    fun `writes a valid PNG header`() {
        val outputFile = tempDir.resolve("qr.png")

        QrCodeWriterServiceImpl().writeQrCode(
            content = "hello",
            outputFile = outputFile,
            backgroundColorRgba = 0xFFFFFFFF.toInt(),
            foregroundColorRgba = 0xFF000000.toInt(),
        )

        val bytes = outputFile.readBytes()
        assertTrue(bytes.size > 8, "PNG file should not be empty")

        val signature = bytes.take(8).toByteArray()
        val expected =
            byteArrayOf(
                0x89.toByte(),
                0x50,
                0x4E,
                0x47,
                0x0D,
                0x0A,
                0x1A,
                0x0A,
            )
        assertEquals(expected.toList(), signature.toList())

        // First chunk should be IHDR (type bytes at offset 12..15)
        val chunkType = bytes.copyOfRange(12, 16).decodeToString()
        assertEquals("IHDR", chunkType)
    }
}
