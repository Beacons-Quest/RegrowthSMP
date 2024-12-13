package org.lushplugins.regrowthsmp.module.recipes.gui.button;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.lushplugins.lushlib.gui.button.SimpleItemButton;
import org.lushplugins.lushlib.utils.DisplayItemStack;
import org.lushplugins.regrowthsmp.module.recipes.Recipes;
import org.lushplugins.regrowthsmp.module.recipes.gui.RecipeGui;

public class RecipeButton extends SimpleItemButton {

    public RecipeButton(DisplayItemStack item, NamespacedKey key) {
        super(item, (event) -> {
            Player player = (Player) event.getWhoClicked();
            RecipeGui gui = new RecipeGui(Recipes.getInstance().getConfigManager().getRecipe(key), player);
            gui.open();
        });
    }
}
