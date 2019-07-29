package com.kgeezy.landclaim.event.player

import com.kgeezy.landclaim.claim.Claim
import com.kgeezy.landclaim.manager.PlayerClaimManager
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent

class PlayerMoveListener(val antiBuildZone: Claim): Listener {
    private val claimManager = PlayerClaimManager.getInstance()

    private val pcMap: Map<Player, List<Claim>>
        get() = claimManager.getActiveMap()

    @EventHandler
    fun onPlayerMove(event: PlayerMoveEvent) {

       // if (antiBuildZone.isInClaim(event.player.x))


        if (claimManager.hasClaim(event.player)) {
            val claims = pcMap[event.player]
            claims?.forEach { claim ->
                claimManager.pcFromClaim(claim)?.playerMove()
            }
        }
    }
}