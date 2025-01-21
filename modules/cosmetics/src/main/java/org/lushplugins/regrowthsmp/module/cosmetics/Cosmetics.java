package org.lushplugins.regrowthsmp.module.cosmetics;

import org.lushplugins.regrowthsmp.common.module.Module;
import org.lushplugins.regrowthsmp.common.plugin.RegrowthPlugin;
import org.lushplugins.regrowthsmp.module.cosmetics.command.GiveCosmeticCommand;
import org.lushplugins.regrowthsmp.module.cosmetics.config.ConfigManager;

public final class Cosmetics extends Module {
    private static Cosmetics instance;

    private final ConfigManager configManager;

    public Cosmetics(RegrowthPlugin plugin) {
        super("cosmetics", plugin);

        if (instance == null) {
            instance = this;
        }

        this.configManager = new ConfigManager();

        plugin.registerCommand(new GiveCosmeticCommand());
    }

    @Override
    public void onEnable() {
        configManager.reloadConfig();
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public static Cosmetics getInstance() {
        return instance;
    }
}
