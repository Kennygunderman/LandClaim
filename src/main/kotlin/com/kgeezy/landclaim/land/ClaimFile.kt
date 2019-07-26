package com.kgeezy.landclaim.land

import com.kgeezy.landclaim.util.FileManager
import org.bukkit.entity.Player
import java.util.*

class ClaimFile(fileManager: FileManager) {
    private val yml = fileManager.yml()
    private val config = fileManager.configFromYml(yml)

    fun declareClaim(player: Player, radius: Long = 8, claimed: (claim: Claim) -> Unit) {
        val claimId = UUID.randomUUID().toString()
        val x = player.location.x.toInt()
        val z = player.location.z.toInt()
        config.set("${player.name}.$claimId.x", x)
        config.set("${player.name}.$claimId.z", z)
        config.set("${player.name}.$claimId.radius", radius)
        config.save(yml)
        claimed.invoke(Claim(Center(x, z), radius.toInt()))
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