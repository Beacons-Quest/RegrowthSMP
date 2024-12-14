package org.lushplugins.regrowthsmp.module.welcome;

import org.lushplugins.lushlib.module.Module;
import org.lushplugins.lushlib.plugin.SpigotPlugin;
import org.lushplugins.regrowthsmp.module.welcome.config.ConfigManager;
import org.lushplugins.regrowthsmp.module.welcome.listener.PlayerListener;

public class Welcome extends Module {
    private static Welcome instance;

    private final SpigotPlugin plugin;
    private final ConfigManager configManager;

    public Welcome(SpigotPlugin plugin) {
        super("welcome");
        this.plugin = plugin;

        if (instance == null) {
            instance = this;
        }

        this.configManager = new ConfigManager();

        plugin.registerListener(new PlayerListener());
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

    public static Welcome getInstance() {
        return instance;
    }
}
