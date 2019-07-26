package com.kgeezy.landclaim

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class LandClaim: JavaPlugin() {

    private val claimer by lazy {
        Claimer(FileManager.getInstance())
    }

    override fun onEnable() {
        super.onEnable()
        FileManager.initialize(dataFolder)
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        if (command.name == CLAIM_CMD && sender is Player) {
            claimer.claim(sender)
        }

        return true
    }
}

/**
 * Hows it gonna work?
 *
 * save the 16x16 piece of land  to config.
 *
 * load configs in memory so we don't have to access YML file every time
 *
 * track to the player move event for displaying message if in landclaim or not
 *
 * track player block event to see if the player is breaking a block in a claimed piece of land
 *
 *
 *
 *
 *
 * */