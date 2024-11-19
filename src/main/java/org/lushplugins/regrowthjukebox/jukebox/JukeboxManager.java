package org.lushplugins.regrowthjukebox.jukebox;

import java.util.Collection;
import java.util.HashMap;

public class JukeboxManager {
    private final HashMap<String, Jukebox> jukeboxes = new HashMap<>();

    public void registerJukebox(Jukebox jukebox) {
        jukeboxes.put(jukebox.getId(), jukebox);
    }

    public void unregisterJukebox(String id) {
        jukeboxes.remove(id);
    }

    public Jukebox getJukebox(String id) {
        return jukeboxes.get(id);
    }

    public Collection<Jukebox> getAllJukeboxes() {
        return jukeboxes.values();
    }
}
