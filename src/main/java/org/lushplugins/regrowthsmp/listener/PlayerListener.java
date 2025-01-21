package org.lushplugins.regrowthsmp.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.lushplugins.regrowthsmp.RegrowthSMP;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        RegrowthSMP.getInstance().getUserManager().getUser(player.getUniqueId(), true);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        RegrowthSMP.getInstance().getUserManager().invalidateUser(player.getUniqueId());
    }
}
