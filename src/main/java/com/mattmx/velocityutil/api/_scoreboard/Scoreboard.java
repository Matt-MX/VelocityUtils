package com.mattmx.velocityutil.api._scoreboard;

import com.mattmx.velocityutil.api._scoreboard.packets.DisplayScoreboardPacket;
import com.mattmx.velocityutil.api._scoreboard.packets.ScoreboardObjectivePacket;
import com.mattmx.velocityutil.api._scoreboard.packets.enums.OptionalVarInt;
import com.mattmx.velocityutil.api._scoreboard.packets.enums.ScoreboardMode;
import com.mattmx.velocityutil.api._scoreboard.packets.enums.ScoreboardPosition;
import com.velocitypowered.api.proxy.Player;
import dev.simplix.protocolize.api.Protocolize;
import dev.simplix.protocolize.api.player.ProtocolizePlayer;

import java.util.*;

public class Scoreboard {
    private String name;
    private List<String> lines = new ArrayList<>();
    private List<Player> players = new ArrayList<>();

    public boolean contains(Player p) {
        return players.contains(p);
    }

    public void add(Player p) {
        players.add(p);
    }

    public void remove(Player p) {
        players.remove(p);
        ProtocolizePlayer protocolizePlayer = Protocolize.playerProvider().player(p.getUniqueId());
        protocolizePlayer.sendPacket(new ScoreboardObjectivePacket(name, ScoreboardMode.REMOVE));
    }

    public void sendAll() {
        new ArrayList<>(players).forEach(this::send);
    }

    public void send(Player p) {
        ProtocolizePlayer protocolizePlayer = Protocolize.playerProvider().player(p.getUniqueId());
        protocolizePlayer.sendPacket(new ScoreboardObjectivePacket(name, ScoreboardMode.CREATE, String.join("\n", lines), OptionalVarInt.INTEGER));
        protocolizePlayer.sendPacket(new DisplayScoreboardPacket(ScoreboardPosition.SIDEBAR, this.name));
    }

    public void update(Player p) {
        ProtocolizePlayer protocolizePlayer = Protocolize.playerProvider().player(p.getUniqueId());
        protocolizePlayer.sendPacket(new ScoreboardObjectivePacket(name, ScoreboardMode.UPDATE, String.join("\n", lines), OptionalVarInt.INTEGER));
    }

    public String getName() {
        return name;
    }

    public Scoreboard setName(String name) {
        this.name = name;
        return this;
    }

    public List<String> getLines() {
        return lines;
    }

    public Scoreboard setLines(List<String> lines) {
        this.lines = lines;
        return this;
    }

    public Scoreboard addLine(String line) {
        this.lines.add(line);
        return this;
    }

    public Scoreboard clearLines() {
        this.lines.clear();
        return this;
    }

    public Scoreboard setLine(int i, String line) {
        this.lines.set(i, line);
        return this;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Scoreboard setPlayers(List<Player> players) {
        this.players = players;
        return this;
    }
}
