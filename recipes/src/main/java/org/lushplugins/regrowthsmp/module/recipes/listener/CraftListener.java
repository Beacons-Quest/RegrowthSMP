package org.lushplugins.regrowthsmp.module.recipes.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.lushplugins.regrowthsmp.module.recipes.Recipes;

public class CraftListener implements Listener {

    @EventHandler
    public void onCraftPrepare(PrepareItemCraftEvent event) {
        ItemStack[] ingredients = event.getInventory().getMatrix();
        Recipes.getInstance().getConfigManager().getRecipes().stream()
            .filter(recipe -> recipe.matchesRecipe(ingredients))
            .findFirst()
            .ifPresent(recipe -> event.getInventory().setResult(recipe.getResult().asItemStack()));
    }
}
