package org.lushplugins.regrowthsmp.module.glassitemframes.listener;

import org.bukkit.*;
import org.bukkit.entity.Item;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.lushplugins.regrowthsmp.module.glassitemframes.GlassItemFrames;
import org.lushplugins.regrowthsmp.module.glassitemframes.util.GlassItemFrameUtils;

public class ItemFrameListener implements Listener {

    @EventHandler
    public void onHangingPlace(HangingPlaceEvent event) {
        if (!(event.getEntity() instanceof ItemFrame itemFrame)) {
            return;
        }

        ItemStack item = event.getItemStack();
        if (item != null && GlassItemFrameUtils.isGlassItemFrame(item)) {
            itemFrame.setVisible(false);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onHangingBreak(HangingBreakEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (!(event.getEntity() instanceof ItemFrame itemFrame) || itemFrame.isVisible()) {
            return;
        }

        event.setCancelled(true);

        World world = itemFrame.getWorld();
        Location dropLocation = itemFrame.getLocation().add(itemFrame.getFacing().getDirection().multiply(0.3));

        itemFrame.remove();
        world.playSound(dropLocation, Sound.ENTITY_ITEM_FRAME_BREAK, SoundCategory.BLOCKS, 1f, 1f);
        Item frameDrop = world.dropItem(dropLocation, GlassItemFrameUtils.createGlassItemFrame());
        Item drop = world.dropItemNaturally(dropLocation, itemFrame.getItem());

        if (!GlassItemFrames.getInstance().getPlugin().callEvent(new EntityDropItemEvent(itemFrame, frameDrop))) {
            frameDrop.remove();
        }

        if (!GlassItemFrames.getInstance().getPlugin().callEvent(new EntityDropItemEvent(itemFrame, drop))) {
            drop.remove();
        }
    }
}
