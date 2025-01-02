package org.lushplugins.regrowthsmp.module.effects.effect;

import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.util.Vector;

public class BoatBoostEffect extends Effect implements Listener {

    public BoatBoostEffect() {
        super("boat_boost");
    }

    @EventHandler
    public void onReelingIn(PlayerFishEvent event) {
        if (!switch (event.getState()) {
            case CAUGHT_FISH, CAUGHT_ENTITY, FAILED_ATTEMPT, REEL_IN -> true;
            default -> false;
        }) {
            return;
        }

        Player player = event.getPlayer();
        if (!player.isInsideVehicle()) {
            return;
        }

        Entity vehicle = player.getVehicle();
        if (!(vehicle instanceof Boat boat)) {
            return;
        }

        Vector boatVelocity = boat.getLocation().getDirection();
        boat.setVelocity(new Vector(
            boatVelocity.getX() * 1.5,
            boatVelocity.getY(),
            boatVelocity.getZ() * 1.5
        ));
    }
}
