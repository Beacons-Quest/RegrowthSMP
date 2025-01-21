package org.lushplugins.regrowthsmp.module.abilities.ability;

public abstract class Ability {
    private final String id;

    public Ability(String id) {
        this.id = id;
    }

    public final String getId() {
        return id;
    }
}
