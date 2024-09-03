package org.lushplugins.regrowthsmp.module;

import org.lushplugins.lushlib.module.Module;
import org.lushplugins.regrowthsmp.module.crateanimation.CrateAnimation;

import java.util.concurrent.Callable;

public enum ModuleType {
    CRATE_ANIMATION(CrateAnimation::new);

    private final Callable<Module> moduleCallable;

    ModuleType(Callable<Module> moduleCallable) {
        this.moduleCallable = moduleCallable;
    }

    public Module init() {
        try {
            return moduleCallable.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
