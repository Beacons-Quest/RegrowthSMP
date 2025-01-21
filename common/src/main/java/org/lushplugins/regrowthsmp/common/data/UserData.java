package org.lushplugins.regrowthsmp.common.data;

import com.google.gson.JsonObject;

import java.util.UUID;

public abstract class UserData {
    private final UUID uuid;

    public UserData(UUID uuid) {
        this.uuid = uuid;
    }

    public UserData(UUID uuid, JsonObject jsonObject) {
        this.uuid = uuid;
    }

    public UUID getUUID() {
        return uuid;
    }

    public abstract JsonObject toJsonObject();
}
