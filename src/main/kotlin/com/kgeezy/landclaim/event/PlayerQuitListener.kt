package com.kgeezy.landclaim.event

import com.kgeezy.landclaim.manager.PlayerClaimManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class PlayerQuitListener: Listener {
    @EventHandler
    fun playerQuitEvent(event: PlayerQuitEvent) {
        PlayerClaimManager.getInstance().remove(event.player)
    }
}