package org.lushplugins.regrowthsmp.module.abilities.data;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.Nullable;
import org.lushplugins.regrowthsmp.common.data.UserData;
import org.lushplugins.regrowthsmp.module.abilities.Abilities;

import java.time.Instant;
import java.util.UUID;

public class AbilitiesUser extends UserData {
    private String currentAbility = null;
    private long abilityChangeCooldownUntil = -1; // -1 means not on cooldown

    public AbilitiesUser(UUID uuid, @Nullable JsonObject json) {
        super(uuid, json);
        if (json == null) {
            return;
        }

        this.currentAbility = json.get("currentAbility").getAsString();
        this.abilityChangeCooldownUntil = json.has("abilityChangeCooldown") ? json.get("abilityChangeCooldown").getAsLong() : -1;
    }

    public AbilitiesUser(UUID uuid, String currentEffect) {
        super(uuid);
        this.currentAbility = currentEffect;
    }

    public String getCurrentAbility() {
        return currentAbility;
    }

    public void setCurrentAbility(String currentAbility) {
        this.currentAbility = currentAbility;
        Abilities.getInstance().getPlugin().saveCachedSMPUser(this.getUUID());
    }

    public boolean isOnAbilityChangeCooldown() {
        return abilityChangeCooldownUntil > Instant.now().getEpochSecond();
    }

    public long remainingAbilityChangeCooldown() {
        if (isOnAbilityChangeCooldown()) {
            return abilityChangeCooldownUntil - Instant.now().getEpochSecond();
        } else {
            return 0;
        }
    }

    public void startAbilityChangeCooldown() {
        abilityChangeCooldownUntil = Instant.now().getEpochSecond() + 300; // 5 minutes
    }

    @Override
    public JsonObject toJsonObject() {
        JsonObject json = new JsonObject();

        json.addProperty("currentAbility", currentAbility);
        json.addProperty("abilityChangeCooldownUntil", abilityChangeCooldownUntil);

        return json;
    }
}
