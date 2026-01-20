package org.jack.features.autocomplete

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context

const val COMPLETION_COMMAND_NAME = "completion"
const val COMPLETION_COMMAND_HELP = "Show shell completion setup instructions"

class AutocompleteCommand : CliktCommand(name = COMPLETION_COMMAND_NAME) {
    override fun help(context: Context): String = COMPLETION_COMMAND_HELP

    override fun run() {
        echo("Shell completion setup for jack:")
        echo()
        echo("Bash:")
        echo("  echo 'eval \"$(_JACK_COMPLETE=bash jack)\"' >> ~/.bashrc")
        echo("  source ~/.bashrc")
        echo()
        echo("Zsh:")
        echo("  echo 'eval \"$(_JACK_COMPLETE=zsh jack)\"' >> ~/.zshrc")
        echo("  source ~/.zshrc")
        echo()
        echo("Fish:")
        echo("  _JACK_COMPLETE=fish jack > ~/.config/fish/completions/jack.fish")
        echo()
        echo("The completion will be generated dynamically each time your shell starts.")
    }
}
