package org.jack.features.jack

import com.github.ajalt.clikt.core.CliktCommand

const val JACK_COMMAND_NAME = "jack"

class JackCommand : CliktCommand(name = JACK_COMMAND_NAME) {
    override fun run() = Unit
}