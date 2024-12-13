package org.lushplugins.regrowthsmp.module.glassitemframes.listener;

import org.bukkit.*;
import org.bukkit.entity.Item;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.lushplugins.lushlib.utils.DisplayItemStack;
import org.lushplugins.regrowthsmp.module.glassitemframes.GlassItemFrames;

public class ItemFrameListener implements Listener {
    private static final DisplayItemStack GLASS_ITEM_FRAME = DisplayItemStack.builder()
        .setType(Material.ITEM_FRAME)
        .setDisplayName("&rGlass Item Frame")
        .setCustomModelData(1)
        .build();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onHangingBreak(HangingBreakEvent event) {
        if (!(event.getEntity() instanceof ItemFrame itemFrame) || itemFrame.isVisible()) {
            return;
        }

        event.setCancelled(true);

        World world = itemFrame.getWorld();
        Location dropLocation = itemFrame.getLocation().add(itemFrame.getFacing().getDirection().multiply(0.3));

        itemFrame.remove();
        world.playSound(dropLocation, Sound.ENTITY_ITEM_FRAME_BREAK, SoundCategory.BLOCKS, 1f, 1f);
        Item frameDrop = world.dropItem(dropLocation, createGlassFrame());
        Item drop = world.dropItemNaturally(dropLocation, itemFrame.getItem());

        if (!GlassItemFrames.getInstance().getPlugin().callEvent(new EntityDropItemEvent(itemFrame, frameDrop))) {
            frameDrop.remove();
        }

        if (!GlassItemFrames.getInstance().getPlugin().callEvent(new EntityDropItemEvent(itemFrame, drop))) {
            drop.remove();
        }
    }

    private ItemStack createGlassFrame() {
        return Bukkit.getServer().getUnsafe().modifyItemStack(GLASS_ITEM_FRAME.asItemStack(), "minecraft:item_frame[entity_data={id:\"minecraft:item_frame\",Invisible:1b}]");
    }
}
