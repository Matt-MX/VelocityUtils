package com.mattmx.velocityutil.examples.commands;

import com.google.common.collect.ImmutableList;
import com.mattmx.velocityutil.api.commands.VelocityCommand;
import com.mattmx.velocityutil.api.general.VelocityChat;
import com.mattmx.velocityutil.examples.gui.ExampleGui;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

import java.util.List;

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
            }
        }
        source.sendMessage(VelocityChat.color("&#36a2fbV&#4494fbe&#5385fbl&#6177fco&#6f69fcc&#7d5afci&#8c4cfct&#9a3dfcy&#a82ffcU&#b621fdt&#c512fdi&#d304fdl &7» &fRunning v1.0.0"));
    }
}
