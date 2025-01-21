package org.lushplugins.regrowthsmp.storage;

import com.google.gson.JsonObject;
import org.bukkit.configuration.ConfigurationSection;

import java.util.UUID;

/*
 * Table: regrowthsmp_users
 * Columns: uuid, username, data
 */
public interface Storage {

    default void enable(ConfigurationSection config) {}

    default void disable() {}

    JsonObject loadSMPUserJson(UUID uuid);

    void saveSMPUserJson(UUID uuid, JsonObject json);
}
