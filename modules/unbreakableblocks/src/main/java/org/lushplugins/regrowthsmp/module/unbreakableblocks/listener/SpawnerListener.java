package org.lushplugins.regrowthsmp.module.unbreakableblocks.listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.lushplugins.regrowthsmp.module.unbreakableblocks.UnbreakableBlocks;

public class SpawnerListener implements Listener {

    @EventHandler
    public void onSpawnerSpawn(SpawnerSpawnEvent event) {
        CreatureSpawner spawner = event.getSpawner();
        if (spawner == null) {
            return;
        }

        Block blockAbove = spawner.getLocation().clone().add(0, 1, 0).getBlock();
        if (blockAbove.getType() != Material.TORCH) {
            return;
        }

        if (UnbreakableBlocks.getInstance().getConfigManager().getTorchDisabledSpawners().contains(event.getEntityType())) {
            event.setCancelled(true);
        }
    }
}
