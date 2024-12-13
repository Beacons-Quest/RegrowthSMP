package org.lushplugins.regrowthsmp.module.recipes.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerRecipeDiscoverEvent;
import org.bukkit.inventory.*;
import org.lushplugins.lushlib.utils.DisplayItemStack;
import org.lushplugins.regrowthsmp.module.recipes.Recipes;
import org.lushplugins.regrowthsmp.module.recipes.config.ConfigManager;
import org.lushplugins.regrowthsmp.module.recipes.recipe.CraftingRecipe;

import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("DuplicatedCode")
public class CraftListener implements Listener {

    @EventHandler
    public void onCraftPrepare(PrepareItemCraftEvent event) {
        Recipe recipe = event.getRecipe();
        if (!(recipe instanceof org.bukkit.inventory.CraftingRecipe craftingRecipe)) {
            return;
        }

        CraftingRecipe storedRecipe = Recipes.getInstance().getConfigManager().getRecipe(craftingRecipe.getKey());
        if (storedRecipe == null || !storedRecipe.isCustom()) {
            return;
        }

        ItemStack[] ingredients = event.getInventory().getMatrix();
        if (!storedRecipe.matchesRecipe(ingredients)) {
            event.getInventory().setResult(null);
        }
    }

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        Recipe recipe = event.getRecipe();
        if (!(recipe instanceof org.bukkit.inventory.CraftingRecipe craftingRecipe)) {
            return;
        }

        CraftingRecipe storedRecipe = Recipes.getInstance().getConfigManager().getRecipe(craftingRecipe.getKey());
        if (storedRecipe == null || !storedRecipe.isCustom()) {
            return;
        }

        ItemStack[] ingredients;
        if (recipe instanceof ShapedRecipe shapedRecipe) {
            ingredients = shapedRecipe.getChoiceMap().values().stream()
                .map(choice -> choice != null ? choice.getItemStack() : null)
                .toArray(ItemStack[]::new);
        } else if (recipe instanceof ShapelessRecipe shapelessRecipe) {
            ingredients = shapelessRecipe.getChoiceList().stream()
                .map(choice -> choice != null ? choice.getItemStack() : null)
                .toArray(ItemStack[]::new);
        } else {
            return;
        }

        List<DisplayItemStack> unmatchedIngredients = Arrays.stream(storedRecipe.getIngredients())
            .filter(Objects::nonNull)
            .collect(Collectors.toCollection(ArrayList::new));

        for (ItemStack ingredient : ingredients) {
            if (ingredient == null) {
                continue;
            }

            for (DisplayItemStack unmatchedIngredient : Collections.unmodifiableCollection(unmatchedIngredients)) {
                if (unmatchedIngredient == null) {
                    continue;
                }

                if (unmatchedIngredient.isSimilar(ingredient)) {
                    ingredient.setAmount(ingredient.getAmount() - unmatchedIngredient.getAmount().getMin());
                    unmatchedIngredients.remove(unmatchedIngredient);
                    break;
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDiscoverRecipe(PlayerRecipeDiscoverEvent event) {
        ConfigManager configManager = Recipes.getInstance().getConfigManager();
        if (!configManager.showInRecipeBook() && configManager.getRecipe(event.getRecipe()) != null) {
            event.setCancelled(true);
        }
    }
}
