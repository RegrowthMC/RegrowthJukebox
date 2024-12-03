package org.lushplugins.regrowthjukebox;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.lushplugins.nbsminecraft.NBSAPI;
import org.lushplugins.regrowthjukebox.command.TestCommand;
import org.lushplugins.regrowthjukebox.config.SongManager;
import org.lushplugins.regrowthjukebox.hook.PlaceholderAPIHook;
import org.lushplugins.regrowthjukebox.jukebox.Jukebox;
import org.lushplugins.regrowthjukebox.jukebox.JukeboxManager;
import org.lushplugins.regrowthjukebox.listener.PlayerListener;

import java.util.List;

public class RegrowthJukebox extends JavaPlugin {
    private static RegrowthJukebox plugin;

    private SongManager songManager;
    private JukeboxManager jukeboxManager;

    @Override
    public void onLoad() {
        plugin = this;
    }

    @Override
    public void onEnable() {
        songManager = new SongManager();
        songManager.reloadSongs();

        jukeboxManager = new JukeboxManager();
        // Temporary hardcoded jukebox
        jukeboxManager.registerJukebox(new Jukebox(
            "qawEdR",
            new Location(Bukkit.getWorld("world"), -7, 69, -51),
            songManager.loadPlaylist("winter")));

        jukeboxManager.registerJukebox(new Jukebox(
            "AwrCbD",
            new Location(Bukkit.getWorld("world"), -19, 70, 14),
            List.of(
                NBSAPI.INSTANCE.readSongInputStream(getResource("letitbe/let-it-be.nbs")),
                NBSAPI.INSTANCE.readSongInputStream(getResource("letitbe/let-it-be-harp.nbs"))
            )));

        PluginManager pluginManager = getServer().getPluginManager();
        if (pluginManager.getPlugin("PlaceholderAPI") != null) {
            new PlaceholderAPIHook();
        }

        pluginManager.registerEvents(new PlayerListener(), this);

        getCommand("regrowthjukebox").setExecutor(new TestCommand());
    }

    @Override
    public void onDisable() {
        if (jukeboxManager != null) {
            jukeboxManager = null;
        }

        if (songManager != null) {
            songManager = null;
        }
    }

    public SongManager getSongManager() {
        return songManager;
    }

    public JukeboxManager getJukeboxManager() {
        return jukeboxManager;
    }

    public static RegrowthJukebox getInstance() {
        return plugin;
    }
}
