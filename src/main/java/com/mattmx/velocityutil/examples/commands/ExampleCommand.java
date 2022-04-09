package com.mattmx.velocityutil.examples.commands;

import com.mattmx.velocityutil.api._scoreboard.packets.ScoreboardObjectivePacket;
import com.mattmx.velocityutil.api.commands.VelocityCommand;
import com.mattmx.velocityutil.api.general.VelocityChat;
import com.mattmx.velocityutil.api._scoreboard.Scoreboard;
import com.mattmx.velocityutil.api.scoreboard.packets.ScoreboardDisplay;
import com.mattmx.velocityutil.api.scoreboard.packets.ScoreboardObjective;
import com.mattmx.velocityutil.api.scoreboard.packets.ScoreboardScore;
import com.mattmx.velocityutil.examples.gui.ExampleGui;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import dev.simplix.protocolize.api.Protocolize;
import dev.simplix.protocolize.api.player.ProtocolizePlayer;

public class ExampleCommand extends VelocityCommand {

    public ExampleCommand() {
        setName("velocityutils");
    }

    @Override
    public void execute(CommandSource source, String alias, String[] args) {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("gui")) {
                if (source instanceof Player) {
                    if (source.hasPermission("velocityutil.command.gui")) {
                        Player p = (Player) source;
                        ExampleGui gui = new ExampleGui();
                        gui.define(p);
                        gui.open();
                    }
                } else {
                    source.sendMessage(VelocityChat.color("&#36a2fbV&#4494fbe&#5385fbl&#6177fco&#6f69fcc&#7d5afci&#8c4cfct&#9a3dfcy&#a82ffcU&#b621fdt&#c512fdi&#d304fdl &7» &fPlayer only command!"));
                }
            } else if (args[0].equalsIgnoreCase("scoreboard")) {
                if (source.hasPermission("velocityutil.command.scoreboard")) {
                    if (source instanceof Player) {
                        Player p = (Player) source;
                        ProtocolizePlayer protocolizePlayer = Protocolize.playerProvider().player(p.getUniqueId());
                        ScoreboardObjective objective = new ScoreboardObjective("ScoreboardName", "Title", ScoreboardObjective.HealthDisplay.HEARTS, (byte) 0);
                        protocolizePlayer.sendPacket(objective);
                        ScoreboardScore score = new ScoreboardScore("Line 1", (byte) 0, "ScoreboardName", 0);
                        protocolizePlayer.sendPacket(score);
                        ScoreboardDisplay display = new ScoreboardDisplay((byte) 1, "ScoreboardName");
                        protocolizePlayer.sendPacket(display);
                    }
                }
            }
        }
        source.sendMessage(VelocityChat.color("&#36a2fbV&#4494fbe&#5385fbl&#6177fco&#6f69fcc&#7d5afci&#8c4cfct&#9a3dfcy&#a82ffcU&#b621fdt&#c512fdi&#d304fdl &7» &fRunning v1.0.0"));
    }
}
