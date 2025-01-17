package org.lushplugins.regrowthsmp.module.effects.effect;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.lushplugins.gardeningtweaks.api.events.BlockLumberEvent;
import org.lushplugins.regrowthsmp.module.effects.Effects;
import org.lushplugins.regrowthsmp.module.effects.data.EffectsUser;

public class TreeChomperEffect extends Effect implements Listener {

    public TreeChomperEffect() {
        super("tree_chomper");
    }

    @EventHandler
    public void onBlockLumber(BlockLumberEvent event) {
        Player player = event.getPlayer();
        if (player == null) {
            return;
        }

        EffectsUser user = Effects.getInstance().getUserManager().getUser(player.getUniqueId());
        if (user == null || !user.getCurrentEffect().equals(this.getId())) {
            event.setCancelled(true);
        }
    }
}
