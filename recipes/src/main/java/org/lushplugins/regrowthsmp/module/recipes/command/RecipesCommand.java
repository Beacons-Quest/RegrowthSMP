package org.lushplugins.regrowthsmp.module.recipes.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.lushplugins.lushlib.command.Command;
import org.lushplugins.regrowthsmp.module.recipes.gui.RecipesGui;

public class RecipesCommand extends Command {

    public RecipesCommand() {
        super("recipes");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String s, @NotNull String[] strings, @NotNull String[] strings1) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("This command cannot be ran by console");
            return true;
        }

        RecipesGui gui = new RecipesGui(player);
        gui.open();
        return true;
    }
}
