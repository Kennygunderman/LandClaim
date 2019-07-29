package com.kgeezy.landclaim.event.player

import com.kgeezy.landclaim.claim.ClaimFile
import com.kgeezy.landclaim.manager.PlayerClaimManager
import com.kgeezy.landclaim.player.ClaimListener
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerLoginEvent

class PlayerLoginListener(private val claimFile: ClaimFile, private val listener: ClaimListener) : Listener {

    @EventHandler
    fun onPlayerLogin(event: PlayerLoginEvent) {
        val claims = claimFile.getClaims(event.player)

        if (!claims.isEmpty()) {
            PlayerClaimManager.getInstance().addActive(event.player, claims, listener)
        }
    }
}