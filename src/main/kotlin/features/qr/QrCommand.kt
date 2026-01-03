package org.jack.features.qr

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.help
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.file
import features.qr.utils.toRgbaColorCode
import org.jack.features.qr.services.QrCodeWriterService

const val QR_COMMAND_NAME = "qr"
const val QR_COMMAND_HELP = "Generate a QR code"
const val QR_INPUT_HELP = "The content to encode in the QR code"
const val QR_OUTPUT_NAME = "--output"
const val QR_OUTPUT_NAME_SHORT = "-o"
const val QR_OUTPUT_HELP = "Output file to write the QR code image to in PNG format"
const val QR_BACKGROUND_COLOR_NAME = "--backgroundColor"
const val QR_BACKGROUND_COLOR_NAME_SHORT = "-b"
const val QR_BACKGROUND_COLOR_HELP = "Background color in hex format, supports RGB and ARGB ex: 'FFFFFFFF'"
const val QR_FOREGROUND_COLOR_NAME = "--foregroundColor"
const val QR_FOREGROUND_COLOR_NAME_SHORT = "-f"
const val QR_FOREGROUND_COLOR_HELP = "Background color in hex format, supports RGB and ARGB ex: 'FF000000'"
const val DEFAULT_BACKGROUND_COLOR = 0xFFFFFFFF.toInt()
const val DEFAULT_FOREGROUND_COLOR = 0xFF000000.toInt()

class QrCommand(
    private val qrCodeWriterService: QrCodeWriterService,
) : CliktCommand(name = QR_COMMAND_NAME) {
    private val input by argument(help = QR_INPUT_HELP)

    private val output by option(QR_OUTPUT_NAME, QR_OUTPUT_NAME_SHORT)
        .help(QR_OUTPUT_HELP)
        .file(canBeDir = false)
        .required()

    private val backgroundColor by option(QR_BACKGROUND_COLOR_NAME, QR_BACKGROUND_COLOR_NAME_SHORT)
        .help(QR_BACKGROUND_COLOR_HELP)
        .convert { it.toRgbaColorCode() }
        .default(DEFAULT_BACKGROUND_COLOR)

    private val foregroundColor by option(QR_FOREGROUND_COLOR_NAME, QR_FOREGROUND_COLOR_NAME_SHORT)
        .help(QR_FOREGROUND_COLOR_HELP)
        .convert { it.toRgbaColorCode() }
        .default(DEFAULT_FOREGROUND_COLOR)

    override fun help(context: Context): String = QR_COMMAND_HELP

    override fun run() {
        qrCodeWriterService.writeQrCode(
            content = input,
            outputFile = output,
            backgroundColorRgba = backgroundColor,
            foregroundColorRgba = foregroundColor,
        )
        echo("QR code successfully generated at: ${output.absolutePath}")
    }
}
