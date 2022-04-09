package com.mattmx.velocityutil.api.commands;

import com.google.common.collect.ImmutableList;
import com.mattmx.velocityutil.api.general.VelocityPlugin;
import com.velocitypowered.api.command.CommandSource;

import java.util.*;
import java.util.stream.Collectors;

public class VelocityCommands {
    private static List<VelocityCommand> commands = new ArrayList<>();

    public static void register(VelocityPlugin plugin, VelocityCommand command) {
        plugin.getServer().getCommandManager().register(command.getName(), new CatchCommand(), command.getAliases().toArray(new String[0]));
        commands.add(command);
    }

    public static void execute(CommandSource source, String alias, String[] args) {
        for (VelocityCommand cmd : commands) {
            if (cmd.isCommand(alias)) {
                cmd.execute(source, alias, args);
            }
        }
    }

    public static List<String> suggest(CommandSource source, String alias, String[] args) {
        for (VelocityCommand cmd : commands) {
            if (cmd.isCommand(alias)) {
                return cmd.suggest(source, alias, args);
            }
        }
        return ImmutableList.of();
    }
}
