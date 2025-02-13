package org.lushplugins.regrowthsmp.module.pinata;

import org.lushplugins.regrowthsmp.common.module.Module;
import org.lushplugins.regrowthsmp.common.plugin.RegrowthPlugin;
import org.lushplugins.regrowthsmp.module.pinata.config.ConfigManager;
import org.lushplugins.regrowthsmp.module.pinata.listener.BossListener;

public final class Pinata extends Module {
    private static Pinata instance;

    private final ConfigManager configManager;

    public Pinata(RegrowthPlugin plugin) {
        super("pinata", plugin);

        if (instance == null) {
            instance = this;
        }

        this.configManager = new ConfigManager();

        plugin.registerListener(new BossListener());
    }

    @Override
    public void onEnable() {
        configManager.reloadConfig();
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public static Pinata getInstance() {
        return instance;
    }
}
