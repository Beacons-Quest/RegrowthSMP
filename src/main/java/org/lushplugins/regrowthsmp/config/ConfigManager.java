package org.lushplugins.regrowthsmp.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.lushplugins.lushlib.manager.Manager;
import org.lushplugins.regrowthsmp.RegrowthSMP;
import org.lushplugins.regrowthsmp.module.ModuleManager;
import org.lushplugins.regrowthsmp.module.ModuleType;

import java.util.logging.Level;

public class ConfigManager extends Manager {

    public ConfigManager() {
        RegrowthSMP.getInstance().saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        RegrowthSMP plugin = RegrowthSMP.getInstance();
        plugin.reloadConfig();
        FileConfiguration config = plugin.getConfig();

        ModuleManager moduleManager = plugin.getModuleManager();
        ConfigurationSection modulesSection = config.getConfigurationSection("modules");
        if (modulesSection != null) {
            modulesSection.getValues(false).forEach((moduleName, value) -> {
                ModuleType moduleType;
                try {
                    moduleType = ModuleType.valueOf(moduleName.toUpperCase());
                } catch (IllegalArgumentException e) {
                    plugin.getLogger().log(Level.WARNING, moduleName + " is not a valid module type");
                    return;
                }

                if (value instanceof Boolean enable && enable) {
                    moduleManager.enableModule(moduleType);
                } else {
                    moduleManager.disableModule(moduleType);
                }
            });
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public void reload() {
        if (isEnabled()) {
            disable();
        }

        enable();
    }
}
