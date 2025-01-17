package org.lushplugins.regrowthsmp.module.effects.effect;

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
import org.lushplugins.regrowthsmp.module.effects.Effects;
import org.lushplugins.regrowthsmp.module.effects.data.EffectsUser;

public class StepEffect extends Effect implements Listener {

    public StepEffect() {
        super("step");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        EffectsUser user = Effects.getInstance().getUserManager().getUser(player.getUniqueId());
        if (user == null || !user.getCurrentEffect().equals(this.getId())) {
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
            new NamespacedKey(Effects.getInstance().getPlugin(), "effects_step"),
            0.5,
            AttributeModifier.Operation.ADD_NUMBER
        ));
    }
}
