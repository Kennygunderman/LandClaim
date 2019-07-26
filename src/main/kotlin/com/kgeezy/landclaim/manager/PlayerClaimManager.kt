package com.kgeezy.landclaim.manager

import com.kgeezy.landclaim.land.Claim
import com.kgeezy.landclaim.player.ClaimListener
import com.kgeezy.landclaim.player.PlayerClaim
import org.bukkit.entity.Player

class PlayerClaimManager private constructor() {
    companion object {
        private var inst: PlayerClaimManager? = null
        fun getInstance(): PlayerClaimManager {
            if (inst == null) {
                inst = PlayerClaimManager()
            }
            return inst!!
        }
    }

    private val playerClaims = mutableListOf<PlayerClaim>()
    private val playerClaimMap = mutableMapOf<Player, MutableList<Claim>>()

    fun getMap(): Map<Player, List<Claim>> = playerClaimMap
    fun getPlayerClaims(): List<PlayerClaim> = playerClaims

    fun add(player: Player, claims: List<Claim>, claimListener: ClaimListener) {
        if (playerClaimMap[player] == null) {
            playerClaimMap[player] = claims.toMutableList()
        } else {
            playerClaimMap[player]?.addAll(claims)
        }

        claims.forEach { claim ->
            playerClaims.add(PlayerClaim(player, claim, claimListener))
        }
    }

    fun remove(player: Player) {
        if (hasClaim(player)) {
            playerClaimMap.remove(player)
            playerClaims.filter { pc -> pc.player == player }.forEach { pc ->
                playerClaims.remove(pc)
            }
        }
    }

    fun hasClaim(player: Player): Boolean = playerClaimMap[player] != null

    fun pcFromClaim(claim: Claim): PlayerClaim? {
        return playerClaims.firstOrNull { pc -> pc.claim == claim }
    }
}