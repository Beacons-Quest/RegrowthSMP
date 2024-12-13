package org.lushplugins.regrowthsmp.module.recipes.listener;

import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.lushplugins.regrowthsmp.module.recipes.Recipes;

import java.util.Set;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Set<NamespacedKey> recipeKeys = Recipes.getInstance().getConfigManager().getRecipeKeys();
        if (Recipes.getInstance().getConfigManager().showInRecipeBook()) {
            event.getPlayer().discoverRecipes(recipeKeys);
        } else {
            event.getPlayer().undiscoverRecipes(recipeKeys);
        }
    }
}
