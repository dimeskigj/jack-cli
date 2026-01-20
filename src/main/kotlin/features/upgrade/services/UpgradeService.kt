package org.jack.features.upgrade.services

interface UpgradeService {
    fun getCurrentVersion(): String

    fun getLatestVersion(): String
}
