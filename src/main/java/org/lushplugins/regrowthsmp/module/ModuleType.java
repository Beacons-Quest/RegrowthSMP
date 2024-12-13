package org.lushplugins.regrowthsmp.module;

import org.lushplugins.lushlib.module.Module;
import org.lushplugins.lushlib.plugin.SpigotPlugin;
import org.lushplugins.regrowthsmp.RegrowthSMP;
import org.lushplugins.regrowthsmp.module.cosmetics.Cosmetics;
import org.lushplugins.regrowthsmp.module.crateanimation.CrateAnimation;
import org.lushplugins.regrowthsmp.module.glassitemframes.GlassItemFrames;
import org.lushplugins.regrowthsmp.module.extraluckpermscontexts.ExtraLuckPermsContexts;
import org.lushplugins.regrowthsmp.module.recipes.Recipes;

import java.util.function.Function;

@SuppressWarnings("Convert2MethodRef")
public enum ModuleType {
    COSMETICS((plugin) -> new Cosmetics(plugin)),
    CRATE_ANIMATION((plugin) -> new CrateAnimation(plugin)),
    GLASS_ITEM_FRAMES((plugin) -> new GlassItemFrames(plugin)),
    LUCK_PERMS_CONTEXTS((plugin) -> new ExtraLuckPermsContexts(plugin)),
    RECIPES((plugin) -> new Recipes(plugin));

    private final Function<SpigotPlugin, Module> moduleCallable;

    ModuleType(Function<SpigotPlugin, Module> moduleCallable) {
        this.moduleCallable = moduleCallable;
    }

    public Module init() {
        try {
            return moduleCallable.apply(RegrowthSMP.getInstance());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
