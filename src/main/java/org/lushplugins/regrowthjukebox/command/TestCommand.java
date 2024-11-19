package org.lushplugins.regrowthjukebox.command;

import cz.koca2000.nbs4j.Song;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.lushplugins.nbsminecraft.NBSAPI;
import org.lushplugins.nbsminecraft.platform.bukkit.player.BukkitSongPlayer;
import org.lushplugins.nbsminecraft.player.SongPlayer;
import org.lushplugins.nbsminecraft.player.emitter.StaticSoundEmitter;
import org.lushplugins.nbsminecraft.song.SongQueue;
import org.lushplugins.nbsminecraft.utils.AudioListener;
import org.lushplugins.nbsminecraft.utils.SoundLocation;
import org.lushplugins.regrowthjukebox.RegrowthJukebox;

import java.io.File;
import java.util.Map;

public class TestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Map<String, Song> songs = NBSAPI.INSTANCE.readSongsInDirectory(new File(RegrowthJukebox.getInstance().getDataFolder(), "songs"));

        SongQueue queue = new SongQueue();
        queue.queueSongs(songs.values());

        SongPlayer songPlayer = new BukkitSongPlayer.Builder()
            .soundEmitter(new StaticSoundEmitter(new SoundLocation("world", 92, 146, -135)))
            .setQueue(queue)
            .transposeNotes(true)
            .build();

        Bukkit.getOnlinePlayers().forEach(player -> songPlayer.addListener(new AudioListener(player.getEntityId(), player.getUniqueId())));
        songPlayer.loopQueue(true);
        songPlayer.shuffleQueue();
        songPlayer.play();

        return true;
    }
}
