package org.lushplugins.regrowthsmp.module.extraluckpermscontexts;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.context.ContextManager;
import org.lushplugins.regrowthsmp.common.module.Module;
import org.lushplugins.regrowthsmp.common.plugin.RegrowthPlugin;
import org.lushplugins.regrowthsmp.module.extraluckpermscontexts.calculator.BedrockPlayerCalculator;

public class ExtraLuckPermsContexts extends Module {

    public ExtraLuckPermsContexts(RegrowthPlugin plugin) {
        super("luck_perms_contexts", plugin);

        LuckPerms luckPerms = plugin.getServer().getServicesManager().load(LuckPerms.class);
        if (luckPerms == null) {
            throw new IllegalStateException("LuckPerms is required for this module to run");
        }

        ContextManager contextManager = luckPerms.getContextManager();
        plugin.addHook("floodgate", () -> contextManager.registerCalculator(new BedrockPlayerCalculator()));
    }
}
