package org.lushplugins.regrowthsmp.module.unbreakableblocks.listener;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.lushplugins.lushlib.libraries.chatcolor.ChatColorHandler;
import org.lushplugins.regrowthsmp.module.unbreakableblocks.UnbreakableBlocks;

import java.util.List;

public class BlockListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (!UnbreakableBlocks.getInstance().getConfigManager().getUnbreakableBlocks().contains(block.getType())) {
            return;
        }

        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
            ChatColorHandler.sendMessage(player, UnbreakableBlocks.getInstance().getConfigManager().getDenyMessage());
        } else {
            ChatColorHandler.sendMessage(player, UnbreakableBlocks.getInstance().getConfigManager().getBypassMessage());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityExplode(EntityExplodeEvent event) {
        List<Block> unbreakableBlocks = event.blockList().stream()
            .filter(brokenBlock -> UnbreakableBlocks.getInstance().getConfigManager().getUnbreakableBlocks().contains(brokenBlock.getType()))
            .toList();

        event.blockList().removeAll(unbreakableBlocks);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onStructureGrowEvent(StructureGrowEvent event) {
        List<BlockState> unbreakableBlocks = event.getBlocks().stream()
            .filter(blockState -> {
                Block brokenBlock = blockState.getLocation().getBlock();
                return UnbreakableBlocks.getInstance().getConfigManager().getUnbreakableBlocks().contains(brokenBlock.getType());
            })
            .toList();

        event.getBlocks().removeAll(unbreakableBlocks);
    }
}
