package org.factotum.features.uuid

import com.github.ajalt.clikt.core.CliktCommand
import org.factotum.features.uuid.services.UuidService


class UuidCommand(private val uuidService: UuidService) : CliktCommand() {
    override fun run() {
        echo(uuidService.randomUuid())
    }
}