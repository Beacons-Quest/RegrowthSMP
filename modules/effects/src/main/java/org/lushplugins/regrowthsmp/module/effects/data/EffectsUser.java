package org.lushplugins.regrowthsmp.module.effects.data;

import com.google.gson.JsonObject;

public class EffectsUser {
    private final String currentEffect;

    public EffectsUser(JsonObject json) {
        this.currentEffect = json.get("currentEffect").getAsString();
    }

    public EffectsUser(String currentEffect) {
        this.currentEffect = currentEffect;
    }

    public String getCurrentEffect() {
        return currentEffect;
    }
}
