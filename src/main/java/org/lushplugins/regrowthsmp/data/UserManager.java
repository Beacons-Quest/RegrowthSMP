package org.lushplugins.regrowthsmp.data;

import org.jetbrains.annotations.Nullable;
import org.lushplugins.regrowthsmp.RegrowthSMP;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class UserManager {
    private final Map<UUID, SMPUser> users = new HashMap<>();


    public @Nullable SMPUser getCachedUser(UUID uuid) {
        return users.get(uuid);
    }

    public CompletableFuture<SMPUser> getUser(UUID uuid) {
        return getUser(uuid, true);
    }

    public CompletableFuture<SMPUser> getUser(UUID uuid, boolean cache) {
        if (users.containsKey(uuid)) {
            return CompletableFuture.completedFuture(users.get(uuid));
        }

        return RegrowthSMP.getInstance().getStorageManager().loadSMPUserJson(uuid).thenApply(json -> {
            SMPUser user = new SMPUser(uuid, json);

            if (cache) {
                users.put(uuid, user);
            }

            return user;
        });
    }

    public void invalidateUser(UUID uuid) {
        users.remove(uuid);
    }

    @SuppressWarnings("UnusedReturnValue")
    public CompletableFuture<Void> saveUser(SMPUser user) {
        return RegrowthSMP.getInstance().getStorageManager().saveSMPUserJson(user.getUUID(), user.toJsonObject());
    }
}
