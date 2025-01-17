package org.lushplugins.regrowthsmp.module.effects;

import org.bukkit.event.Listener;
import org.lushplugins.lushlib.module.Module;
import org.lushplugins.lushlib.plugin.SpigotPlugin;
import org.lushplugins.regrowthsmp.module.effects.data.UserManager;
import org.lushplugins.regrowthsmp.module.effects.effect.*;

import java.util.HashMap;

public class Effects extends Module {
    private static Effects instance;

    private final SpigotPlugin plugin;
    private final UserManager userManager = new UserManager();
    private final HashMap<String, Effect> effects = new HashMap<>();

    public Effects(SpigotPlugin plugin) {
        super("effects");
        this.plugin = plugin;

        if (instance == null) {
            instance = this;
        }

        registerEffect(new BetterPotionsEffect());
        registerEffect(new BoatBoostEffect());
        registerEffect(new ContactInvulnerabilityEffect());
        registerEffect(new FriendlyMobsEffect());
        registerEffect(new GrowthDanceEffect());
        registerEffect(new OreCruncherEffect());
        registerEffect(new StepEffect());
        registerEffect(new TreeChomperEffect());
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    public UserManager getUserManager() {
        return userManager;
    }

    public void registerEffect(Effect effect) {
        Effect oldEffect = effects.get(effect.getId());
        if (oldEffect instanceof Listener oldListener) {
            // TODO: Add SpigotPlugin#unregisterListener
            plugin.getServer().getPluginManager().registerEvents(oldListener, plugin);
        }

        effects.put(effect.getId(), effect);
        if (effect instanceof Listener listener) {
            plugin.registerListener(listener);
        }
    }

    public SpigotPlugin getPlugin() {
        return plugin;
    }

    public static Effects getInstance() {
        return instance;
    }
}
