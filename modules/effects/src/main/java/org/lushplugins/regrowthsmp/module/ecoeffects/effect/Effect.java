package org.lushplugins.regrowthsmp.module.ecoeffects.effect;

public abstract class Effect {
    private final String id;

    public Effect(String id) {
        this.id = id;
    }

    public final String getId() {
        return id;
    }
}
