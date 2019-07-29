package com.kgeezy.landclaim.claim

import com.kgeezy.landclaim.CANT_CLAIM
import com.kgeezy.landclaim.CLAIM_CLAIMED
import com.kgeezy.landclaim.manager.PlayerClaimManager
import com.kgeezy.landclaim.manager.FileManager
import org.bukkit.entity.Player
import java.util.*

class ClaimFile(fileManager: FileManager) {
    private val yml = fileManager.yml()
    private val config = fileManager.configFromYml(yml)

    fun declareClaim(player: Player, radius: Long = 8, claimed: (claim: Claim?, msg: String) -> Unit) {
        val x = player.location.x.toInt()
        val z = player.location.z.toInt()
        val createdClaim = Claim(Center(x, z), radius.toInt())

        var isInClaim = false
        PlayerClaimManager.getInstance().getAllPlayerClaims().forEach { pc ->
            if (pc.claim.isInClaim(createdClaim)) {
                isInClaim = true
                claimed.invoke(null, CANT_CLAIM)
                return@forEach
            }
        }

        if (!isInClaim) {
            val claimId = UUID.randomUUID().toString()
            config.set("${player.name}.$claimId.x", x)
            config.set("${player.name}.$claimId.z", z)
            config.set("${player.name}.$claimId.radius", radius)
            config.save(yml)
            claimed.invoke(createdClaim, CLAIM_CLAIMED)
        }
    }

    fun getClaims(player: Player): List<Claim> {
        return mutableListOf<Claim>().apply {
            config.getConfigurationSection(player.name)?.getKeys(false)?.forEach { claimId ->
                val x = config.get("${player.name}.$claimId.x") as? Int
                val z = config.get("${player.name}.$claimId.z") as? Int
                val radius = config.get("${player.name}.$claimId.radius") as? Int
                if (x != null && z != null && radius != null) {
                    add(Claim(Center(x, z), radius))
                }
            }
        }
    }

    //TODO: move out shared config logic
    fun getClaims(): Map<String, List<Claim>> {
        val map = mutableMapOf<String, List<Claim>>()
        config.getKeys(false).forEach { player ->
            val claims = mutableListOf<Claim>()
            config.getConfigurationSection(player)?.getKeys(true)?.forEach { claimId ->
                val x = config.get("$player.$claimId.x") as? Int
                val z = config.get("$player.$claimId.z") as? Int
                val radius = config.get("$player.$claimId.radius") as? Int
                if (x != null && z != null && radius != null) {
                    claims.add(Claim(Center(x, z), radius))
                }
                map[player] = claims
            }
        }
        return map
    }
}