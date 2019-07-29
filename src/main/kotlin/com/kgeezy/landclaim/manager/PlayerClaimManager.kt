package com.kgeezy.landclaim.manager

import com.kgeezy.landclaim.claim.Claim
import com.kgeezy.landclaim.player.ClaimListener
import com.kgeezy.landclaim.claim.PlayerClaim
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

    /**
     * A list of all claims on the server
     */
    private val allPlayerClaims = mutableListOf<PlayerClaim>()

    /**
     * All claims that are active with a player online
     */
    private val activePlayerClaims = mutableListOf<PlayerClaim>()
    private val activePlayerClaimMap = mutableMapOf<Player, MutableList<Claim>>()

    fun getAllPlayerClaims(): List<PlayerClaim> = allPlayerClaims
    fun getActiveMap(): Map<Player, List<Claim>> = activePlayerClaimMap
    fun getActivePlayerClaims(): List<PlayerClaim> = activePlayerClaims

    fun addActive(player: Player, claims: List<Claim>, claimListener: ClaimListener) {
        if (activePlayerClaimMap[player] == null) {
            activePlayerClaimMap[player] = claims.toMutableList()
        } else {
            activePlayerClaimMap[player]?.addAll(claims)
        }

        claims.forEach { claim ->
            activePlayerClaims.add(PlayerClaim(player, claim, claimListener))
        }
    }

    fun removeActive(player: Player) {
        if (hasClaim(player)) {
            activePlayerClaimMap.remove(player)
            activePlayerClaims.filter { pc -> pc.player == player }.forEach { pc ->
                activePlayerClaims.remove(pc)
            }
        }
    }

    /**
     * Adds to all the claims on the server
     */
    fun addToAll(playerName: String, claims: List<Claim>) {
        claims.forEach { claim ->
            val pc = PlayerClaim(claim = claim)
            pc.playerName = playerName
            allPlayerClaims.add(pc)
        }
    }

    fun hasClaim(player: Player): Boolean = activePlayerClaimMap[player] != null

    fun pcFromClaim(claim: Claim): PlayerClaim? {
        return activePlayerClaims.firstOrNull { pc -> pc.claim == claim }
    }
}