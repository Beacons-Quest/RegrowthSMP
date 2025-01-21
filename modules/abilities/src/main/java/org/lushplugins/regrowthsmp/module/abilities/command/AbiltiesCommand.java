package org.lushplugins.regrowthsmp.module.abilities.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.lushplugins.lushlib.command.Command;
import org.lushplugins.regrowthsmp.module.abilities.gui.AbilitiesMenu;

public class AbiltiesCommand extends Command {

    public AbiltiesCommand() {
        super("abilities");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args, @NotNull String[] fullArgs) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("This command cannot be ran by console");
            return true;
        }

        AbilitiesMenu gui = new AbilitiesMenu(player);
        gui.open();

        return true;
    }
}
