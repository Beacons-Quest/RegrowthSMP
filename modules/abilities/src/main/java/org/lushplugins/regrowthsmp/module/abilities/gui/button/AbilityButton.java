package org.lushplugins.regrowthsmp.module.abilities.gui.button;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.lushplugins.lushlib.gui.button.ItemButton;
import org.lushplugins.lushlib.gui.inventory.Gui;
import org.lushplugins.lushlib.manager.GuiManager;
import org.lushplugins.lushlib.utils.DisplayItemStack;
import org.lushplugins.regrowthsmp.module.abilities.Abilities;
import org.lushplugins.regrowthsmp.module.abilities.data.AbilitiesUser;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AbilityButton extends ItemButton {
    private final String ability;
    private final DisplayItemStack item;

    public AbilityButton(String ability, Material material, List<String> description) {
        super((event) -> {
            HumanEntity entity = event.getWhoClicked();
            // TODO: Remove permission check and instead hook into EcoSkills to check their level in a skill
            if (!entity.hasPermission("regrowthsmp.abilities." + ability)) {
                return;
            }

            UUID uuid = entity.getUniqueId();
            AbilitiesUser user = Abilities.getInstance().getCachedUserData(uuid);
            if (user != null) {
                user.setCurrentAbility(ability);

                Abilities.getInstance().getPlugin().getManager(GuiManager.class).ifPresent(manager -> {
                    Gui gui = manager.getGui(uuid);
                    if (gui != null) {
                        gui.refresh();
                    }
                });
            }
        });

        this.ability = ability;
        this.item = DisplayItemStack.builder(material)
            .setDisplayName("&#FBC067&l" + makeFriendly(ability))
            .setLore(description.stream().map(line -> "&#A7A4A0" + line).toList())
            .build();
    }

    @Override
    public ItemStack getItemStack(Player player) {
        DisplayItemStack.Builder itemBuilder = DisplayItemStack.builder(this.item);
        List<String> lore = itemBuilder.hasLore() ? new ArrayList<>(itemBuilder.getLore()) : new ArrayList<>();

        // TODO: Remove permission check and instead hook into EcoSkills to check their level in a skill
        if (player.hasPermission("regrowthsmp.abilities." + ability)) {
            AbilitiesUser user = Abilities.getInstance().getCachedUserData(player.getUniqueId());
            if (user != null) {
                lore.add(" ");
                lore.add("&#A7A4A0Active: &#FFD392" + (user.getCurrentAbility().equals(ability) ? "true" : "false"));
            }
        } else {
            lore.add(" ");
            lore.add("&#ff6969&oReach level 10 Foraging in /skills to unlock this");
        }

        ItemStack item = DisplayItemStack.builder(this.item)
            .setLore(lore)
            .build()
            .asItemStack();

        item.addItemFlags(
            ItemFlag.HIDE_ATTRIBUTES,
            ItemFlag.HIDE_ADDITIONAL_TOOLTIP
        );

        return item;
    }

    private static String makeFriendly(String string) {
        StringBuilder output = new StringBuilder();

        String[] words = string.toLowerCase().split("[ _]");
        for (int i = 0; i < words.length; i++) {
            if (i > 0) {
                output.append(" ");
            }

            String word = words[i];
            output.append(word.substring(0, 1).toUpperCase()).append(word.substring(1));
        }

        return output.toString();
    }
}
