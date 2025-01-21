package org.lushplugins.regrowthsmp.module.abilities.ability;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.lushplugins.regrowthsmp.module.abilities.data.AbilitiesUser;
import org.lushplugins.regrowthsmp.module.abilities.Abilities;

import java.util.List;

public class FriendlyMobsAbility extends Ability implements Listener {
    private static final List<EntityType> HOSTILE_MOBS = List.of(
        EntityType.BOGGED,
        EntityType.CAVE_SPIDER,
        EntityType.DROWNED,
        EntityType.HUSK,
        EntityType.SKELETON,
        EntityType.SPIDER,
        EntityType.STRAY,
        EntityType.ZOMBIE,
        EntityType.ZOMBIE_VILLAGER
    );

    public FriendlyMobsAbility() {
        super(AbilityTypes.FRIENDLY_MOBS);
    }

    @EventHandler
    public void onEntityTargetPlayer(EntityTargetEvent event) {
        if (!(event.getTarget() instanceof Player player) || !(event.getEntity() instanceof Mob mob)) {
            return;
        }

        if (!HOSTILE_MOBS.contains(event.getEntityType())) {
            return;
        }

        AbilitiesUser user = Abilities.getInstance().getCachedUserData(player.getUniqueId());
        if (user == null || !user.getCurrentAbility().equals(this.getId())) {
            return;
        }

        if (event.getReason() == EntityTargetEvent.TargetReason.TARGET_ATTACKED_ENTITY || mob.getTarget() == player) {
            return;
        }

        event.setCancelled(true);
    }
}
