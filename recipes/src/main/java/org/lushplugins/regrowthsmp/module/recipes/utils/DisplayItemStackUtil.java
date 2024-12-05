package org.lushplugins.regrowthsmp.module.recipes.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.lushplugins.lushlib.utils.DisplayItemStack;
import org.lushplugins.lushlib.utils.IntRange;

import java.util.List;

public class DisplayItemStackUtil {

    // TODO: Remove when updating LushLib
    public static boolean isSimilar(DisplayItemStack displayItemStack, @NotNull ItemStack itemStack) {
        Material material = displayItemStack.getType();
        if (material != null && itemStack.getType() != material) {
            return false;
        }

        IntRange amount = displayItemStack.getAmount();
        // TODO: Add IntRange#contains(int) to LushLib
        if (itemStack.getAmount() < amount.getMin() || itemStack.getAmount() > amount.getMax()) {
            return false;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        // TODO: Add DisplayItemStack#hasMeta to LushLib
        if (itemMeta == null) {
            return !displayItemStack.hasDisplayName() && !displayItemStack.hasLore() && !displayItemStack.hasEnchantGlow() && !displayItemStack.hasCustomModelData() && !displayItemStack.hasSkullTexture();
        }

        String displayName = displayItemStack.getDisplayName();
        if (displayName != null && !itemMeta.getDisplayName().equals(displayName)) {
            return false;
        }

        List<String> lore = displayItemStack.getLore();
        if (lore != null && (itemMeta.getLore() == null || !itemMeta.getLore().equals(lore))) {
            return false;
        }

        Boolean enchantGlow = displayItemStack.getEnchantGlow();
        if (enchantGlow != null && !itemMeta.getDisplayName().equals(displayName)) {
            return false;
        }

        int customModelData = displayItemStack.getCustomModelData();
        if (customModelData != 0 && (!itemMeta.hasCustomModelData() || itemMeta.getCustomModelData() != customModelData)) {
            return false;
        }

        return true;
    }
}
