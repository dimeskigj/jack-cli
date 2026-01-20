package services

import org.jack.features.upgrade.services.impl.UpgradeServiceImpl
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
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

    @Test
    fun `should parse tag name from github api response`() {
        // Use reflection to test the private parseTagName method
        val parseMethod =
            UpgradeServiceImpl::class.java.getDeclaredMethod(
                "parseTagName",
                String::class.java,
            )
        parseMethod.isAccessible = true

        val json = """{"tag_name": "v1.2.3", "other": "field"}"""
        val result = parseMethod.invoke(service, json) as String

        assertEquals("1.2.3", result)
    }

    @Test
    fun `should parse tag name without v prefix`() {
        val parseMethod =
            UpgradeServiceImpl::class.java.getDeclaredMethod(
                "parseTagName",
                String::class.java,
            )
        parseMethod.isAccessible = true

        val json = """{"tag_name": "2.0.0", "other": "field"}"""
        val result = parseMethod.invoke(service, json) as String

        assertEquals("2.0.0", result)
    }

    @Test
    fun `should return unknown for invalid json`() {
        val parseMethod =
            UpgradeServiceImpl::class.java.getDeclaredMethod(
                "parseTagName",
                String::class.java,
            )
        parseMethod.isAccessible = true

        val json = """{"no_tag": "here"}"""
        val result = parseMethod.invoke(service, json) as String

        assertEquals("unknown", result)
    }
}
