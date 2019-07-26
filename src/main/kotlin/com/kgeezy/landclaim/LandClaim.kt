package com.kgeezy.landclaim

import com.kgeezy.landclaim.event.block.BlockBreakListener
import com.kgeezy.landclaim.event.block.BlockPlaceListener
import com.kgeezy.landclaim.event.player.PlayerLoginListener
import com.kgeezy.landclaim.event.player.PlayerMoveListener
import com.kgeezy.landclaim.event.player.PlayerQuitListener
import com.kgeezy.landclaim.land.ClaimFile
import com.kgeezy.landclaim.manager.PlayerClaimManager
import com.kgeezy.landclaim.player.ClaimListener
import com.kgeezy.landclaim.util.ClaimMonitor
import com.kgeezy.landclaim.util.FileManager
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class LandClaim : JavaPlugin(), ClaimListener {

    private val claimFile by lazy {
        ClaimFile(FileManager.getInstance())
    }

    override fun onEnable() {
        super.onEnable()
        FileManager.initialize(dataFolder)

        addClaimsForOnlinePlayers()
        initAllClaims()
        registerEvents()
        ClaimMonitor.monitorClaims(this)
    }

    private fun registerEvents() {
        server.pluginManager.registerEvents(PlayerMoveListener(), this)
        server.pluginManager.registerEvents(PlayerQuitListener(), this)
        server.pluginManager.registerEvents(PlayerLoginListener(claimFile, this), this)
        server.pluginManager.registerEvents(BlockBreakListener(), this)
        server.pluginManager.registerEvents(BlockPlaceListener(), this)
    }

    private fun addClaimsForOnlinePlayers() {
        server.onlinePlayers.forEach { player ->
            PlayerClaimManager.getInstance().addActive(player, claimFile.getClaims(player), this)
        }
    }

    private fun initAllClaims() {
        claimFile.getClaims().forEach { playerName, claims ->
            PlayerClaimManager.getInstance().addToAll(playerName, claims)
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
            claimFile.declareClaim(sender) { claim, msg ->
                claim?.let { c ->
                    PlayerClaimManager.getInstance().addActive(sender, listOf(c), this)
                    PlayerClaimManager.getInstance().addToAll(sender.name, listOf(c))
                }
                sender.sendMessage(msg)
            }
        }

        return true
    }
}
