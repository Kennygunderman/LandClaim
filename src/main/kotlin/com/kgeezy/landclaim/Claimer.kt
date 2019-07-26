package com.kgeezy.landclaim

import org.bukkit.entity.Player
import java.util.*

class Claimer(fileManager: FileManager) {
    private val yml = fileManager.yml()
    private val config = fileManager.configFromYml(yml)

    fun claim(player: Player, radius: Long = 8) {
        val claimId = UUID.randomUUID().toString()
        config.set("${player.name}.$claimId.x", player.location.x.toInt())
        config.set("${player.name}.$claimId.z", player.location.z.toInt())
        config.set("${player.name}.$claimId.radius", radius)
        config.save(yml)
    }
}