package io.github.galaxyvn.authmeui;

import io.github.galaxyvn.authmeui.command.AuthMeUICommands;
import io.github.galaxyvn.authmeui.listener.PlayerJoinListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Logger;

public final class AuthMeUI extends JavaPlugin {
    public static AuthMeUI plugin;
    Logger logger = Bukkit.getLogger();

    @Override
    public void onEnable() {
        plugin = this;

        logger.info("Loading AuthMeUI...");

        saveDefaultConfig();
        reloadConfig();

        logger.info("Registering event...");

        Bukkit.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);

        logger.info("Registering command...");

        getCommand("authmeui").setExecutor(new AuthMeUICommands());

        logger.info("Hooking...");

        if (!Bukkit.getPluginManager().isPluginEnabled("AuthMe") || !Bukkit.getPluginManager().isPluginEnabled("Floodgate")) {
            logger.warning("Missing plugin AuthMe or Floodgate! Disable plugin...");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        logger.info("Loading Successfully");
        logger.info("AuthMeUI by GalaxyVN");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
