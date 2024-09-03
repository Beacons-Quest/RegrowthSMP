package org.lushplugins.regrowthsmp.module;

import org.lushplugins.lushlib.manager.Manager;
import org.lushplugins.lushlib.module.Module;

import java.util.HashMap;

public class ModuleManager extends Manager {
    private final HashMap<ModuleType, Module> modules = new HashMap<>();

    @Override
    public void onDisable() {
        for (Module module : modules.values()) {
            module.disable();
        }

        modules.clear();
    }

    public void enableModule(ModuleType moduleType) {
        Module module = modules.get(moduleType);
        if (module == null) {
            module = moduleType.init();
            modules.put(moduleType, module);
        }

        module.reload();
    }

    public void disableModule(ModuleType moduleType) {
        Module module = modules.get(moduleType);
        if (module != null) {
            module.disable();
        }
    }
}
