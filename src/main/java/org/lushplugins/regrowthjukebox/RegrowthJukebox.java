package org.lushplugins.regrowthjukebox;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.lushplugins.regrowthjukebox.command.ReloadCommand;
import org.lushplugins.regrowthjukebox.command.TestCommand;
import org.lushplugins.regrowthjukebox.config.ConfigManager;
import org.lushplugins.regrowthjukebox.config.SongManager;
import org.lushplugins.regrowthjukebox.hook.PlaceholderAPIHook;
import org.lushplugins.regrowthjukebox.jukebox.JukeboxManager;
import org.lushplugins.regrowthjukebox.listener.PlayerListener;

public class RegrowthJukebox extends JavaPlugin {
    private static RegrowthJukebox plugin;

    private SongManager songManager;
    private JukeboxManager jukeboxManager;
    private ConfigManager configManager;

    @Override
    public void onLoad() {
        plugin = this;
    }

    @Override
    public void onEnable() {
        songManager = new SongManager();
        songManager.reloadSongs();

        jukeboxManager = new JukeboxManager();
        configManager = new ConfigManager();

        PluginManager pluginManager = getServer().getPluginManager();
        if (pluginManager.getPlugin("PlaceholderAPI") != null) {
            new PlaceholderAPIHook();
        }

        pluginManager.registerEvents(new PlayerListener(), this);

        getCommand("regrowthjukebox").setExecutor(new TestCommand());
        getCommand("regrowthjukeboxreload").setExecutor(new ReloadCommand());
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

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public static RegrowthJukebox getInstance() {
        return plugin;
    }
}
