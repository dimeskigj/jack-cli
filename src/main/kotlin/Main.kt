package org.jack

import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.core.subcommands
import org.jack.features.hash.HashCommand
import org.jack.features.hash.services.impl.HashServiceImpl
import org.jack.features.jack.JackCommand
import org.jack.features.json.JsonCommand
import org.jack.features.json.services.impl.JsonServiceImpl
import org.jack.features.jwt.JwtCommand
import org.jack.features.jwt.services.impl.JwtServiceImpl
import org.jack.features.lorem.LoremCommand
import org.jack.features.lorem.services.impl.LoremIpsumServiceImpl
import org.jack.features.qr.QrCommand
import org.jack.features.qr.services.impl.QrCodeWriterServiceImpl
import org.jack.features.timestamp.TimestampCommand
import org.jack.features.timestamp.services.impl.TimestampServiceImpl
import org.jack.features.uuid.UuidCommand
import org.jack.features.uuid.services.impl.UuidServiceImpl
import org.jack.features.uuid.subcommands.GenerateCommand
import org.jack.features.uuid.subcommands.ValidateCommand

fun main(args: Array<String>) {
    val uuidService = UuidServiceImpl()
    val loremIpsumService = LoremIpsumServiceImpl()
    val qrCodeWriterService = QrCodeWriterServiceImpl()
    val timestampService = TimestampServiceImpl()
    val hashService = HashServiceImpl()
    val jwtService = JwtServiceImpl()
    val jsonService = JsonServiceImpl()

    val uuidCommand =
        UuidCommand().subcommands(
            GenerateCommand(uuidService),
            ValidateCommand(uuidService),
        )

    val loremCommand = LoremCommand(loremIpsumService)
    val qrCommand = QrCommand(qrCodeWriterService)
    val timestampCommand = TimestampCommand(timestampService)
    val hashCommand = HashCommand(hashService)
    val jwtCommand = JwtCommand(jwtService)
    val jsonCommand = JsonCommand(jsonService)

    JackCommand()
        .subcommands(
            uuidCommand,
            loremCommand,
            qrCommand,
            timestampCommand,
            hashCommand,
            jwtCommand,
            jsonCommand,
        ).main(args)
}
