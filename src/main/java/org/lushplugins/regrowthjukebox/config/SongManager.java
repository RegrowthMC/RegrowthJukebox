package org.lushplugins.regrowthjukebox.config;

import net.raphimc.noteblocklib.model.Song;
import org.lushplugins.nbsminecraft.NBSAPI;
import org.lushplugins.regrowthjukebox.RegrowthJukebox;

import java.io.File;
import java.util.Collection;
import java.util.Map;

public class SongManager {
    private Map<String, Song> songs;

    public void reloadSongs() {
        File songsDir = new File(RegrowthJukebox.getInstance().getDataFolder(), "songs");
        if (!songsDir.exists()) {
            return;
        }

        this.songs = NBSAPI.INSTANCE.readSongsInDirectory(songsDir);
    }

    public Collection<Song> getAllSongs() {
        return songs.values();
    }

    public Collection<Song> loadPlaylist(String playlistName) {
        return NBSAPI.INSTANCE.readSongsInDirectory(new File(RegrowthJukebox.getInstance().getDataFolder(), "playlists/" + playlistName)).values();
    }
}
