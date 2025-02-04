package org.lushplugins.regrowthjukebox.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.lushplugins.regrowthjukebox.RegrowthJukebox;

public class ReloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("regrowthjukebox.reload")) {
            sender.sendMessage("You do not have permission for this command");
            return true;
        }

        RegrowthJukebox.getInstance().getConfigManager().reloadConfig();
        sender.sendMessage("Reloaded RegrowthJukebox");
        return true;
    }
}
