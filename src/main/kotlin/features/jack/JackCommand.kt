package org.jack.features.jack

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.core.UsageError
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.output.HelpFormatter
import com.github.ajalt.clikt.output.MordantHelpFormatter
import com.github.ajalt.clikt.parameters.options.versionOption
import com.github.ajalt.mordant.rendering.TextColors
import java.util.Properties

const val JACK_COMMAND_NAME = "jack"
const val VERSION_OPTION_LONG = "--version"
const val VERSION_OPTION_SHORT = "-v"
const val VERSION_PROPERTIES_PATH = "/version.properties"
const val VERSION_PROPERTY_KEY = "version"
const val UNKNOWN_VERSION = "unknown"
const val COMPLETION_ENV_VAR = "_JACK_COMPLETE"

private val LOGO_LINES =
    listOf(
        """_____________________________ __""",
        """______  /__    |_  ____/__  //_/""",
        """___ _  /__  /| |  /    __  ,<   """,
        """/ /_/ / _  ___ / /___  _  /| |  """,
        """\____/  /_/  |_\____/  /_/ |_|  """,
    )

class JackCommand : CliktCommand(name = JACK_COMMAND_NAME) {
    override val autoCompleteEnvvar: String = COMPLETION_ENV_VAR

    init {
        versionOption(getVersion(), names = setOf(VERSION_OPTION_LONG, VERSION_OPTION_SHORT))
        context {
            helpFormatter = { ctx ->
                object : MordantHelpFormatter(ctx) {
                    override fun formatHelp(
                        error: UsageError?,
                        prolog: String,
                        epilog: String,
                        parameters: List<HelpFormatter.ParameterHelp>,
                        programName: String,
                    ): String {
                        val helpResult = super.formatHelp(error, prolog, epilog, parameters, programName)
                        if (context.command.commandName != JACK_COMMAND_NAME) {
                            return helpResult
                        }

                        val colors =
                            listOf(TextColors.magenta, TextColors.magenta, TextColors.brightCyan, TextColors.cyan, TextColors.brightBlue)
                        val banner = LOGO_LINES.zip(colors).joinToString("\n") { (line, color) -> color(line) }
                        return banner + "\n\n" + helpResult
                    }
                }
            }
        }
    }

    override fun help(context: Context) = "Jack of all trades CLI utility"

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
