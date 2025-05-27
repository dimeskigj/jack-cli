package org.factotum

import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.core.subcommands
import org.factotum.features.factotum.FactotumCommand
import org.factotum.features.lorem.LoremCommand
import org.factotum.features.lorem.services.impl.LoremIpsumServiceImpl
import org.factotum.features.uuid.UuidCommand
import org.factotum.features.uuid.services.impl.UuidServiceImpl

val uuidService = UuidServiceImpl()
val loremIpsumService = LoremIpsumServiceImpl()

val uuidCommand = UuidCommand(uuidService)
val loremCommand = LoremCommand(loremIpsumService)

fun main(args: Array<String>) = FactotumCommand()
    .subcommands(uuidCommand, loremCommand)
    .main(args)