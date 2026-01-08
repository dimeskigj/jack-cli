package org.jack.features.qr.services.impl

import features.qr.utils.PngEncoder
import org.jack.features.qr.services.QrCodeWriterService
import qrcode.raw.QRCodeProcessor
import java.io.File
import java.io.FileOutputStream

class QrCodeWriterServiceImpl : QrCodeWriterService {
    companion object {
        private const val CELL_SIZE = 25
    }

    override fun writeQrCode(
        content: String,
        outputFile: File,
        backgroundColorRgba: Int,
        foregroundColorRgba: Int,
    ) {
        val rawData = QRCodeProcessor(content).encode()
        val moduleCount = rawData.size
        val imageSize = moduleCount * CELL_SIZE

        val pngBytes =
            PngEncoder.encode(imageSize, imageSize) { x, y ->
                val row = y / CELL_SIZE
                val col = x / CELL_SIZE
                if (rawData[row][col].dark) foregroundColorRgba else backgroundColorRgba
            }

        FileOutputStream(outputFile).use { it.write(pngBytes) }
    }
}
