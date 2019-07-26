package com.kgeezy.landclaim.event

import com.kgeezy.landclaim.land.Claim
import com.kgeezy.landclaim.manager.PlayerClaimManager
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent

class PlayerMoveListener: Listener {
    private val claimManager = PlayerClaimManager.getInstance()

    private val pcMap: Map<Player, List<Claim>>
        get() = claimManager.getMap()

    @EventHandler
    fun playerMove(event: PlayerMoveEvent) {
        if (claimManager.hasClaim(event.player)) {
            val claims = pcMap[event.player]
            claims?.forEach { claim ->
                claimManager.pcFromClaim(claim)?.playerMove()
            }
        }
    }
}