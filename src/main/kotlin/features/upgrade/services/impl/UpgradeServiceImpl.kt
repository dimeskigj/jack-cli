package org.jack.features.upgrade.services.impl

import org.jack.features.upgrade.services.UpgradeService
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration

const val GITHUB_RELEASES_API_URL = "https://api.github.com/repos/dimeskigj/jack-cli/releases/latest"
const val VERSION_PROPERTIES_FILE = "version.properties"
const val VERSION_PREFIX = "version="
const val UNKNOWN_VERSION = "unknown"
const val FETCH_FAILED_VERSION = "unknown (failed to fetch)"
const val HTTP_TIMEOUT_SECONDS = 5L
const val GITHUB_ACCEPT_HEADER = "application/vnd.github.v3+json"

class UpgradeServiceImpl : UpgradeService {
    private val httpClient: HttpClient by lazy {
        HttpClient
            .newBuilder()
            .connectTimeout(Duration.ofSeconds(HTTP_TIMEOUT_SECONDS))
            .build()
    }

    override fun getCurrentVersion(): String =
        javaClass.classLoader
            .getResourceAsStream(VERSION_PROPERTIES_FILE)
            ?.bufferedReader()
            ?.readText()
            ?.substringAfter(VERSION_PREFIX)
            ?.trim()
            ?: UNKNOWN_VERSION

    override fun getLatestVersion(): String =
        try {
            val request =
                HttpRequest
                    .newBuilder()
                    .uri(URI.create(GITHUB_RELEASES_API_URL))
                    .header("Accept", GITHUB_ACCEPT_HEADER)
                    .timeout(Duration.ofSeconds(HTTP_TIMEOUT_SECONDS))
                    .GET()
                    .build()

            val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())

            if (response.statusCode() == 200) {
                parseTagName(response.body())
            } else {
                System.err.println("Failed to fetch latest version: HTTP $responseCode")
                FETCH_FAILED_VERSION
            }
        } catch (e: Exception) {
            System.err.println("Error fetching latest version: ${e.message}")
            FETCH_FAILED_VERSION
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
