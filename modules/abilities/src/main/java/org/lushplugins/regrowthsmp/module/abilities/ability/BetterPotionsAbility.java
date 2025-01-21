package org.lushplugins.regrowthsmp.module.abilities.ability;

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
import org.lushplugins.regrowthsmp.module.abilities.Abilities;
import org.lushplugins.regrowthsmp.module.abilities.data.AbilitiesUser;

public class BetterPotionsAbility extends Ability implements Listener {

    public BetterPotionsAbility() {
        super(AbilityTypes.BETTER_POTIONS);
    }

    @EventHandler
    public void onConsumePotion(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        AbilitiesUser user = Abilities.getInstance().getCachedUserData(player.getUniqueId());
        if (user == null || !user.getCurrentAbility().equals(this.getId())) {
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

        Bukkit.getScheduler().runTaskLater(Abilities.getInstance().getPlugin(), () -> {
            for (PotionEffect potionEffect : potionType.getPotionEffects()) {
                PotionEffect amplifiedEffect = potionEffect.withAmplifier(potionEffect.getAmplifier() + 1);
                player.addPotionEffect(amplifiedEffect);
            }
        }, 1);
    }
}
