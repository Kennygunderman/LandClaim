package com.kgeezy.landclaim.event.block

import com.kgeezy.landclaim.CANT_MODIFY_CLAIM
import com.kgeezy.landclaim.claim.Claim
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent

class BlockPlaceListener(antiBuildZone: Claim): BaseBlockModifiedListener(antiBuildZone), Listener {

    @EventHandler
    fun onBlockPlaced(event: BlockPlaceEvent) {
        if (!canPlayerModifyBlock(event.player, event.block)) {
            event.player.sendMessage(CANT_MODIFY_CLAIM)
            event.isCancelled = true
        }
    }
}