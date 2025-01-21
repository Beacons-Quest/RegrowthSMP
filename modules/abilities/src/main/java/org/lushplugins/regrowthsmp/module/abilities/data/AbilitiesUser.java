package org.lushplugins.regrowthsmp.module.abilities.data;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.Nullable;
import org.lushplugins.regrowthsmp.common.data.UserData;
import org.lushplugins.regrowthsmp.module.abilities.Abilities;

import java.util.UUID;

public class AbilitiesUser extends UserData {
    private String currentAbility = null;

    public AbilitiesUser(UUID uuid, @Nullable JsonObject json) {
        super(uuid, json);
        if (json == null) {
            return;
        }

        this.currentAbility = json.get("currentAbility").getAsString();
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

    @Override
    public JsonObject toJsonObject() {
        JsonObject json = new JsonObject();

        json.addProperty("currentAbility", currentAbility);

        return json;
    }
}
