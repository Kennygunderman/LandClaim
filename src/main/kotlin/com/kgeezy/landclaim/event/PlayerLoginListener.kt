package com.kgeezy.landclaim.event

import com.kgeezy.landclaim.land.ClaimFile
import com.kgeezy.landclaim.manager.PlayerClaimManager
import com.kgeezy.landclaim.player.ClaimListener
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerLoginEvent

class PlayerLoginListener(private val claimFile: ClaimFile, private val listener: ClaimListener) : Listener {

    @EventHandler
    fun playerLoginEvent(event: PlayerLoginEvent) {
        val claims = claimFile.getClaims(event.player)

        if (!claims.isEmpty()) {
            PlayerClaimManager.getInstance().add(event.player, claims, listener)
        }
    }
}