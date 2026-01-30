package xyz.shaggysa.stillSky

import org.bukkit.Bukkit
import org.bukkit.GameRule
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.java.JavaPlugin



class PlayerListener(private val plugin: StillSky) : Listener {
    @EventHandler
    @Suppress("UNUSED_PARAMETER")
    fun onPlayerQuit(event: PlayerQuitEvent) {
        // player count updates after the event listener triggers in this case
        if (plugin.serverEmpty() || Bukkit.getOnlinePlayers().size == 1) {
            plugin.freezeDaylight()
        }
    }

    @EventHandler
    @Suppress("UNUSED_PARAMETER")
    fun onPlayerJoin(event: PlayerJoinEvent) {
        if (Bukkit.getOnlinePlayers().size == 1) {
            plugin.unfreezeDaylight()
        }
    }
}

class StillSky : JavaPlugin() {

    fun serverEmpty(): Boolean {
        return (Bukkit.getOnlinePlayers().isEmpty())
    }

    fun freezeDaylight() {
        Bukkit.getWorlds()[0].setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false)
        logger.info("The daylight cycle has paused.")
    }

    fun unfreezeDaylight() {
        Bukkit.getWorlds()[0].setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true)
        logger.info("The daylight cycle has resumed.")
    }

    override fun onEnable() {
        if (serverEmpty()) {
            freezeDaylight()
        }
        server.pluginManager.registerEvents(PlayerListener(this), this)
    }

    override fun onDisable() {
        unfreezeDaylight()
    }
}



