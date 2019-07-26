package com.kgeezy.landclaim.event.player

import com.kgeezy.landclaim.manager.PlayerClaimManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class PlayerQuitListener: Listener {
    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        PlayerClaimManager.getInstance().removeActive(event.player)
    }
}