package com.kgeezy.landclaim.player

import com.kgeezy.landclaim.land.Claim
import org.bukkit.entity.Player

interface ClaimListener {
    fun playerLeft(player: Player)
    fun playerEntered(player: Player)
}

class PlayerClaim(val player: Player, val claim: Claim, private val claimListener: ClaimListener? = null) {
    var isInClaim: Boolean = false

    fun playerMove() {
        val x = player.location.x.toInt()
        val z = player.location.z.toInt()

        val inClaim = claim.isInClaim(x, z)
        if (inClaim && !isInClaim) {
            isInClaim = true
            claimListener?.playerEntered(player)
        } else if (!inClaim && isInClaim) {
            isInClaim = false
            claimListener?.playerLeft(player)
        }
    }
}