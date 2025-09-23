package org.lushplugins.regrowthjukebox.jukebox;

import net.raphimc.noteblocklib.model.Song;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.lushplugins.nbsminecraft.platform.bukkit.player.BukkitSongPlayer;
import org.lushplugins.nbsminecraft.player.SongPlayer;
import org.lushplugins.nbsminecraft.player.emitter.StaticSoundEmitter;
import org.lushplugins.nbsminecraft.utils.AudioListener;
import org.lushplugins.nbsminecraft.utils.SoundLocation;
import org.lushplugins.regrowthjukebox.RegrowthJukebox;

import java.util.Collection;
import java.util.UUID;

public class Jukebox {
    private final String id;
    private final SongPlayer songPlayer;

    public Jukebox(String id, Location location) {
        this(id, location, RegrowthJukebox.getInstance().getSongManager().getAllSongs());
    }

    public Jukebox(String id, Location location, Collection<Song> songs) {
        this.id = id;
        this.songPlayer = new BukkitSongPlayer.Builder()
            .soundEmitter(new StaticSoundEmitter(new SoundLocation(
                location.getWorld().getName(),
                location.getBlockX(),
                location.getBlockY(),
                location.getBlockZ())))
            .build();

        songs.forEach(songPlayer::queueSong);
        songPlayer.loopQueue(true);
        songPlayer.shuffleQueue();
        songPlayer.play();
    }

    public String getId() {
        return id;
    }

    public SongPlayer getSongPlayer() {
        return songPlayer;
    }

    public void queueSong(Song song) {
        songPlayer.queueSongPriority(song);
    }

    public void addListener(Player player) {
        songPlayer.addListener(new AudioListener(player.getEntityId(), player.getUniqueId()));
    }

    public void removeListener(UUID uuid) {
        songPlayer.removeListener(uuid);
    }
}
