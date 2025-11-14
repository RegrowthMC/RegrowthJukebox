package org.lushplugins.regrowthjukebox.config;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.lushplugins.nbsminecraft.player.emitter.GlobalSoundEmitter;
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

            Map<?, ?> locationMap = (Map<?, ?>) jukeboxMap.get("location");
            Location location = new Location(
                Bukkit.getWorld((String) locationMap.get("world")),
                (int) locationMap.get("x"),
                (int) locationMap.get("y"),
                (int) locationMap.get("z")
            );
            String playlist = (String) jukeboxMap.get("playlist");

            JukeboxManager jukeboxManager = plugin.getJukeboxManager();
            Jukebox jukebox = jukeboxManager.getJukebox(id);
            if (jukebox != null) {
                SongQueue songQueue = jukebox.getSongPlayer().getQueue();
                songQueue.clearQueue();
                songQueue.queueSongs(plugin.getSongManager().loadPlaylist(playlist));
            } else {
                if (jukeboxMap.containsKey("global") && (boolean) jukeboxMap.get("global")) {
                    jukeboxManager.registerJukebox(new Jukebox(
                        id,
                        plugin.getSongManager().loadPlaylist(playlist),
                        new GlobalSoundEmitter()
                    ));
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
}
