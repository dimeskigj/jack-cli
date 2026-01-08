package features.qr.utils

import java.io.ByteArrayOutputStream
import java.util.zip.CRC32
import java.util.zip.Deflater

/**
* The QR code package does not work with GraalVM, this is a manual implementation of the encoder as a workaround.
*/
object PngEncoder {
    private val SIGNATURE = byteArrayOf(
        0x89.toByte(), 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A,
    )
    private const val BIT_DEPTH: Byte = 8
    private const val COLOR_TYPE_RGBA: Byte = 6
    private const val FILTER_NONE: Byte = 0
    private const val COMPRESSION_DEFLATE: Byte = 0
    private const val INTERLACE_NONE: Byte = 0
    private const val IHDR_LENGTH = 13
    private const val DEFLATE_BUFFER_SIZE = 16 * 1024

    fun encode(width: Int, height: Int, pixelProvider: (x: Int, y: Int) -> Int): ByteArray {
        require(width > 0 && height > 0) { "Dimensions must be positive" }

        val scanlines = buildScanlines(width, height, pixelProvider)
        val compressed = deflate(scanlines)

        return ByteArrayOutputStream().use { out ->
            out.write(SIGNATURE)
            writeChunk(out, "IHDR", buildIhdr(width, height))
            writeChunk(out, "IDAT", compressed)
            writeChunk(out, "IEND", byteArrayOf())
            out.toByteArray()
        }
    }

    private fun buildScanlines(width: Int, height: Int, pixelProvider: (Int, Int) -> Int): ByteArray {
        val bytesPerPixel = 4
        val out = ByteArrayOutputStream(height * (1 + width * bytesPerPixel))
        for (y in 0 until height) {
            out.write(FILTER_NONE.toInt())
            for (x in 0 until width) {
                writeRgba(out, pixelProvider(x, y))
            }
        }
        return out.toByteArray()
    }

    private fun buildIhdr(width: Int, height: Int): ByteArray {
        val data = ByteArray(IHDR_LENGTH)
        writeIntBE(data, 0, width)
        writeIntBE(data, 4, height)
        data[8] = BIT_DEPTH
        data[9] = COLOR_TYPE_RGBA
        data[10] = COMPRESSION_DEFLATE
        data[11] = FILTER_NONE
        data[12] = INTERLACE_NONE
        return data
    }

    private fun writeChunk(out: ByteArrayOutputStream, type: String, data: ByteArray) {
        require(type.length == 4) { "PNG chunk type must be 4 chars" }

        val typeBytes = type.encodeToByteArray()
        writeIntBE(out, data.size)
        out.write(typeBytes)
        out.write(data)

        val crc32 = CRC32()
        crc32.update(typeBytes)
        crc32.update(data)
        writeIntBE(out, crc32.value.toInt())
    }

    private fun deflate(input: ByteArray): ByteArray {
        val deflater = Deflater(Deflater.DEFAULT_COMPRESSION)
        return try {
            deflater.setInput(input)
            deflater.finish()

            val buffer = ByteArray(DEFLATE_BUFFER_SIZE)
            val out = ByteArrayOutputStream(input.size / 2)
            while (!deflater.finished()) {
                val count = deflater.deflate(buffer)
                if (count > 0) out.write(buffer, 0, count)
            }
            out.toByteArray()
        } finally {
            deflater.end()
        }
    }

    private fun writeRgba(out: ByteArrayOutputStream, argb: Int) {
        val a = (argb ushr 24) and 0xFF
        val r = (argb ushr 16) and 0xFF
        val g = (argb ushr 8) and 0xFF
        val b = argb and 0xFF
        out.write(r)
        out.write(g)
        out.write(b)
        out.write(a)
    }

    private fun writeIntBE(out: ByteArrayOutputStream, value: Int) {
        out.write((value ushr 24) and 0xFF)
        out.write((value ushr 16) and 0xFF)
        out.write((value ushr 8) and 0xFF)
        out.write(value and 0xFF)
    }

    private fun writeIntBE(buf: ByteArray, offset: Int, value: Int) {
        buf[offset] = ((value ushr 24) and 0xFF).toByte()
        buf[offset + 1] = ((value ushr 16) and 0xFF).toByte()
        buf[offset + 2] = ((value ushr 8) and 0xFF).toByte()
        buf[offset + 3] = (value and 0xFF).toByte()
    }
}
