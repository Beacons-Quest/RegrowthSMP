package org.lushplugins.regrowthsmp.module.abilities.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.lushplugins.lushlib.gui.inventory.Gui;
import org.lushplugins.lushlib.libraries.chatcolor.ChatColorHandler;
import org.lushplugins.lushlib.utils.DisplayItemStack;
import org.lushplugins.regrowthsmp.module.abilities.ability.AbilityTypes;
import org.lushplugins.regrowthsmp.module.abilities.gui.button.AbilityButton;

import java.util.List;

public class AbilitiesMenu extends Gui {
    private static final int[] BORDER_SLOTS = new int[]{
        1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 16, 17, 18, 19, 20, 24, 25, 26, 28, 29, 30, 31, 32, 33, 34
    };
    private static final int[] CORNER_SLOTS = new int[]{
        0, 8, 27, 35
    };

    public AbilitiesMenu(Player player) {
        super(36, ChatColorHandler.translate("&#65A74BAbilities"), player);

        // &#FFD392 - highlight
        // &#A7A4A0
        addButton(11, new AbilityButton(AbilityTypes.BETTER_POTIONS, Material.OMINOUS_BOTTLE, List.of("Gain an &#FFD392extra potion level &#A7A4A0for", "consumed potions")));
        addButton(12, new AbilityButton(AbilityTypes.BOAT_BOOST, Material.SPRUCE_BOAT, List.of("Gain a &#FFD392speed boost &#A7A4A0when using a", "fishing rod from within a boat")));
        addButton(13, new AbilityButton(AbilityTypes.CONTACT_INVULNERABILITY, Material.SHIELD, List.of("Become &#FFD392immune &#A7A4A0to damage from", "&#FFD392sweet berries&#A7A4A0, &#FFD392cactus&#A7A4A0, &#FFD392magma blocks", "and other contact related damage")));
        addButton(14, new AbilityButton(AbilityTypes.FRIENDLY_MOBS, Material.ZOMBIE_SPAWN_EGG, List.of("Become frenemies with Zombies, Skeletons", "and Spiders, they will no longer", "attack you unless provoked")));
        addButton(15, new AbilityButton(AbilityTypes.GROWTH_DANCE, Material.BEETROOT_SEEDS, List.of("Crouch lots near crops to encourage", "the worms to dance and &#FFD392grow your", "&#FFD392crops faster")));
        addButton(21, new AbilityButton(AbilityTypes.ORE_CRUNCHER, Material.GOLDEN_PICKAXE, List.of("Crouch whilst mining ores with", "a pickaxe to &#FFD392break multiple", "&#FFD392connected ores &#A7A4A0in one go")));
        addButton(22, new AbilityButton(AbilityTypes.STEP, Material.LEATHER_BOOTS, List.of("&#FFD392Run up inclines &#A7A4A0without having to jump,", "you can now walk up steeper heights")));
        addButton(23, new AbilityButton(AbilityTypes.TREE_CHOMPER, Material.GOLDEN_AXE, List.of("Chomp your way through a tree with ease", "by &#FFD392breaking multiple connected logs &#A7A4A0by", "crouching whilst mining with an axe")));

        ItemStack border = DisplayItemStack.builder(Material.LIME_STAINED_GLASS_PANE)
            .setDisplayName("&r")
            .build()
            .asItemStack();
        for (int slot : BORDER_SLOTS) {
            setItem(slot, border);
        }

        ItemStack corner = DisplayItemStack.builder(Material.SUNFLOWER)
            .setDisplayName("&r")
            .build()
            .asItemStack();
        for (int slot : CORNER_SLOTS) {
            setItem(slot, corner);
        }
    }
}
