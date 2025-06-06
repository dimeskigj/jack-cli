package org.jack.features.qr.services

import java.io.File

interface QrCodeWriterService {
    fun writeQrCode(content: String, outputFile: File, backgroundColorRgba: Int, foregroundColorRgba: Int)
}