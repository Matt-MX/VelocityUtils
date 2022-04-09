package com.mattmx.velocityutil;

import com.google.inject.Inject;
import com.mattmx.velocityutil.api.commands.VelocityCommands;
import com.mattmx.velocityutil.api.general.VelocityPlugin;
import com.mattmx.velocityutil.api._scoreboard.ScoreboardManager;
import com.mattmx.velocityutil.api._scoreboard.packets.ScoreboardObjectivePacket;
import com.mattmx.velocityutil.api._scoreboard.packets.DisplayScoreboardPacket;
import com.mattmx.velocityutil.api.scoreboard.packets.ScoreboardDisplay;
import com.mattmx.velocityutil.api.scoreboard.packets.ScoreboardObjective;
import com.mattmx.velocityutil.api.scoreboard.packets.ScoreboardScore;
import com.mattmx.velocityutil.examples.commands.ExampleCommand;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.simplix.protocolize.api.PacketDirection;
import dev.simplix.protocolize.api.Protocol;
import dev.simplix.protocolize.api.Protocolize;
import org.slf4j.Logger;

@Plugin(
        id = "velocityutil",
        name = "VelocityUtil",
        version = "1.0",
        description = "Making Velocity plugins easier to all",
        url = "mattmx.com",
        authors = {"MattMX"}
)
public class VelocityUtil extends VelocityPlugin {
    static VelocityUtil instance;

    private ScoreboardManager scoreboardManager = new ScoreboardManager();

    @Inject
    public VelocityUtil(ProxyServer server, Logger logger) {
        super(server, logger, "velocityutil");
        instance = this;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        VelocityCommands.register(this, new ExampleCommand());
        Protocolize.protocolRegistration().registerPacket(ScoreboardObjective.MAPPINGS, Protocol.PLAY, PacketDirection.CLIENTBOUND, ScoreboardObjective.class);
        Protocolize.protocolRegistration().registerPacket(ScoreboardScore.MAPPINGS, Protocol.PLAY, PacketDirection.CLIENTBOUND, ScoreboardScore.class);
        Protocolize.protocolRegistration().registerPacket(ScoreboardDisplay.MAPPINGS, Protocol.PLAY, PacketDirection.CLIENTBOUND, ScoreboardDisplay.class);
    }

    public static ScoreboardManager getScoreboardManager() {
        return instance.scoreboardManager;
    }

    public static VelocityUtil get() {
        return instance;
    }
}
