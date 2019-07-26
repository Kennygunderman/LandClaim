package com.kgeezy.landclaim.util

import com.kgeezy.landclaim.manager.PlayerClaimManager
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin

/**
 * Monitor the claims in memory, should eventually clean up claims if an issue
 */
object ClaimMonitor {
    fun monitorClaims(plugin: Plugin) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, {
            val claimsMap = PlayerClaimManager.getInstance().getActiveMap()
            val playerClaims = PlayerClaimManager.getInstance().getActivePlayerClaims()
            val all = PlayerClaimManager.getInstance().getAllPlayerClaims()

            print("Active Claim map size = ${claimsMap.size}, " +
                    "active number of Claims in memory = ${playerClaims.size}, " +
                    "all Claims in memory = ${all.size}")

        }, 0, 60L)
    }
}