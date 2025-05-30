package org.jack

import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.core.subcommands
import org.jack.features.jack.JackCommand
import org.jack.features.lorem.LoremCommand
import org.jack.features.lorem.services.impl.LoremIpsumServiceImpl
import org.jack.features.uuid.UuidCommand
import org.jack.features.uuid.services.impl.UuidServiceImpl

val uuidService = UuidServiceImpl()
val loremIpsumService = LoremIpsumServiceImpl()

val uuidCommand = UuidCommand(uuidService)
val loremCommand = LoremCommand(loremIpsumService)

fun main(args: Array<String>) = JackCommand()
    .subcommands(uuidCommand, loremCommand)
    .main(args)