package org.lushplugins.regrowthsmp.module.unbreakableblocks.config;

import org.bukkit.Material;
import org.bukkit.Registry;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.lushplugins.lushlib.registry.RegistryUtils;
import org.lushplugins.regrowthsmp.module.unbreakableblocks.UnbreakableBlocks;

import java.util.List;

public class ConfigManager {
    private List<Material> unbreakableBlocks;
    private List<EntityType> torchDisabledSpawners;
    private String denyMessage;
    private String bypassMessage;

    public ConfigManager() {
        UnbreakableBlocks.getInstance().getPlugin().saveDefaultResource("modules/unbreakable_blocks.yml");
    }

    public void reloadConfig() {
        ConfigurationSection config = UnbreakableBlocks.getInstance().getPlugin().getConfigResource("modules/unbreakable_blocks.yml");

        this.unbreakableBlocks = RegistryUtils.fromStringList(config.getStringList("unbreakable-blocks"), Registry.MATERIAL);
        this.torchDisabledSpawners = RegistryUtils.fromStringList(config.getStringList("torch-disabled-spawners"), Registry.ENTITY_TYPE);
        this.denyMessage = config.getString("messages.deny-break");
        this.bypassMessage = config.getString("messages.bypass");
    }

    public List<Material> getUnbreakableBlocks() {
        return unbreakableBlocks;
    }

    public List<EntityType> getTorchDisabledSpawners() {
        return torchDisabledSpawners;
    }

    public String getDenyMessage() {
        return denyMessage;
    }

    public String getBypassMessage() {
        return bypassMessage;
    }
}
