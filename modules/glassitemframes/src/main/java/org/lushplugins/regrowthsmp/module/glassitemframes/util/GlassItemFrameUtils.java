package org.lushplugins.regrowthsmp.module.glassitemframes.util;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.lushplugins.lushlib.utils.DisplayItemStack;

public class GlassItemFrameUtils {
    private static final DisplayItemStack GLASS_ITEM_FRAME = DisplayItemStack.builder()
        .setType(Material.ITEM_FRAME)
        .setDisplayName("&rGlass Item Frame")
        .setCustomModelData(1)
        .build();

    public static boolean isGlassItemFrame(ItemStack item) {
        if (item.getType() != Material.ITEM_FRAME) {
            return false;
        }

        ItemMeta itemMeta = item.getItemMeta();
        return itemMeta != null
            && itemMeta.hasCustomModelData()
            && itemMeta.getCustomModelData() == 1;
    }

    public static boolean isGlassItemFrame(Entity entity) {
        return entity instanceof ItemFrame frame && !frame.isVisible();
    }

    public static ItemStack createGlassItemFrame() {
        return GLASS_ITEM_FRAME.asItemStack();
    }
}
