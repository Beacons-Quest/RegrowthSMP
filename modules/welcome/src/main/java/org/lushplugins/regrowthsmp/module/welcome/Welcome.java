package org.lushplugins.regrowthsmp.module.welcome;

import org.lushplugins.regrowthsmp.common.module.Module;
import org.lushplugins.regrowthsmp.common.plugin.RegrowthPlugin;
import org.lushplugins.regrowthsmp.module.welcome.config.ConfigManager;
import org.lushplugins.regrowthsmp.module.welcome.listener.PlayerListener;

public class Welcome extends Module {
    private static Welcome instance;

    private final ConfigManager configManager;

    public Welcome(RegrowthPlugin plugin) {
        super("welcome", plugin);

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

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public static Welcome getInstance() {
        return instance;
    }
}
