package io.github.galaxyvn.authmeui.listener;

import fr.xephi.authme.api.v3.AuthMeApi;
import io.github.galaxyvn.authmeui.AuthMeUI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.geysermc.cumulus.form.CustomForm;
import org.geysermc.cumulus.response.CustomFormResponse;
import org.geysermc.floodgate.api.FloodgateApi;
import org.geysermc.floodgate.api.player.FloodgatePlayer;

public class PlayerJoinListener implements Listener {

    FileConfiguration config = AuthMeUI.plugin.getConfig();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!FloodgateApi.getInstance().isFloodgatePlayer(event.getPlayer().getUniqueId())) return;

        Player player = event.getPlayer();
        FloodgatePlayer fPlayer = FloodgateApi.getInstance().getPlayer(player.getUniqueId());

        if (!AuthMeApi.getInstance().isAuthenticated(player)) {
            CustomForm.Builder form = CustomForm.builder()
                    .title(config.getString("title"))
                    .input(config.getString("input_title"), config.getString("input_placeholder"))
                    .closedOrInvalidResultHandler(() -> player.kickPlayer(config.getString("close")))
                    .validResultHandler(response -> {
                        String password = response.asInput();

                        System.out.println("Response Password:" + password);

                        if (AuthMeApi.getInstance().isRegistered(player.getName())) {
                            if (AuthMeApi.getInstance().checkPassword(player.getName(), password)) {
                                AuthMeApi.getInstance().forceLogin(player);
                            } else
                                player.kickPlayer(config.getString("wrong_password"));
                        } else
                            AuthMeApi.getInstance().forceRegister(player, password, true);
                    });

            // Send Form
            fPlayer.sendForm(form.build());
        }
    }

}
