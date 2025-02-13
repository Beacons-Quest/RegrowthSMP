package org.lushplugins.regrowthsmp.module.pinata.config;

import org.bukkit.configuration.ConfigurationSection;
import org.lushplugins.regrowthsmp.module.pinata.Pinata;

public class ConfigManager {
    private int healthPerPlayer;
    private int maxHealth;

    public ConfigManager() {
        Pinata.getInstance().getPlugin().saveDefaultResource("modules/pinata.yml");
    }

    public void reloadConfig() {
        ConfigurationSection config = Pinata.getInstance().getPlugin().getConfigResource("modules/pinata.yml");

        this.healthPerPlayer = config.getInt("health-per-player", 5);
        this.maxHealth = config.getInt("max-health", 250);
    }

    public int getHealthPerPlayer() {
        return healthPerPlayer;
    }

    public int getMaxHealth() {
        return maxHealth;
    }
}
