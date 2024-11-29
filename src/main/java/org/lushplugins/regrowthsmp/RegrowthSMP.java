package org.lushplugins.regrowthsmp;

import org.lushplugins.lushlib.LushLib;
import org.lushplugins.lushlib.plugin.SpigotPlugin;
import org.lushplugins.regrowthsmp.command.RegrowthSMPCommand;
import org.lushplugins.regrowthsmp.config.ConfigManager;
import org.lushplugins.regrowthsmp.module.ModuleManager;

public final class RegrowthSMP extends SpigotPlugin {
    private static RegrowthSMP plugin;

    private ModuleManager moduleManager;
    private ConfigManager configManager;

    @Override
    public void onLoad() {
        plugin = this;
        LushLib.getInstance().enable(this);
    }

    @Override
    public void onEnable() {
        moduleManager = new ModuleManager();

        configManager = new ConfigManager();
        configManager.reload();

        registerCommand(new RegrowthSMPCommand());
    }

    @Override
    public void onDisable() {
        if (configManager != null) {
            configManager.disable();
            configManager = null;
        }

        if (moduleManager != null) {
            moduleManager.disable();
            moduleManager = null;
        }
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public static RegrowthSMP getInstance() {
        return plugin;
    }
}
