package org.lushplugins.regrowthsmp.common.plugin;

import org.lushplugins.lushlib.plugin.SpigotPlugin;
import org.lushplugins.regrowthsmp.common.data.SMPUser;

import java.util.UUID;

public abstract class RegrowthPlugin extends SpigotPlugin {

    public abstract SMPUser getCachedSMPUser(UUID uuid);

    public abstract void saveCachedSMPUser(UUID uuid);
}
