package org.jack.features.qr.services.impl

import org.jack.features.qr.services.QrCodeWriterService
import qrcode.QRCode
import qrcode.QRCodeShapesEnum
import java.io.File
import java.io.FileOutputStream

class QrCodeWriterServiceImpl : QrCodeWriterService {
    override fun writeQrCode(
        content: String,
        outputFile: File,
        backgroundColorRgba: Int,
        foregroundColorRgba: Int,
    ) {
        val qrCode =
            QRCode
                .ofSquares()
                .withShape(QRCodeShapesEnum.SQUARE)
                .withSize(25)
                .withInnerSpacing(0)
                .withBackgroundColor(backgroundColorRgba)
                .withColor(foregroundColorRgba)
                .build(content)

        val qrRender = qrCode.render()

        qrRender.writeImage(FileOutputStream(outputFile))
    }
}
