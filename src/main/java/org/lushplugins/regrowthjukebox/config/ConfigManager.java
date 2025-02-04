package org.lushplugins.regrowthjukebox.config;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.lushplugins.nbsminecraft.song.SongQueue;
import org.lushplugins.regrowthjukebox.RegrowthJukebox;
import org.lushplugins.regrowthjukebox.jukebox.Jukebox;
import org.lushplugins.regrowthjukebox.jukebox.JukeboxManager;

import java.util.Map;

public class ConfigManager {

    public ConfigManager() {
        RegrowthJukebox.getInstance().saveDefaultConfig();
        reloadConfig();
    }

    public void reloadConfig() {
        RegrowthJukebox plugin = RegrowthJukebox.getInstance();
        plugin.reloadConfig();
        FileConfiguration config = plugin.getConfig();

        for (Map<?, ?> jukeboxMap : config.getMapList("jukeboxes")) {
            String id = (String) jukeboxMap.get("id");
            Location location = new Location(
                Bukkit.getWorld((String) jukeboxMap.get("world")),
                (int) jukeboxMap.get("x"),
                (int) jukeboxMap.get("y"),
                (int) jukeboxMap.get("z")
            );
            String playlist = (String) jukeboxMap.get("playlist");

            JukeboxManager jukeboxManager = plugin.getJukeboxManager();
            Jukebox jukebox = jukeboxManager.getJukebox(id);
            if (jukebox != null) {
                SongQueue songQueue = jukebox.getSongPlayer().getQueue();
                songQueue.clearQueue();
                songQueue.queueSongs(plugin.getSongManager().loadPlaylist(playlist));
            } else {
                jukeboxManager.registerJukebox(new Jukebox(
                    id,
                    location,
                    plugin.getSongManager().loadPlaylist(playlist)
                ));
            }
        }
    }
}
