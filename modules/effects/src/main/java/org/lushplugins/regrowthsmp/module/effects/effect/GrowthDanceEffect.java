package org.lushplugins.regrowthsmp.module.effects.effect;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.lushplugins.gardeningtweaks.api.events.PlayerGrowthDanceEvent;

public class GrowthDanceEffect extends Effect implements Listener {

    public GrowthDanceEffect() {
        super("growth_dance");
    }

    @EventHandler
    public void onPlayerGrowthDance(PlayerGrowthDanceEvent event) {
        // TODO: Add check for player with effect
        event.setCancelled(true);
    }
}
