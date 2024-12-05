package org.lushplugins.regrowthsmp.module.recipes.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.lushplugins.lushlib.utils.DisplayItemStack;
import org.lushplugins.lushlib.utils.YamlUtils;
import org.lushplugins.lushlib.utils.converter.YamlConverter;
import org.lushplugins.regrowthsmp.module.recipes.Recipes;
import org.lushplugins.regrowthsmp.module.recipes.recipe.CraftingRecipe;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

public class ConfigManager {
    private HashMap<String, CraftingRecipe> recipes;

    public ConfigManager() {
        Recipes.getInstance().getPlugin().saveDefaultResource("modules/recipes.yml");
    }

    public void reloadConfig() {
        ConfigurationSection config = YamlConfiguration.loadConfiguration(new File(Recipes.getInstance().getPlugin().getDataFolder(), "modules/recipes.yml"));

        this.recipes = new HashMap<>();
        List<ConfigurationSection> recipeSections = YamlUtils.getConfigurationSections(config, "recipes");
        for (ConfigurationSection recipeSection : recipeSections) {
            CraftingRecipe.Builder recipeBuilder = CraftingRecipe.builder();

            boolean shapeless = recipeSection.getBoolean("shapeless");
            recipeBuilder.shapeless(shapeless);

            List<ConfigurationSection> ingredientSections = YamlUtils.getConfigurationSections(config, "ingredients");
            for (ConfigurationSection ingredientSection : ingredientSections) {
                DisplayItemStack ingredient = YamlConverter.getDisplayItem(ingredientSection);
                if (shapeless) {
                    recipeBuilder.addIngredient(ingredient);
                } else {
                    int slot = Integer.parseInt(ingredientSection.getName());
                    recipeBuilder.addIngredient(ingredient, slot);
                }
            }

            ConfigurationSection resultSection = recipeSection.getConfigurationSection("result");
            if (resultSection != null) {
                recipeBuilder.result(YamlConverter.getDisplayItem(resultSection));
            }

            try {
                this.recipes.put(recipeSection.getName(), recipeBuilder.build());
            } catch (IllegalArgumentException e) {
                Recipes.getInstance().getPlugin().log(Level.WARNING, "Failed to load recipe: " + recipeSection.getName(), e);
            }
        }
    }

    public Collection<CraftingRecipe> getRecipes() {
        return recipes.values();
    }
}
