package org.jack.features.upgrade

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context
import org.jack.features.upgrade.services.UpgradeService

const val UPGRADE_COMMAND_NAME = "upgrade"
const val UPGRADE_HELP = "Show instructions to upgrade jack-cli"
const val INSTALL_SCRIPT_URL = "https://raw.githubusercontent.com/dimeskigj/jack-cli/main/scripts/install.sh"
const val INSTALL_SCRIPT_WINDOWS_URL = "https://raw.githubusercontent.com/dimeskigj/jack-cli/main/scripts/install.ps1"

class UpgradeCommand(
    private val upgradeService: UpgradeService,
) : CliktCommand(name = UPGRADE_COMMAND_NAME) {
    override fun help(context: Context) = UPGRADE_HELP

    override fun run() {
        val currentVersion = upgradeService.getCurrentVersion()
        val latestVersion = upgradeService.getLatestVersion()

        echo("Current version: $currentVersion")
        echo("Latest version:  $latestVersion")

        if (currentVersion == latestVersion) {
            echo("\nYou are on the latest version.")
            return
        }

        val isWindows = System.getProperty("os.name").lowercase().contains("win")

        echo("\nTo upgrade jack-cli, run:")
        if (isWindows) {
            echo("  iwr $INSTALL_SCRIPT_WINDOWS_URL -useb | iex")
        } else {
            echo("  curl -fsSL $INSTALL_SCRIPT_URL | bash")
        }
    }
}
