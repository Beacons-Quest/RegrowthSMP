package org.lushplugins.regrowthsmp.module.crateanimation.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.lushplugins.lushlib.listener.EventListener;
import org.lushplugins.regrowthsmp.module.crateanimation.CrateAnimation;
import su.nightexpress.excellentcrates.api.event.CrateOpenEvent;
import su.nightexpress.excellentcrates.crate.impl.Crate;

import java.util.Objects;

public class ExcellentCratesListener implements EventListener {

    @EventHandler
    public void onCrateOpen(CrateOpenEvent event) {
        Crate crate = event.getCrate();
        if (!Objects.equals(crate.getOpeningConfig(), "regrowth-default")) {
            return;
        }

        String crateName = crate.getName();
        if (CrateAnimation.getInstance().isCrateLocked(crateName)) {
            event.setCancelled(true);
        } else {
            CrateAnimation.getInstance().lockCrate(crateName);
        }
    }
}