package com.kgeezy.landclaim.event.block

import com.kgeezy.landclaim.manager.PlayerClaimManager
import org.bukkit.block.Block
import org.bukkit.entity.Player

open class BaseBlockModifiedListener {
    fun canPlayerModifyBlock(player: Player, block: Block): Boolean {
        PlayerClaimManager.getInstance().getAllPlayerClaims().forEach { pc ->
            val blockLoc = block.location
            if (pc.claim.isInClaim(blockLoc.x.toInt(), blockLoc.z.toInt())) {
                if (pc.playerName != player.name) {
                    return false
                }
            }
        }
        return true
    }
}