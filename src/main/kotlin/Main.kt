package org.jack

import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.core.subcommands
import org.jack.di.AppModule
import org.jack.features.autocomplete.AutocompleteCommand
import org.jack.features.base64.Base64Command
import org.jack.features.base64.subcommands.DecodeCommand
import org.jack.features.base64.subcommands.EncodeCommand
import org.jack.features.cron.CronCommand
import org.jack.features.cron.subcommands.ExplainCommand
import org.jack.features.cron.subcommands.NextCommand
import org.jack.features.hash.HashCommand
import org.jack.features.jack.JackCommand
import org.jack.features.json.JsonCommand
import org.jack.features.jwt.JwtCommand
import org.jack.features.lorem.LoremCommand
import org.jack.features.net.NetCommand
import org.jack.features.net.subcommands.DnsCommand
import org.jack.features.net.subcommands.IpCommand
import org.jack.features.qr.QrCommand
import org.jack.features.timestamp.TimestampCommand
import org.jack.features.upgrade.UpgradeCommand
import org.jack.features.uuid.UuidCommand
import org.jack.features.uuid.subcommands.ValidateCommand
import org.jack.features.uuid.subcommands.GenerateCommand as UuidGenerateCommand

fun main(args: Array<String>) {
    val uuidCommand =
        UuidCommand().subcommands(
            UuidGenerateCommand(AppModule.uuidService),
            ValidateCommand(AppModule.uuidService),
        )

    val loremCommand = LoremCommand(AppModule.loremIpsumService)
    val qrCommand = QrCommand(AppModule.qrCodeWriterService)
    val timestampCommand = TimestampCommand(AppModule.timestampService)
    val hashCommand = HashCommand(AppModule.hashService)
    val jwtCommand = JwtCommand(AppModule.jwtService)
    val jsonCommand = JsonCommand(AppModule.jsonService)
    val upgradeCommand = UpgradeCommand(AppModule.upgradeService)
    val netCommand =
        NetCommand(AppModule.netService).subcommands(
            IpCommand(AppModule.netService),
            DnsCommand(AppModule.netService),
        )
    val base64Command =
        Base64Command(AppModule.base64Service).subcommands(
            EncodeCommand(AppModule.base64Service),
            DecodeCommand(AppModule.base64Service),
        )
    val cronCommand =
        CronCommand().subcommands(
            ExplainCommand(AppModule.cronService),
            NextCommand(AppModule.cronService),
        )
    val completionCommand = AutocompleteCommand()

    val jackCommand =
        JackCommand()
            .subcommands(
                uuidCommand,
                loremCommand,
                qrCommand,
                timestampCommand,
                hashCommand,
                jwtCommand,
                jsonCommand,
                upgradeCommand,
                netCommand,
                base64Command,
                cronCommand,
                completionCommand,
            )

    jackCommand.main(args)
}
