package org.lushplugins.regrowthsmp.module.unbreakableblocks;

import org.lushplugins.lushlib.module.Module;
import org.lushplugins.lushlib.plugin.SpigotPlugin;
import org.lushplugins.regrowthsmp.module.unbreakableblocks.config.ConfigManager;
import org.lushplugins.regrowthsmp.module.unbreakableblocks.listener.BlockListener;
import org.lushplugins.regrowthsmp.module.unbreakableblocks.listener.SpawnerListener;

public class UnbreakableBlocks extends Module {
    private static UnbreakableBlocks instance;

    private final SpigotPlugin plugin;
    private final ConfigManager configManager;

    public UnbreakableBlocks(SpigotPlugin plugin) {
        super("unbreakable_blocks");
        this.plugin = plugin;

        if (instance == null) {
            instance = this;
        }

        this.configManager = new ConfigManager();

        plugin.registerListeners(
            new BlockListener(),
            new SpawnerListener()
        );
    }

    @Override
    public void onEnable() {
        this.configManager.reloadConfig();
    }

    public SpigotPlugin getPlugin() {
        return plugin;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public static UnbreakableBlocks getInstance() {
        return instance;
    }
}