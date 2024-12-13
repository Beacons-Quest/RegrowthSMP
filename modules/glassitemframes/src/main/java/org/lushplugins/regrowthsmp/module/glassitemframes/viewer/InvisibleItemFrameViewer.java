package org.lushplugins.regrowthsmp.module.glassitemframes.viewer;

import com.google.common.collect.HashMultimap;
import fr.skytasul.glowingentities.GlowingEntities;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.lushplugins.regrowthsmp.module.glassitemframes.GlassItemFrames;

import java.util.Collection;
import java.util.logging.Level;

public class InvisibleItemFrameViewer extends BukkitRunnable {
    private HashMultimap<Player, Integer> glowMap = HashMultimap.create();

    @Override
    public void run() {
        HashMultimap<Player, Integer> newGlowMap = HashMultimap.create();

        GlowingEntities glowingEntities = GlassItemFrames.getInstance().getGlowingEntities();
        for (Player player : Bukkit.getOnlinePlayers()) {
            ItemStack mainHand = player.getInventory().getItemInMainHand();
            ItemStack offHand = player.getInventory().getItemInOffHand();

            if (isInvisibleItemFrame(mainHand) || isInvisibleItemFrame(offHand)) {
                for (ItemFrame frame : getNearbyInvisibleFrames(player)) {
                    try {
                        // TODO: Swap glowing for temporarily displaying the frame as visible to the player
                        glowingEntities.setGlowing(frame, player, ChatColor.WHITE);

                        int entityId = frame.getEntityId();
                        newGlowMap.put(player, entityId);
                        glowMap.remove(player, entityId);
                    } catch (ReflectiveOperationException e) {
                        GlassItemFrames.getInstance().getPlugin().log(Level.WARNING, "Something went wrong when making item frame glow: ", e);
                    }
                }
            }

            glowMap.get(player).forEach(entityId -> {
                try {
                    glowingEntities.unsetGlowing(entityId, player);
                } catch (ReflectiveOperationException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        glowMap = newGlowMap;
    }

    private boolean isInvisibleItemFrame(ItemStack item) {
        if (item.getType() != Material.ITEM_FRAME) {
            return false;
        }

        ItemMeta itemMeta = item.getItemMeta();
        return itemMeta != null
            && itemMeta.hasCustomModelData()
            && itemMeta.getCustomModelData() == 1;
    }

    private Collection<ItemFrame> getNearbyInvisibleFrames(Player player) {
        return player.getWorld().getNearbyEntities(player.getLocation(), 5, 5, 5, (entity) -> entity instanceof ItemFrame frame && !frame.isVisible()).stream()
            .map(entity -> (ItemFrame) entity)
            .toList();
    }
}
