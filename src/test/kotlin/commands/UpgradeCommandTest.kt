package commands

import com.github.ajalt.clikt.testing.test
import org.jack.features.upgrade.UpgradeCommand
import org.jack.features.upgrade.services.UpgradeService
import org.junit.jupiter.api.Test
import kotlin.test.assertContains
import kotlin.test.assertFalse

class UpgradeCommandTest {
    @Test
    fun `should show current and latest version`() {
        val service = FakeUpgradeService(currentVersion = "1.0.0", latestVersion = "1.1.0")
        val command = UpgradeCommand(service)

        val result = command.test(emptyList())

        assertContains(result.stdout, "Current version: 1.0.0")
        assertContains(result.stdout, "Latest version:  1.1.0")
    }

    @Test
    fun `should show upgrade instructions when update available`() {
        val service = FakeUpgradeService(currentVersion = "1.0.0", latestVersion = "1.1.0")
        val command = UpgradeCommand(service)

        val result = command.test(emptyList())

        assertContains(result.stdout, "To upgrade jack-cli, run:")
    }

    @Test
    fun `should show already on latest when versions match`() {
        val service = FakeUpgradeService(currentVersion = "1.1.0", latestVersion = "1.1.0")
        val command = UpgradeCommand(service)

        val result = command.test(emptyList())

        assertContains(result.stdout, "You are on the latest version.")
        assertFalse(result.stdout.contains("To upgrade jack-cli, run:"))
    }

    @Test
    fun `should show windows instructions on windows`() {
        withOsName("Windows 10") {
            val service = FakeUpgradeService(currentVersion = "1.0.0", latestVersion = "1.1.0")
            val command = UpgradeCommand(service)

            val result = command.test(emptyList())

            assertContains(result.stdout, "iwr")
            assertContains(result.stdout, "install.ps1")
        }
    }

    @Test
    fun `should show unix instructions on linux`() {
        withOsName("Linux") {
            val service = FakeUpgradeService(currentVersion = "1.0.0", latestVersion = "1.1.0")
            val command = UpgradeCommand(service)

            val result = command.test(emptyList())

            assertContains(result.stdout, "curl")
            assertContains(result.stdout, "install.sh")
        }
    }

    private fun withOsName(
        osName: String,
        block: () -> Unit,
    ) {
        val originalOs = System.getProperty("os.name")
        try {
            System.setProperty("os.name", osName)
            block()
        } finally {
            System.setProperty("os.name", originalOs)
        }
    }

    private class FakeUpgradeService(
        private val currentVersion: String,
        private val latestVersion: String,
    ) : UpgradeService {
        override fun getCurrentVersion(): String = currentVersion

        override fun getLatestVersion(): String = latestVersion
    }
}
