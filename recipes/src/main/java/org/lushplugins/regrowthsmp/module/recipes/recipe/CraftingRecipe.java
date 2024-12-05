package org.lushplugins.regrowthsmp.module.recipes.recipe;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lushplugins.lushlib.utils.DisplayItemStack;
import org.lushplugins.regrowthsmp.module.recipes.utils.DisplayItemStackUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CraftingRecipe {
    private final List<DisplayItemStack> ingredients;
    private final DisplayItemStack result;
    private final boolean shapeless;

    private CraftingRecipe(List<DisplayItemStack> ingredients, DisplayItemStack result, boolean shapeless) {
        this.ingredients = Collections.unmodifiableList(ingredients);
        this.result = result;
        this.shapeless = shapeless;
    }

    public List<DisplayItemStack> getIngredients() {
        return ingredients;
    }

    /**
     * @param slot crafting slot 0-8
     */
    public @Nullable DisplayItemStack getIngredient(int slot) {
        return ingredients.get(slot);
    }

    public DisplayItemStack getResult() {
        return result;
    }

    public boolean isShapeless() {
        return shapeless;
    }

    public boolean matchesRecipe(ItemStack[] ingredients) {
        if (shapeless) {
            List<DisplayItemStack> unmatchedIngredients = new ArrayList<>(this.ingredients);

            for (ItemStack ingredient : ingredients) {
                if (ingredient == null) {
                    continue;
                }

                boolean found = false;
                for (DisplayItemStack unmatchedIngredient : unmatchedIngredients) {
                    if (DisplayItemStackUtil.isSimilar(unmatchedIngredient, ingredient)) {
                        found = true;
                        unmatchedIngredients.remove(unmatchedIngredient);
                        break;
                    }
                }

                if (!found) {
                    return false;
                }
            }
        } else {
            for (int slot = 0; slot < ingredients.length; slot++) {
                ItemStack ingredient = ingredients[slot];
                DisplayItemStack recipeIngredient = this.ingredients.get(slot);

                if (ingredient == null) {
                    if (recipeIngredient != null) {
                        return false;
                    } else {
                        continue;
                    }
                }

                if (recipeIngredient == null) {
                    return false;
                }

                if (!DisplayItemStackUtil.isSimilar(recipeIngredient, ingredient)) {
                    return false;
                }
            }
        }

        return true;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final List<DisplayItemStack> ingredients = new ArrayList<>();
        private DisplayItemStack result;
        private boolean shapeless = false;

        private Builder() {}

        /**
         * @param ingredient the ingredient to add
         */
        public Builder addIngredient(@NotNull DisplayItemStack ingredient) {
            if (ingredients.size() >= 9) {
                throw new IllegalArgumentException("No available slots");
            }

            ingredients.add(ingredient);
            return this;
        }

        /**
         * @param ingredient the ingredient to add
         * @param slot crafting slot 0-8
         */
        public Builder addIngredient(@NotNull DisplayItemStack ingredient, int slot) {
            if (slot < 0 || slot > 8) {
                throw new IllegalArgumentException("Slot out of bounds: " + slot);
            }

            ingredients.add(slot, ingredient);
            return this;
        }

        public Builder result(@NotNull DisplayItemStack result) {
            this.result = result;
            return this;
        }

        /**
         * @param shapeless whether the recipe should be shapeless
         */
        public Builder shapeless(boolean shapeless) {
            this.shapeless = shapeless;
            return this;
        }

        /**
         * @return a built recipe
         */
        public CraftingRecipe build() {
            if (result == null) {
                throw new IllegalArgumentException("Crafting recipe requires a result");
            }

            return new CraftingRecipe(ingredients, result, shapeless);
        }
    }
}
