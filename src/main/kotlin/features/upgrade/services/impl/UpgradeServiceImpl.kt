package org.jack.features.upgrade.services.impl

import org.jack.features.upgrade.services.UpgradeService
import java.net.HttpURLConnection
import java.net.URI

const val GITHUB_RELEASES_API_URL = "https://api.github.com/repos/dimeskigj/jack-cli/releases/latest"
const val VERSION_PROPERTIES_FILE = "version.properties"
const val VERSION_PREFIX = "version="
const val UNKNOWN_VERSION = "unknown"
const val FETCH_FAILED_VERSION = "unknown (failed to fetch)"
const val HTTP_TIMEOUT_MS = 5000
const val GITHUB_ACCEPT_HEADER = "application/vnd.github.v3+json"

class UpgradeServiceImpl : UpgradeService {
    override fun getCurrentVersion(): String =
        javaClass.classLoader
            .getResourceAsStream(VERSION_PROPERTIES_FILE)
            ?.bufferedReader()
            ?.readText()
            ?.substringAfter(VERSION_PREFIX)
            ?.trim()
            ?: UNKNOWN_VERSION

    override fun getLatestVersion(): String {
        var connection: HttpURLConnection? = null
        return try {
            connection = URI(GITHUB_RELEASES_API_URL).toURL().openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.setRequestProperty("Accept", GITHUB_ACCEPT_HEADER)
            connection.setRequestProperty("User-Agent", "jack-cli")
            connection.connectTimeout = HTTP_TIMEOUT_MS
            connection.readTimeout = HTTP_TIMEOUT_MS

            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val response = connection.inputStream.bufferedReader().use { it.readText() }
                parseTagName(response)
            } else {
                System.err.println("Failed to fetch latest version: HTTP $responseCode")
                FETCH_FAILED_VERSION
            }
        } catch (e: Exception) {
            System.err.println("Error fetching latest version: ${e.message}")
            FETCH_FAILED_VERSION
        } finally {
            connection?.disconnect()
        }
    }

    private fun parseTagName(json: String): String {
        val regex = """"tag_name"\s*:\s*"([^"]+)"""".toRegex()
        return regex
            .find(json)
            ?.groupValues
            ?.get(1)
            ?.removePrefix("v") ?: UNKNOWN_VERSION
    }
}
