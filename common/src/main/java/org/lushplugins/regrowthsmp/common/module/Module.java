package org.lushplugins.regrowthsmp.common.module;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.Nullable;
import org.lushplugins.regrowthsmp.common.data.SMPUser;
import org.lushplugins.regrowthsmp.common.data.UserData;
import org.lushplugins.regrowthsmp.common.plugin.RegrowthPlugin;

import java.util.UUID;

public abstract class Module extends org.lushplugins.lushlib.module.Module {
    private final RegrowthPlugin plugin;

    public Module(String id, RegrowthPlugin plugin) {
        super(id);
        this.plugin = plugin;
    }

    public RegrowthPlugin getPlugin() {
        return plugin;
    }

    public boolean storesUserData() {
        return false;
    }

    /**
     * @param jsonObject user data json or null to create default
     * @return user data
     */
    public UserData createUserData(UUID uuid, JsonObject jsonObject) {
        return null;
    }

    public @Nullable UserData getCachedUserData(UUID uuid) {
        SMPUser smpUser = plugin.getCachedSMPUser(uuid);
        if (smpUser == null) {
            return null;
        }


        return smpUser.getUserData(id);
    }
}
