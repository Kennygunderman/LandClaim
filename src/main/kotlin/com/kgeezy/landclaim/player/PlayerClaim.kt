package com.kgeezy.landclaim.player

import com.kgeezy.landclaim.land.Claim
import org.bukkit.entity.Player

interface ClaimListener {
    fun playerLeft(player: Player)
    fun playerEntered(player: Player)
}

class PlayerClaim(val player: Player? = null, val claim: Claim, private val claimListener: ClaimListener? = null) {

    var playerName: String? =null

    init {
        playerName = player?.name
    }

    private var isInClaim: Boolean = false

    fun playerMove() {
        val x = player?.location?.x?.toInt() ?: 0
        val z = player?.location?.z?.toInt() ?: 0

        val inClaim = claim.isInClaim(x, z)
        if (inClaim && !isInClaim) {
            isInClaim = true
            player?.let {
                claimListener?.playerEntered(it)
            }
        } else if (!inClaim && isInClaim) {
            isInClaim = false
            player?.let {
                claimListener?.playerLeft(it)
            }
        }
    }
}