package org.lushplugins.regrowthsmp.module.welcome.config;

import org.bukkit.configuration.ConfigurationSection;
import org.lushplugins.regrowthsmp.module.welcome.Welcome;

public class ConfigManager {
    private String firstJoinMessage;
    private String rewardMessage;
    private int expReward;

    public ConfigManager() {
        Welcome.getInstance().getPlugin().saveDefaultResource("modules/welcome.yml");
    }

    public void reloadConfig() {
        ConfigurationSection config = Welcome.getInstance().getPlugin().getConfigResource("modules/welcome.yml");
        this.firstJoinMessage = config.getString("first-join-message");
        this.rewardMessage = config.getString("reward-message");
        this.expReward = config.getInt("exp-reward");
    }

    public boolean hasFirstJoinMessage() {
        return firstJoinMessage != null;
    }

    public String getFirstJoinMessage() {
        return firstJoinMessage;
    }

    public boolean hasRewardMessage() {
        return rewardMessage != null;
    }

    public String getRewardMessage() {
        return rewardMessage;
    }

    public int getExpReward() {
        return expReward;
    }
}
