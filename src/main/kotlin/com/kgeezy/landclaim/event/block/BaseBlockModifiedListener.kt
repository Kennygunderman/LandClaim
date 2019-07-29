package com.kgeezy.landclaim.event.block

import com.kgeezy.landclaim.claim.Claim
import com.kgeezy.landclaim.manager.PlayerClaimManager
import org.bukkit.block.Block
import org.bukkit.entity.Player

open class BaseBlockModifiedListener(private val antiBuildZone: Claim) {
    fun canPlayerModifyBlock(player: Player, block: Block): Boolean {
        val blockLoc = block.location
        if (antiBuildZone.isInClaim(blockLoc.x.toInt(), blockLoc.z.toInt())) {
            return false
        }

        PlayerClaimManager.getInstance().getAllPlayerClaims().forEach { pc ->
            if (pc.claim.isInClaim(blockLoc.x.toInt(), blockLoc.z.toInt())) {
                if (pc.playerName != player.name) {
                    return false
                }
            }
        }
        return true
    }
}