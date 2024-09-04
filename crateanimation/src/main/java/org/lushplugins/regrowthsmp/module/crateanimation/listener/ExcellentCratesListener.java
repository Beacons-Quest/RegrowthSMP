package org.lushplugins.regrowthsmp.module.crateanimation.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.lushplugins.regrowthsmp.module.crateanimation.CrateAnimation;
import su.nightexpress.excellentcrates.api.event.CrateOpenEvent;

public class ExcellentCratesListener implements Listener {

    @EventHandler
    public void onCrateInteract(CrateOpenEvent event) {
        if (CrateAnimation.getInstance().isCrateLocked(event.getCrate().getName())) {
            event.setCancelled(true);
        }
    }
}