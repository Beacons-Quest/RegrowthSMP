package org.lushplugins.regrowthsmp.module.abilities.ability;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.lushplugins.gardeningtweaks.api.events.CropGrowEvent;
import org.lushplugins.gardeningtweaks.api.events.PlayerGrowthDanceEvent;
import org.lushplugins.regrowthsmp.module.abilities.data.AbilitiesUser;
import org.lushplugins.regrowthsmp.module.abilities.Abilities;

public class GrowthDanceAbility extends Ability implements Listener {

    public GrowthDanceAbility() {
        super(AbilityTypes.GROWTH_DANCE);
    }

    @EventHandler
    public void onPlayerGrowthDance(PlayerGrowthDanceEvent event) {
        Player player = event.getPlayer();
        AbilitiesUser user = Abilities.getInstance().getCachedUserData(player.getUniqueId());
        if (user == null || user.getCurrentAbility() == null || !user.getCurrentAbility().equals(this.getId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onGrowthDanceCropGrown(CropGrowEvent event) {
        Block block = event.getBlock();
        Location particleLocation = block.getLocation().clone().add(0.5, 0.5, 0.5);
        block.getWorld().spawnParticle(Particle.SMALL_GUST, particleLocation, 3, 0.5, 0.5, 0.5, 0);
    }
}
