package org.lushplugins.regrowthjukebox.hook;

import net.raphimc.noteblocklib.model.Song;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.lushplugins.regrowthjukebox.RegrowthJukebox;
import org.lushplugins.regrowthjukebox.jukebox.Jukebox;

import java.util.Arrays;

public class PlaceholderAPIHook {

    public PlaceholderAPIHook() {
        PlaceholderExpansion expansion = new PlaceholderExpansion();
        expansion.register();
    }

    public static class PlaceholderExpansion extends me.clip.placeholderapi.expansion.PlaceholderExpansion {

        public String onPlaceholderRequest(Player player, @NotNull String params) {
            String[] args = params.split("_");
            if (args.length < 2) {
                return null;
            }

            Jukebox jukebox = RegrowthJukebox.getInstance().getJukeboxManager().getJukebox(args[0]);
            if (jukebox != null) {
                String query = String.join("_", Arrays.copyOfRange(args, 1, args.length));
                switch (query.toLowerCase()) {
                    case "song_title" -> {
                        Song song = jukebox.getSongPlayer().getCurrentSong();
                        if (song == null) {
                            return "";
                        } else {
                            return song.getMetadata().getTitle();
                        }
                    }
                    case "song_artist" -> {
                        Song song = jukebox.getSongPlayer().getCurrentSong();
                        if (song == null) {
                            return "";
                        } else {
                            String originalAuthor = song.getMetadata().getOriginalAuthor();
                            return !originalAuthor.isEmpty() ? originalAuthor : "Unknown";
                        }
                    }
                    case "song_nbs_artist" -> {
                        Song song = jukebox.getSongPlayer().getCurrentSong();
                        if (song == null) {
                            return "";
                        } else {
                            String author = song.getMetadata().getAuthor();
                            return !author.isEmpty() ? author : "Unknown";
                        }
                    }
                }
            }

            return null;
        }

        public boolean persist() {
            return true;
        }

        public boolean canRegister() {
            return true;
        }

        @NotNull
        public String getIdentifier() {
            return "jukebox";
        }

        @NotNull
        public String getAuthor() {
            return RegrowthJukebox.getInstance().getDescription().getAuthors().toString();
        }

        @NotNull
        public String getVersion() {
            return RegrowthJukebox.getInstance().getDescription().getVersion();
        }
    }
}
