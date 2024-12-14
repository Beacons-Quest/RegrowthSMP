package org.lushplugins.regrowthsmp.module.welcome.config;

import org.bukkit.configuration.ConfigurationSection;
import org.lushplugins.regrowthsmp.module.welcome.Welcome;

public class ConfigManager {
    private int expReward;

    public ConfigManager() {
        Welcome.getInstance().getPlugin().saveDefaultResource("modules/welcome.yml");
    }

    public void reloadConfig() {
        ConfigurationSection config = Welcome.getInstance().getPlugin().getConfigResource("modules/welcome.yml");
        this.expReward = config.getInt("exp-reward");
    }

    public int getExpReward() {
        return expReward;
    }
}
