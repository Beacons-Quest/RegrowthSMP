package org.lushplugins.regrowthsmp.module;

import org.lushplugins.lushlib.module.Module;
import org.lushplugins.lushlib.plugin.SpigotPlugin;
import org.lushplugins.regrowthsmp.RegrowthSMP;
import org.lushplugins.regrowthsmp.module.cosmetics.Cosmetics;
import org.lushplugins.regrowthsmp.module.crateanimation.CrateAnimation;
import org.lushplugins.regrowthsmp.module.recipes.Recipes;

import java.util.function.Function;

public enum ModuleType {
    COSMETICS(Cosmetics::new),
    CRATE_ANIMATION(CrateAnimation::new),
    RECIPES(Recipes::new);

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
