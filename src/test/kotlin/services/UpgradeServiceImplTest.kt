package services

import org.jack.features.upgrade.services.impl.UpgradeServiceImpl
import org.junit.jupiter.api.Test
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class UpgradeServiceImplTest {
    private val service = UpgradeServiceImpl()

    @Test
    fun `should get current version from properties`() {
        val version = service.getCurrentVersion()

        assertNotEquals("unknown", version)
        assertTrue(version.isNotBlank())
    }

    @Test
    fun `should fetch latest version from github`() {
        val version = service.getLatestVersion()

        // Should either be a valid version or a failure message
        assertTrue(
            version.matches(Regex("""\d+\.\d+\.\d+.*""")) ||
                version.contains("unknown"),
        )
    }
}
