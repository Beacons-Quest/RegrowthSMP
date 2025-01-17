package org.lushplugins.regrowthsmp.module.effects.effect;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;
import org.lushplugins.regrowthsmp.module.effects.Effects;
import org.lushplugins.regrowthsmp.module.effects.data.EffectsUser;

public class BetterPotionsEffect extends Effect implements Listener {

    public BetterPotionsEffect() {
        super("better_potions");
    }

    @EventHandler
    public void onConsumePotion(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        EffectsUser user = Effects.getInstance().getUserManager().getUser(player.getUniqueId());
        if (user == null || !user.getCurrentEffect().equals(this.getId())) {
            return;
        }

        ItemStack item = event.getItem();
        ItemMeta itemMeta = item.getItemMeta();
        if (!(itemMeta instanceof PotionMeta potionMeta)) {
            return;
        }

        PotionType potionType = potionMeta.getBasePotionType();
        if (potionType == null) {
            return;
        }

        Bukkit.getScheduler().runTaskLater(Effects.getInstance().getPlugin(), () -> {
            for (PotionEffect potionEffect : potionType.getPotionEffects()) {
                PotionEffect amplifiedEffect = potionEffect.withAmplifier(potionEffect.getAmplifier() + 1);
                player.addPotionEffect(amplifiedEffect);
            }
        }, 1);
    }
}
