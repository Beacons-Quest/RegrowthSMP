package org.lushplugins.regrowthsmp.module.unbreakableblocks;

import org.lushplugins.regrowthsmp.common.module.Module;
import org.lushplugins.regrowthsmp.common.plugin.RegrowthPlugin;
import org.lushplugins.regrowthsmp.module.unbreakableblocks.config.ConfigManager;
import org.lushplugins.regrowthsmp.module.unbreakableblocks.listener.BlockListener;
import org.lushplugins.regrowthsmp.module.unbreakableblocks.listener.SpawnerListener;

public class UnbreakableBlocks extends Module {
    private static UnbreakableBlocks instance;

    private final ConfigManager configManager;

    public UnbreakableBlocks(RegrowthPlugin plugin) {
        super("unbreakable_blocks", plugin);

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

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public static UnbreakableBlocks getInstance() {
        return instance;
    }
}