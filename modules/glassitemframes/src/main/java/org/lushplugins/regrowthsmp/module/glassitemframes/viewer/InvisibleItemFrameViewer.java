package org.lushplugins.regrowthsmp.module.glassitemframes.viewer;

import com.google.common.collect.HashMultimap;
import fr.skytasul.glowingentities.GlowingEntities;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.lushplugins.regrowthsmp.module.glassitemframes.GlassItemFrames;
import org.lushplugins.regrowthsmp.module.glassitemframes.util.GlassItemFrameUtils;

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

            if (GlassItemFrameUtils.isGlassItemFrame(mainHand) || GlassItemFrameUtils.isGlassItemFrame(offHand)) {
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

    private Collection<ItemFrame> getNearbyInvisibleFrames(Player player) {
        return player.getWorld().getNearbyEntities(player.getLocation(), 5, 5, 5, GlassItemFrameUtils::isGlassItemFrame).stream()
            .map(entity -> (ItemFrame) entity)
            .toList();
    }
}
