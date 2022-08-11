package io.github.galaxyvn.authmeui.command;

import io.github.galaxyvn.authmeui.AuthMeUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AuthMeUICommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args[0].equalsIgnoreCase("reload")) {

            if (!sender.hasPermission("authmeui.admin"))
                return false;

            AuthMeUI.plugin.reloadConfig();
            return true;
        }

        return true;
    }
}
