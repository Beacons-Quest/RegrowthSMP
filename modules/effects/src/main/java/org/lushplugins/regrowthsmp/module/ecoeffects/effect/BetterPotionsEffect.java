package org.lushplugins.regrowthsmp.module.ecoeffects.effect;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.lushplugins.regrowthsmp.module.ecoeffects.Effects;

public class BetterPotionsEffect extends Effect implements Listener {

    public BetterPotionsEffect() {
        super("better_potions");
    }

    @EventHandler
    public void onConsumePotion(PlayerItemConsumeEvent event) {
        // TODO: Add check for player with effect

        ItemStack item = event.getItem();
        ItemMeta itemMeta = item.getItemMeta();
        if (!(itemMeta instanceof PotionMeta potionMeta)) {
            return;
        }

        Player player = event.getPlayer();
        Bukkit.getScheduler().runTaskLater(Effects.getInstance().getPlugin(), () -> {
            for (PotionEffect potionEffect : potionMeta.getCustomEffects()) {
                PotionEffect amplifiedEffect = potionEffect.withAmplifier(potionEffect.getAmplifier() + 1);
                player.addPotionEffect(amplifiedEffect);
            }
        }, 1);
    }
}
