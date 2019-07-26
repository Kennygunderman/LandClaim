package com.kgeezy.landclaim

import com.kgeezy.landclaim.event.PlayerLoginListener
import com.kgeezy.landclaim.event.PlayerMoveListener
import com.kgeezy.landclaim.event.PlayerQuitListener
import com.kgeezy.landclaim.land.ClaimFile
import com.kgeezy.landclaim.manager.PlayerClaimManager
import com.kgeezy.landclaim.player.ClaimListener
import com.kgeezy.landclaim.util.ClaimMonitor
import com.kgeezy.landclaim.util.FileManager
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitScheduler

class LandClaim: JavaPlugin(), ClaimListener {

    private val claimFile by lazy {
        ClaimFile(FileManager.getInstance())
    }

    override fun onEnable() {
        super.onEnable()
        FileManager.initialize(dataFolder)

        addOnlinePlayers()
        registerEvents()
        ClaimMonitor.monitorClaims(this)
    }

    private fun registerEvents() {
        server.pluginManager.registerEvents(PlayerMoveListener(), this)
        server.pluginManager.registerEvents(PlayerQuitListener(), this)
        server.pluginManager.registerEvents(PlayerLoginListener(claimFile, this), this)
    }

    private fun addOnlinePlayers() {
        server.onlinePlayers.forEach { player ->
            PlayerClaimManager.getInstance().add(player, claimFile.getClaims(player), this)
        }
    }

    override fun playerLeft(player: Player) {
        player.sendMessage(CLAIM_LEFT)
    }

    override fun playerEntered(player: Player) {
        player.sendMessage(String.format(CLAIM_ENTERED, player.name))
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (command.name == CLAIM_CMD && sender is Player) {
            claimFile.declareClaim(sender) { claim ->
                PlayerClaimManager.getInstance().add(sender, listOf(claim), this)
                sender.sendMessage(CLAIM_CLAIMED)
            }
        }

        return true
    }
}
