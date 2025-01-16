package org.lushplugins.regrowthsmp.module.effects.effect;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.lushplugins.gardeningtweaks.api.events.BlockLumberEvent;

public class TreeChomperEffect extends Effect implements Listener {

    public TreeChomperEffect() {
        super("tree_chomper");
    }

    @EventHandler
    public void onBlockLumber(BlockLumberEvent event) {
        // TODO: Add check for player with effect
        event.setCancelled(true);
    }
}
