package org.lushplugins.regrowthsmp.module.effects.effect;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.lushplugins.gardeningtweaks.api.events.PlayerGrowthDanceEvent;
import org.lushplugins.regrowthsmp.module.effects.Effects;
import org.lushplugins.regrowthsmp.module.effects.data.EffectsUser;

public class GrowthDanceEffect extends Effect implements Listener {

    public GrowthDanceEffect() {
        super("growth_dance");
    }

    @EventHandler
    public void onPlayerGrowthDance(PlayerGrowthDanceEvent event) {
        Player player = event.getPlayer();
        EffectsUser user = Effects.getInstance().getUserManager().getUser(player.getUniqueId());
        if (user == null || !user.getCurrentEffect().equals(this.getId())) {
            event.setCancelled(true);
        }
    }
}
