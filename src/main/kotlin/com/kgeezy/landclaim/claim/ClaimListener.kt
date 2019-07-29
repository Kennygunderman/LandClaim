package com.kgeezy.landclaim.claim

import org.bukkit.entity.Player

interface ClaimListener {
    fun playerLeft(player: Player)
    fun playerEntered(player: Player)
}