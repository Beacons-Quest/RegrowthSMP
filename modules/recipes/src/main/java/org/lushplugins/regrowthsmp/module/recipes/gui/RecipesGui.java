package org.lushplugins.regrowthsmp.module.recipes.gui;

import com.google.common.collect.TreeMultimap;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.lushplugins.lushlib.gui.inventory.GuiFormat;
import org.lushplugins.lushlib.gui.inventory.PagedGui;
import org.lushplugins.lushlib.libraries.chatcolor.ChatColorHandler;
import org.lushplugins.lushlib.utils.DisplayItemStack;
import org.lushplugins.regrowthsmp.module.recipes.Recipes;
import org.lushplugins.regrowthsmp.module.recipes.gui.button.RecipeButton;
import org.lushplugins.regrowthsmp.module.recipes.recipe.CraftingRecipe;

import java.util.Comparator;
import java.util.List;

public class RecipesGui extends PagedGui {

    public RecipesGui(Player player) {
        super(
            Recipes.getInstance().getConfigManager().getRecipesGuiFormat().getSize(),
            ChatColorHandler.translate(Recipes.getInstance().getConfigManager().getGuiTitle()),
            player);
    }

    @Override
    public void refresh() {
        GuiFormat format = Recipes.getInstance().getConfigManager().getRecipesGuiFormat();
        TreeMultimap<Character, Integer> slotMap = format.getSlotMap();
        for (Character character : slotMap.keySet()) {
            if (character ==  'r') {
                List<CraftingRecipe> recipes = Recipes.getInstance().getConfigManager().getRecipes().stream()
                    .sorted(Comparator.comparing(o -> o.getKey().asString()))
                    .toList();

                int index = 0;
                for (int slot : slotMap.get(character)) {
                    if (index >= recipes.size()) {
                        break;
                    }

                    CraftingRecipe recipe = recipes.get(index);
                    DisplayItemStack result = recipe.getResult();
                    DisplayItemStack displayItemStack = format.getItemReference(character);
                    if (displayItemStack != null) {
                        result = DisplayItemStack.builder(displayItemStack)
                            .overwrite(DisplayItemStack.builder(result))
                            .build();
                    }

                    addButton(slot, new RecipeButton(result, recipe.getKey()));
                    index++;
                }
                Recipes.getInstance().getConfigManager().getRecipes();
            } else {
                DisplayItemStack displayItemStack = format.getItemReference(character);
                if (displayItemStack == null) {
                    continue;
                }

                ItemStack itemStack = displayItemStack.asItemStack(this.getPlayer());
                for (int slot : slotMap.get(character)) {
                    setItem(slot, itemStack);
                }
            }
        }

        super.refresh();
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        super.onClick(event, true);
    }
}
