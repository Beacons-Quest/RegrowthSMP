package org.lushplugins.regrowthsmp.module.effects.effect;

import org.bukkit.Bukkit;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.util.Vector;
import org.lushplugins.regrowthsmp.module.effects.Effects;
import org.lushplugins.regrowthsmp.module.effects.data.EffectsUser;

import java.util.HashSet;
import java.util.UUID;

public class BoatBoostEffect extends Effect implements Listener {
    private final HashSet<UUID> onCooldown = new HashSet<>();

    public BoatBoostEffect() {
        super("boat_boost");
    }

    @EventHandler
    public void onReelingIn(PlayerFishEvent event) {
        PlayerFishEvent.State state = event.getState();
        if (state != PlayerFishEvent.State.FAILED_ATTEMPT && state != PlayerFishEvent.State.REEL_IN) {
            return;
        }

        Player player = event.getPlayer();
        EffectsUser user = Effects.getInstance().getUserManager().getUser(player.getUniqueId());
        if (user == null || !user.getCurrentEffect().equals(this.getId())) {
            return;
        }

        if (!player.isInsideVehicle()) {
            return;
        }

        Entity vehicle = player.getVehicle();
        if (!(vehicle instanceof Boat boat)) {
            return;
        }

        if (onCooldown.contains(player.getUniqueId())) {
            return;
        }

        if (!boat.isInWater() && boat.getWorld().isClearWeather()) {
            return;
        }

        // Handle applying cooldown
        UUID uuid = player.getUniqueId();
        onCooldown.add(uuid);
        Bukkit.getScheduler().runTaskLaterAsynchronously(Effects.getInstance().getPlugin(), () -> {
            onCooldown.remove(uuid);
        }, 20);

        FishHook hook = event.getHook();
        Vector direction = hook.getLocation().toVector().subtract(player.getLocation().toVector());
        boat.setVelocity(
            direction
                .normalize()
                .multiply(2)
                .setY(0.5)
        );
    }
}
