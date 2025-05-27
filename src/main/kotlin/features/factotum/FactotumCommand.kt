package org.factotum.features.factotum

import com.github.ajalt.clikt.core.CliktCommand

const val FACTOTUM_COMMAND_NAME = "factotum"

class FactotumCommand : CliktCommand(name = FACTOTUM_COMMAND_NAME) {
    override fun run() = Unit
}