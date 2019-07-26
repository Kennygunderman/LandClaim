package com.kgeezy.landclaim

import com.kgeezy.landclaim.land.Center
import com.kgeezy.landclaim.land.Claim
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent

class PlayerMoveListener: Listener {

    @EventHandler
    fun playerMove(event: PlayerMoveEvent) {
        val x = event.player.location.x.toInt()
        val y = event.player.location.z.toInt()

        if (playerHasClaim()) {

            val claim = getPlayerClaim()

            val isInClaim = claim.isInClaim(x, y)

        }
    }

    fun playerHasClaim() = true

    fun getPlayerClaim(): Claim = Claim(Center(20, 20), 8)
}