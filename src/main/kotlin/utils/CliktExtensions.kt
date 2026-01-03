package org.jack.utils

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.terminal

/**
 * Helper to read input from a positional argument, or fall back to stdin
 * if the argument is missing and the terminal is not interactive (piped input).
 */
fun CliktCommand.readArgumentOrStdin(argument: String?): String? {
    // 1. Prefer the direct argument if provided
    if (argument != null) return argument

    // 2. If interactive (user typing), don't hang waiting for stdin
    if (terminal.terminalInfo.inputInteractive) return null

    // 3. Read all lines from stdin (piped input)
    return generateSequence { terminal.readLineOrNull(false) }
        .joinToString("\n")
        .trim()
        .takeIf { it.isNotEmpty() }
}
