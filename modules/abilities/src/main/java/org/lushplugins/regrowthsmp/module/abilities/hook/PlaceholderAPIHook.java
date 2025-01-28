package org.lushplugins.regrowthsmp.module.abilities.hook;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.lushplugins.lushlib.hook.Hook;
import org.lushplugins.lushlib.utils.StringUtils;
import org.lushplugins.regrowthsmp.module.abilities.Abilities;
import org.lushplugins.regrowthsmp.module.abilities.data.AbilitiesUser;

public class PlaceholderAPIHook extends Hook {
    private PlaceholderExpansion expansion;

    public PlaceholderAPIHook() {
        super("PlaceholderAPI");
    }

    @Override
    public void onEnable() {
        expansion = new PlaceholderExpansion();
        expansion.register();
    }

    @Override
    protected void onDisable() {
        if (expansion != null) {
            expansion.unregister();
            expansion = null;
        }
    }

    public static class PlaceholderExpansion extends me.clip.placeholderapi.expansion.PlaceholderExpansion {

        public String onPlaceholderRequest(Player player, @NotNull String params) {
            if (!params.startsWith("abilities")) {
                return null;
            }

            params = params.replace("abilities_", "");

            if (params.equals("ability")) {
                if (player == null) {
                    return null;
                } else {
                    AbilitiesUser user = Abilities.getInstance().getCachedUserData(player.getUniqueId());
                    if (user != null) {
                        String ability = user.getCurrentAbility();
                        if (ability == null) {
                            ability = "None";
                        } else {
                            ability = StringUtils.makeFriendly(ability.replace("_", " "));
                        }

                        return ability;
                    } else {
                        return "";
                    }
                }
            }

            return null;
        }

        public boolean persist() {
            return true;
        }

        public boolean canRegister() {
            return true;
        }

        @NotNull
        public String getIdentifier() {
            return "regrowthsmp";
        }

        @NotNull
        public String getAuthor() {
            return Abilities.getInstance().getPlugin().getDescription().getAuthors().toString();
        }

        @NotNull
        public String getVersion() {
            return Abilities.getInstance().getPlugin().getDescription().getVersion();
        }
    }
}
