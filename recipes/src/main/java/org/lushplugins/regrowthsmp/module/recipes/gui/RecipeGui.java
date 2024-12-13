package org.lushplugins.regrowthsmp.module.recipes.gui;

import com.google.common.collect.TreeMultimap;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.lushplugins.lushlib.gui.button.SimpleItemButton;
import org.lushplugins.lushlib.gui.inventory.Gui;
import org.lushplugins.lushlib.gui.inventory.GuiFormat;
import org.lushplugins.lushlib.libraries.chatcolor.ChatColorHandler;
import org.lushplugins.lushlib.utils.DisplayItemStack;
import org.lushplugins.regrowthsmp.module.recipes.Recipes;
import org.lushplugins.regrowthsmp.module.recipes.recipe.CraftingRecipe;

public class RecipeGui extends Gui {
    private final CraftingRecipe recipe;

    public RecipeGui(CraftingRecipe recipe, Player player) {
        super(
            Recipes.getInstance().getConfigManager().getRecipeGuiFormat().getSize(),
            recipe.getResult().hasDisplayName() ? ChatColorHandler.translate(recipe.getResult().getDisplayName()) : "Recipe",
            player);
        this.recipe = recipe;
    }

    @Override
    public void refresh() {
        GuiFormat format = Recipes.getInstance().getConfigManager().getRecipeGuiFormat();
        TreeMultimap<Character, Integer> slotMap = format.getSlotMap();
        for (Character character : slotMap.keySet()) {
            if (Character.isDigit(character)) {
                int recipeSlot = Integer.parseInt(String.valueOf(character));
                DisplayItemStack displayItemStack = recipe.getIngredient(recipeSlot);
                if (displayItemStack == null) {
                    continue;
                }

                if (displayItemStack.hasCustomModelData()) {
                    switch (displayItemStack.getType()) {
                        case Material.APPLE -> {
                            String displayName = null;
                            switch (displayItemStack.getCustomModelData()) {
                                case 1 -> displayName = "&rCherry";
                                case 2 -> displayName = "&rGrape";
                                case 3 -> displayName = "&rLemon";
                                case 4 -> displayName = "&rOrange";
                                case 5 -> displayName = "&rPeach";
                                case 6 -> displayName = "&rStrawberry";
                                case 7 -> displayName = "&rWither Apple";
                            }

                            if (displayName != null) {
                                displayItemStack = DisplayItemStack.builder(displayItemStack)
                                    .setDisplayName(displayName)
                                    .build();
                            }
                        }
                        case Material.PAPER -> {
                            if (displayItemStack.getCustomModelData() == 101) {
                                displayItemStack = DisplayItemStack.builder(displayItemStack)
                                    .setDisplayName("&rSeashell")
                                    .build();
                            }
                        }
                        case null, default -> {}
                    }
                }

                ItemStack itemStack = displayItemStack.asItemStack(this.getPlayer());
                for (int slot : slotMap.get(character)) {
                    setItem(slot, itemStack);
                }
            } else if (character == 'o') {
                ItemStack itemStack = recipe.getResult().asItemStack(this.getPlayer());
                for (int slot : slotMap.get(character)) {
                    setItem(slot, itemStack);
                }
            } else if (character == 'b') {
                DisplayItemStack displayItemStack = format.getItemReference(character);
                if (displayItemStack == null) {
                    continue;
                }

                SimpleItemButton button = new SimpleItemButton(displayItemStack, (event) -> {
                    RecipesGui gui = new RecipesGui((Player) event.getWhoClicked());
                    gui.open();
                });

                for (int slot : slotMap.get(character)) {
                    addButton(slot, button);
                }
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
