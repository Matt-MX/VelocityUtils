package com.mattmx.velocityutil.api._scoreboard;

import com.velocitypowered.api.proxy.Player;

import java.util.HashMap;

public class ScoreboardManager {
    private HashMap<Player, Scoreboard> scoreboards = new HashMap<Player, Scoreboard>();

    public void set(Player p, Scoreboard sc) {
        remove(p);
        scoreboards.put(p, sc);
    }

    public void remove(Player p) {
        scoreboards.remove(p);
    }

    public Scoreboard getScoreboard(Player p) {
        return scoreboards.get(p);
    }
}
