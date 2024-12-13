package org.lushplugins.regrowthsmp.module.recipes;

import org.lushplugins.lushlib.module.Module;
import org.lushplugins.lushlib.plugin.SpigotPlugin;
import org.lushplugins.regrowthsmp.module.recipes.command.RecipesCommand;
import org.lushplugins.regrowthsmp.module.recipes.config.ConfigManager;
import org.lushplugins.regrowthsmp.module.recipes.listener.CraftListener;
import org.lushplugins.regrowthsmp.module.recipes.listener.PlayerListener;

public final class Recipes extends Module {
    private static Recipes instance;

    private final SpigotPlugin plugin;
    private final ConfigManager configManager;

    public Recipes(SpigotPlugin plugin) {
        super("recipes");
        this.plugin = plugin;

        if (instance == null) {
            instance = this;
        }

        this.configManager = new ConfigManager();

        plugin.registerListeners(
            new CraftListener(),
            new PlayerListener()
        );

        plugin.registerCommand(new RecipesCommand());
    }

    @Override
    public void onEnable() {
        configManager.reloadConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public SpigotPlugin getPlugin() {
        return plugin;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public static Recipes getInstance() {
        return instance;
    }
}
