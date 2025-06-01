package org.jack.features.qr

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.options.validate
import com.github.ajalt.clikt.parameters.types.file
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import qrcode.QRCode
import qrcode.QRCodeShapesEnum
import qrcode.color.Colors
import qrcode.shape.QRCodeShapeFunction
import java.io.FileOutputStream


class QrCommand : CliktCommand(name = "qr") {
    private val input by argument()

    private val output by option("--output", "-o").file(canBeDir = false).required()

    private val backgroundColor by option("--backgroundColor", "-bg")
        .convert {
            when (it.length) {
                6 -> "FF$it".toLong(radix = 16)
                8 -> it.toLong(radix = 16)
                else -> error("Invalid color length: $it")
            }.toInt()
        }
        .default(0xFFFFFFFF.toInt())

    private val foregroundColor by option("--foregroundColor", "-fg")
        .convert {
            when (it.length) {
                6 -> "FF$it".toLong(radix = 16)
                8 -> it.toLong(radix = 16)
                else -> error("Invalid color length: $it")
            }.toInt()
        }
        .default(0xFF000000.toInt())

    @Volatile
    var isBusy = false

    override fun help(context: Context): String = "Generate a .png QR code from <input> text"

    override fun run() = runBlocking {
        val spinnerChars = listOf('|', '/', '*', '\\')

        val job = launch(Dispatchers.Default) {
            var index = 0
            while (isActive) {
                echo("${spinnerChars[index]}\r", trailingNewline = false)
                index = (index + 1) % spinnerChars.size
                delay(100)
            }
        }

        try {
            withContext(Dispatchers.IO) {
                val qrCode = QRCode.ofSquares()
                    .withBackgroundColor(backgroundColor)
                    .withColor(foregroundColor)
                    .withSize(10)
                    .withMargin(0)
                    .build(input)

                val qrRender = qrCode.render()

                FileOutputStream(output).use {
                    it.write(qrRender.getBytes())
                }
            }
        } finally {
            job.cancelAndJoin()
        }
    }
}