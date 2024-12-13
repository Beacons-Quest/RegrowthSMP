package org.lushplugins.regrowthsmp.module.glassitemframes;

import fr.skytasul.glowingentities.GlowingEntities;
import org.bukkit.scheduler.BukkitTask;
import org.lushplugins.lushlib.module.Module;
import org.lushplugins.lushlib.plugin.SpigotPlugin;
import org.lushplugins.regrowthsmp.module.glassitemframes.listener.ItemFrameListener;
import org.lushplugins.regrowthsmp.module.glassitemframes.viewer.InvisibleItemFrameViewer;

public class GlassItemFrames extends Module {
    private static GlassItemFrames instance;

    private final SpigotPlugin plugin;
    private GlowingEntities glowingEntities;
    private BukkitTask viewerTask;

    public GlassItemFrames(SpigotPlugin plugin) {
        super("glass_item_frames");
        this.plugin = plugin;

        if (instance == null) {
            instance = this;
        }
    }

    @Override
    public void onEnable() {
        glowingEntities = new GlowingEntities(plugin);
        viewerTask = new InvisibleItemFrameViewer().runTaskTimer(plugin, 20, 20);

        plugin.registerListener(new ItemFrameListener());
    }

    @Override
    protected void onDisable() {
        if (viewerTask != null) {
            viewerTask.cancel();
            viewerTask = null;
        }

        if (glowingEntities != null) {
            glowingEntities.disable();
            glowingEntities = null;
        }
    }

    public SpigotPlugin getPlugin() {
        return plugin;
    }

    public GlowingEntities getGlowingEntities() {
        return glowingEntities;
    }

    public static GlassItemFrames getInstance() {
        return instance;
    }
}
