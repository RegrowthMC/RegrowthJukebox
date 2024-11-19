package org.lushplugins.regrowthjukebox.config;

import cz.koca2000.nbs4j.Song;
import org.lushplugins.nbsminecraft.NBSAPI;
import org.lushplugins.regrowthjukebox.RegrowthJukebox;

import java.io.File;
import java.util.Collection;
import java.util.Map;

public class SongManager {
    private Map<String, Song> songs;

    public void reloadSongs() {
        this.songs = NBSAPI.INSTANCE.readSongsInDirectory(new File(RegrowthJukebox.getInstance().getDataFolder(), "songs"));
    }

    public Collection<Song> getAllSongs() {
        return songs.values();
    }

    public Collection<Song> loadPlaylist(String playlistName) {
        return NBSAPI.INSTANCE.readSongsInDirectory(new File(RegrowthJukebox.getInstance().getDataFolder(), "playlists/" + playlistName)).values();
    }
}
