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
            val claimsMap = PlayerClaimManager.getInstance().getMap()
            val playerClaims = PlayerClaimManager.getInstance().getPlayerClaims()
            print("Claim map size = ${claimsMap.size}, number of Claims in memory = ${playerClaims.size}")

        }, 0, 60L)
    }
}