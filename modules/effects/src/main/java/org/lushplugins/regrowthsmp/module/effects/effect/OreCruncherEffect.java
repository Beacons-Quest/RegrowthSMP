package org.lushplugins.regrowthsmp.module.effects.effect;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.lushplugins.regrowthsmp.module.effects.Effects;
import org.lushplugins.regrowthsmp.module.effects.data.EffectsUser;

import java.util.Collections;
import java.util.List;

public class OreCruncherEffect extends Effect implements Listener {
    private static final List<Material> ORES = List.of(
        Material.COAL_ORE,
        Material.DEEPSLATE_COAL_ORE,
        Material.COPPER_ORE,
        Material.DEEPSLATE_COPPER_ORE,
        Material.IRON_ORE,
        Material.DEEPSLATE_IRON_ORE,
        Material.GOLD_ORE,
        Material.DEEPSLATE_GOLD_ORE,
        Material.REDSTONE_ORE,
        Material.DEEPSLATE_REDSTONE_ORE,
        Material.EMERALD_ORE,
        Material.DEEPSLATE_EMERALD_ORE,
        Material.LAPIS_ORE,
        Material.DEEPSLATE_LAPIS_ORE,
        Material.DIAMOND_ORE,
        Material.DEEPSLATE_DIAMOND_ORE,
        Material.NETHER_GOLD_ORE,
        Material.NETHER_QUARTZ_ORE
    );
    private static final int BREAK_LIMIT = 8; // TODO: Add higher counts for higher levels

    public OreCruncherEffect() {
        super("ore_cruncher");
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.isCancelled())  {
            return;
        }

        Player player = event.getPlayer();
        if (!player.isSneaking()) {
            return;
        }

        EffectsUser user = Effects.getInstance().getUserManager().getUser(player.getUniqueId());
        if (user == null || !user.getCurrentEffect().equals(this.getId())) {
            return;
        }

        Block block = event.getBlock();
        Material blockType = block.getType();
        if (!ORES.contains(blockType)) {
            return;
        }

        ItemStack mainHand = player.getInventory().getItemInMainHand();
        if (!Tag.ITEMS_PICKAXES.isTagged(mainHand.getType())) {
            return;
        }

        new VeinMineTask(block, BREAK_LIMIT, player);
    }

    public static class VeinMineTask {
        private static final int[] VERTICAL_BREAK_ORDER = new int[]{ 0, -1, 1 };
        private static final int[][] BREAK_ORDER = new int[][]{
            { 0, 0 },
            { -1, 0 },
            { 0, 1 },
            { 1, 0 },
            { 0, -1 },
            { -1, -1 },
            { -1, 1 },
            { 1, 1 },
            { 1, -1 }
        };

        private final int breakLimit;
        private final Player player;
        private int blocksBroken = 0;

        public VeinMineTask(Block startBlock, int breakLimit, Player player) {
            this.breakLimit = breakLimit;
            this.player = player;

            startTask(startBlock);
        }

        private void startTask(Block block) {
            Block nextBlock = findNextBlock(block, block.getType());
            if (nextBlock != null) {
                Bukkit.getScheduler().runTaskLater(Effects.getInstance().getPlugin(), () -> {
                    breakConnectedBlocks(nextBlock);
                }, 5);
            }
        }

        private void breakConnectedBlocks(@NotNull Block block) {
            Material breakType = block.getType();
            Location location = block.getLocation();
            World world = block.getWorld();

            if (blocksBroken >= breakLimit) {
                return;
            }

            if (!Effects.getInstance().getPlugin().callEvent(new BlockBreakEvent(block, player))) {
                return;
            }

            // Handle block break
            blocksBroken += 1;
            block.breakNaturally();

            // Handle sounds/particles
            BlockData blockData = breakType.createBlockData();
            world.playSound(location.clone().add(0.5, 0.5, 0.5), blockData.getSoundGroup().getBreakSound(), 1f, 1f);
            world.spawnParticle(Particle.BLOCK, location.clone().add(0.5, 0.5, 0.5), 50, 0.3, 0.3, 0.3, blockData);

            // Extra check ran prior to finding next block to slightly improve performance
            if (blocksBroken >= breakLimit) {
                return;
            }

            // Find the next block to break and schedule
            Block nextBlock = findNextBlock(block, breakType);
            if (nextBlock != null) {
                Bukkit.getScheduler().runTaskLater(Effects.getInstance().getPlugin(), () -> {
                    breakConnectedBlocks(nextBlock);
                }, 5);
            }
        }

        public static Block findNextBlock(Block block, Material type) {
            return findNextBlock(block, Collections.singletonList(type));
        }

        public static Block findNextBlock(Block block, List<Material> types) {
            Location startLocation = block.getLocation();

            for (int y : VERTICAL_BREAK_ORDER) {
                for (int[] coords : BREAK_ORDER) {
                    int x = coords[0];
                    int z = coords[1];

                    if (x == 0 && y == 0 && z == 0) {
                        continue;
                    }

                    Location currLoc = startLocation.clone().add(x, y, z);
                    Block nextBlock = currLoc.getBlock();
                    if (types.contains(nextBlock.getType())) {
                        return nextBlock;
                    }
                }
            }

            return null;
        }
    }
}
