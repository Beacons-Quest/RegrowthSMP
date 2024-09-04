package org.lushplugins.regrowthsmp.module.crateanimation;

import org.bukkit.plugin.java.JavaPlugin;
import org.lushplugins.lushlib.module.Module;
import org.lushplugins.lushlib.plugin.SpigotPlugin;
import org.lushplugins.regrowthsmp.module.crateanimation.listener.ExcellentCratesListener;
import org.lushplugins.regrowthsmp.module.crateanimation.opening.AnimatronicOpening;
import su.nightexpress.excellentcrates.CratesAPI;
import su.nightexpress.excellentcrates.opening.OpeningManager;

import java.util.HashSet;

public final class CrateAnimation extends Module {
    private static CrateAnimation instance;

    private final SpigotPlugin plugin;
    private final HashSet<String> lockedCrates = new HashSet<>();

    public CrateAnimation(SpigotPlugin plugin) {
        super("crate_animation");
        this.plugin = plugin;

        if (instance == null) {
            instance = this;
        }
    }

    @Override
    public void onEnable() {
        OpeningManager openingManager = CratesAPI.PLUGIN.getOpeningManager();
        openingManager.loadProvider("regrowth-default", AnimatronicOpening::new);

        new ExcellentCratesListener().registerListeners();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public boolean isCrateLocked(String crateName) {
        return lockedCrates.contains(crateName);
    }

    public void lockCrate(String crateName) {
        lockedCrates.add(crateName);
    }

    public void unlockCrate(String crateName) {
        lockedCrates.remove(crateName);
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public static CrateAnimation getInstance() {
        return instance;
    }
}
