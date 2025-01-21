package org.lushplugins.regrowthsmp.module.abilities.ability;

import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.lushplugins.lushlib.registry.RegistryUtils;
import org.lushplugins.regrowthsmp.module.abilities.Abilities;
import org.lushplugins.regrowthsmp.module.abilities.data.AbilitiesUser;

public class StepAbility extends Ability implements Listener {

    public StepAbility() {
        super(AbilityTypes.STEP);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        AbilitiesUser user = Abilities.getInstance().getCachedUserData(player.getUniqueId());
        if (user == null || !user.getCurrentAbility().equals(this.getId())) {
            return;
        }

        Attribute attribute = RegistryUtils.parseString("generic.step_height", Registry.ATTRIBUTE);
        if (attribute == null) {
            return;
        }

        AttributeInstance attributeInstance = player.getAttribute(attribute);
        if (attributeInstance == null) {
            return;
        }

        attributeInstance.addTransientModifier(new AttributeModifier(
            new NamespacedKey(Abilities.getInstance().getPlugin(), "effects_step"),
            0.5,
            AttributeModifier.Operation.ADD_NUMBER
        ));
    }
}
