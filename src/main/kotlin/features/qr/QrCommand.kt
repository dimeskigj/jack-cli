package org.jack.features.qr

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.parameters.options.*
import com.github.ajalt.clikt.parameters.types.file
import features.qr.utils.toRgbaColorCode
import org.jack.features.qr.services.QrCodeWriterService

const val QR_COMMAND_NAME = "qr"
const val QR_COMMAND_HELP = "Generate a QR code"
const val QR_INPUT_NAME = "--input"
const val QR_INPUT_NAME_SHORT = "-i"
const val QR_OUTPUT_NAME = "--output"
const val QR_OUTPUT_NAME_SHORT = "-o"
const val QR_OUTPUT_HELP = "Output file to write the QR code image to in PNG format"
const val QR_BACKGROUND_COLOR_NAME = "--backgroundColor"
const val QR_BACKGROUND_COLOR_NAME_SHORT = "-bg"
const val QR_BACKGROUND_COLOR_HELP = "Background color in hex format, supports RGB and ARGB ex: 'FFFFFFFF'"
const val QR_FOREGROUND_COLOR_NAME = "--foregroundColor"
const val QR_FOREGROUND_COLOR_NAME_SHORT = "-fg"
const val QR_FOREGROUND_COLOR_HELP = "Background color in hex format, supports RGB and ARGB ex: 'FF000000'"

class QrCommand(private val qrCodeWriterService: QrCodeWriterService) : CliktCommand(name = QR_COMMAND_NAME) {
    private val input by option(QR_INPUT_NAME, QR_INPUT_NAME_SHORT).required()

    private val output by option(QR_OUTPUT_NAME, QR_OUTPUT_NAME_SHORT)
        .help(QR_OUTPUT_HELP)
        .file(canBeDir = false).required()

    private val backgroundColor by option(QR_BACKGROUND_COLOR_NAME, QR_BACKGROUND_COLOR_NAME_SHORT)
        .help(QR_BACKGROUND_COLOR_HELP)
        .convert { it.toRgbaColorCode() }
        .default(0xFFFFFFFF.toInt())

    private val foregroundColor by option(QR_FOREGROUND_COLOR_NAME, QR_FOREGROUND_COLOR_NAME_SHORT)
        .help(QR_FOREGROUND_COLOR_HELP)
        .convert { it.toRgbaColorCode() }
        .default(0xFF000000.toInt())

    override fun help(context: Context): String = QR_COMMAND_HELP

    override fun run() {
        qrCodeWriterService.writeQrCode(
            content = input,
            outputFile = output,
            backgroundColorRgba = backgroundColor,
            foregroundColorRgba = foregroundColor
        )
    }
}