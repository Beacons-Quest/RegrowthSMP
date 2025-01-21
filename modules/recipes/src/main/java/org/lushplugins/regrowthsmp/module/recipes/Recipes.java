package org.lushplugins.regrowthsmp.module.recipes;

import org.lushplugins.regrowthsmp.common.module.Module;
import org.lushplugins.regrowthsmp.common.plugin.RegrowthPlugin;
import org.lushplugins.regrowthsmp.module.recipes.command.RecipesCommand;
import org.lushplugins.regrowthsmp.module.recipes.config.ConfigManager;
import org.lushplugins.regrowthsmp.module.recipes.listener.CraftListener;
import org.lushplugins.regrowthsmp.module.recipes.listener.PlayerListener;

public final class Recipes extends Module {
    private static Recipes instance;

    private final ConfigManager configManager;

    public Recipes(RegrowthPlugin plugin) {
        super("recipes", plugin);

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

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public static Recipes getInstance() {
        return instance;
    }
}
