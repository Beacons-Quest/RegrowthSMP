package org.lushplugins.regrowthsmp.module.pinata.listener;

import me.xemor.enchantedbosses.events.SkillEntitySpawnEvent;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.lushplugins.regrowthsmp.module.pinata.Pinata;

public class BossListener implements Listener {

    @EventHandler
    public void onBossSpawn(SkillEntitySpawnEvent event) {
        LivingEntity entity = event.getEntity();

        int healthPerPlayer = Pinata.getInstance().getConfigManager().getHealthPerPlayer();
        int maxHealth = Pinata.getInstance().getConfigManager().getMaxHealth();
        int onlinePlayerCount = Bukkit.getOnlinePlayers().size();
        int health = Math.min(healthPerPlayer * onlinePlayerCount, maxHealth);

        AttributeInstance attribute = entity.getAttribute(Attribute.MAX_HEALTH);
        if (attribute != null) {
            attribute.setBaseValue(health);
            entity.setHealth(health);
        }
    }
}
