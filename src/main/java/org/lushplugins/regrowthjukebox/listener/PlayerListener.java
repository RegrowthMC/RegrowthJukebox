package org.lushplugins.regrowthjukebox.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.lushplugins.regrowthjukebox.RegrowthJukebox;

import java.util.UUID;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        RegrowthJukebox.getInstance().getJukeboxManager().getAllJukeboxes().forEach(jukebox -> jukebox.addListener(player));
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        RegrowthJukebox.getInstance().getJukeboxManager().getAllJukeboxes().forEach(jukebox -> jukebox.removeListener(uuid));
    }
}
