package org.factotum

import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.core.subcommands
import org.factotum.features.factotum.FactotumCommand
import org.factotum.features.uuid.UuidCommand
import org.factotum.features.uuid.services.impl.UuidServiceImpl

val uuidService = UuidServiceImpl()
val uuidCommand = UuidCommand(uuidService)

fun main(args: Array<String>) = FactotumCommand()
    .subcommands(uuidCommand)
    .main(args)