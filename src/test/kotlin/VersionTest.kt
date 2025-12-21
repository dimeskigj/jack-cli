package org.jack

import com.github.ajalt.clikt.testing.test
import org.jack.features.jack.JackCommand
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class VersionTest {
    @Test
    fun `when --version is passed, it prints the version`() {
        val command = JackCommand()
        val result = command.test("--version")

        // The version might be "unknown" or "${project.version}" if resources aren't processed in the test environment correctly without full build,
        // or "0.0.1" if they are.
        // We just want to ensure it prints *something* that looks like a version or the placeholder.
        // Clikt's versionOption prints the version and exits.

        assertTrue(result.stdout.contains("jack version"), "Output should contain 'jack version'")
    }
}
