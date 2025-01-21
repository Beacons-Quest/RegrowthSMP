package org.lushplugins.regrowthsmp.module;

import org.lushplugins.lushlib.manager.Manager;
import org.lushplugins.regrowthsmp.common.module.Module;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class ModuleManager extends Manager {
    private final HashMap<ModuleType, Module> modules = new HashMap<>();

    @Override
    public void onDisable() {
        for (Module module : modules.values()) {
            module.disable();
        }

        modules.clear();
    }

    public Set<ModuleType> getModuleTypes() {
        return modules.keySet();
    }

    public Collection<Module> getModules() {
        return modules.values();
    }

    public Module getModule(ModuleType type) {
        return modules.get(type);
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
