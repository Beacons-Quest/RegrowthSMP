package org.lushplugins.regrowthsmp.module.effects.data;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.UUID;

public class UserManager {
    private final HashMap<UUID, EffectsUser> users = new HashMap<>();

    public @Nullable EffectsUser getUser(UUID uuid) {
        return users.get(uuid);
    }
}
