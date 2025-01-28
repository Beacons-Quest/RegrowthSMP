package org.lushplugins.regrowthsmp.module.abilities.api.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lushplugins.regrowthsmp.module.abilities.data.AbilitiesUser;

public class AbilitiesUserChangeAbilityEvent extends Event {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final AbilitiesUser user;
    private final String newAbility;
    private final String oldAbility;

    public AbilitiesUserChangeAbilityEvent(AbilitiesUser user, String newAbility, String oldAbility) {
        this.user = user;
        this.newAbility = newAbility;
        this.oldAbility = oldAbility;
    }

    public AbilitiesUser getUser() {
        return user;
    }

    public @Nullable String getNewAbility() {
        return newAbility;
    }

    public @Nullable String getOldAbility() {
        return oldAbility;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
