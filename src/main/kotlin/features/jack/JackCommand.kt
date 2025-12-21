package org.jack.features.jack

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.versionOption
import java.util.Properties

const val JACK_COMMAND_NAME = "jack"
const val VERSION_OPTION_LONG = "--version"
const val VERSION_OPTION_SHORT = "-v"
const val VERSION_PROPERTIES_PATH = "/version.properties"
const val VERSION_PROPERTY_KEY = "version"
const val UNKNOWN_VERSION = "unknown"

class JackCommand : CliktCommand(name = JACK_COMMAND_NAME) {
    init {
        versionOption(getVersion(), names = setOf(VERSION_OPTION_LONG, VERSION_OPTION_SHORT))
    }

    override fun run() = Unit

    private fun getVersion(): String =
        try {
            val props = Properties()
            props.load(this::class.java.getResourceAsStream(VERSION_PROPERTIES_PATH))
            props.getProperty(VERSION_PROPERTY_KEY) ?: UNKNOWN_VERSION
        } catch (e: Exception) {
            UNKNOWN_VERSION
        }
}
