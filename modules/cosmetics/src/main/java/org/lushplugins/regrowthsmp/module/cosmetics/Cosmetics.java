package org.lushplugins.regrowthsmp.module.cosmetics;

import org.lushplugins.lushlib.module.Module;
import org.lushplugins.lushlib.plugin.SpigotPlugin;
import org.lushplugins.regrowthsmp.module.cosmetics.command.GiveCosmeticCommand;
import org.lushplugins.regrowthsmp.module.cosmetics.config.ConfigManager;

public final class Cosmetics extends Module {
    private static Cosmetics instance;

    private final SpigotPlugin plugin;
    private final ConfigManager configManager;

    public Cosmetics(SpigotPlugin plugin) {
        super("cosmetics");
        this.plugin = plugin;

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

    public SpigotPlugin getPlugin() {
        return plugin;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public static Cosmetics getInstance() {
        return instance;
    }
}
