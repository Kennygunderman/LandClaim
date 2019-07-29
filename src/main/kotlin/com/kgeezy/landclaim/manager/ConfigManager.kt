package com.kgeezy.landclaim.manager

import com.kgeezy.landclaim.claim.Center
import com.kgeezy.landclaim.claim.Claim

private const val CONFIG_SECTION = "config"
private const val CONFIG_PROTECTED_ZONE = "$CONFIG_SECTION.protected-zone"
private const val CONFIG_PROTECTED_CENTER_X_SECTION = "$CONFIG_PROTECTED_ZONE.center.x"
private const val CONFIG_PROTECTED_CENTER_Z_SECTION = "$CONFIG_PROTECTED_ZONE.center.z"
private const val CONFIG_PROTECTED_RADIUS = "$CONFIG_PROTECTED_ZONE.radius"

class ConfigManager(fileManager: FileManager) {

    private val yml = fileManager.configYml()
    private val config = fileManager.configFromYml(yml)

    init {
        generateDefaults()
    }


    val antiBuildZone: Claim
        get() = antiBuildZoneFromConfig()

    private val defaultAntiBuildZone = Claim(Center(0, 0), 0)

    /**
     * only gen yaml if config is null
     *
     * @param antiBuildZone - default config param
     */
    private fun generateDefaults(antiBuildZone: Claim = defaultAntiBuildZone) {
        if (config.get(CONFIG_SECTION) == null) {
            config.set(CONFIG_PROTECTED_CENTER_X_SECTION, antiBuildZone.center.x)
            config.set(CONFIG_PROTECTED_CENTER_Z_SECTION, antiBuildZone.center.z)
            config.set(CONFIG_PROTECTED_RADIUS, antiBuildZone.radius)
            config.save(yml)
        }
    }

    private fun antiBuildZoneFromConfig(): Claim {
        val centerX = config.get(CONFIG_PROTECTED_CENTER_X_SECTION) as? Int
        val centerZ = config.get(CONFIG_PROTECTED_CENTER_Z_SECTION) as? Int
        val radius = config.get(CONFIG_PROTECTED_RADIUS) as? Int

        return if (centerX != null && centerZ != null && radius != null) {
            Claim(Center(centerX, centerZ), radius)
        } else {
            defaultAntiBuildZone
        }
    }
}