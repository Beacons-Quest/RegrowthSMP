package org.lushplugins.regrowthsmp.module.cosmetics.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.lushplugins.regrowthsmp.module.cosmetics.Cosmetics;

import java.io.File;
import java.util.HashMap;

public class ConfigManager {
    private HashMap<String, String> displayNameFormats;

    public ConfigManager() {
        Cosmetics.getInstance().getPlugin().saveDefaultResource("modules/cosmetics.yml");
    }

    public void reloadConfig() {
        ConfigurationSection config = YamlConfiguration.loadConfiguration(new File(Cosmetics.getInstance().getPlugin().getDataFolder(), "modules/cosmetics.yml"));

        this.displayNameFormats = new HashMap<>();
        ConfigurationSection themesSection = config.getConfigurationSection("display-name-themes");
        if (themesSection != null) {
            themesSection.getValues(false).forEach((key, value) -> displayNameFormats.put(key, (String) value));
        }
    }

    public String getDisplayNameFormat(String name) {
        return displayNameFormats.get(name);
    }
}
