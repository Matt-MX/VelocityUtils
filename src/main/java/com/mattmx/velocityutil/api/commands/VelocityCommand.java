package com.mattmx.velocityutil.api.commands;

import com.google.common.collect.ImmutableList;
import com.velocitypowered.api.command.CommandSource;

import java.util.*;
import java.util.stream.Collectors;

public abstract class VelocityCommand {
    private String name;
    //private List<VelocityCommand> sub = new ArrayList<>();
    private List<String> aliases = new ArrayList<>();
    private String permission;

    public abstract void execute(CommandSource source, String alias, String[] args);

    public List<String> suggest(CommandSource source, String alias, String[] args) {
        return ImmutableList.of();
    }

//    public VelocityCommand get(String[] arg) {
//        if (arg.length == 0) return null;
//        if (this.isCommand(arg[0])) return this;
//        for (VelocityCommand cmd : sub) {
//            if (cmd.isCommand(arg[0])) {
//                return cmd;
//            } else {
//                return cmd.get(arg);
//            }
//        }
//        return null;
//    }

    public boolean isCommand(String arg) {
        return aliases.stream().map(String::toLowerCase).collect(Collectors.toList()).contains(arg.toLowerCase()) || name.equalsIgnoreCase(arg);
    }

    public VelocityCommand setName(String name) {
        this.name = name;
        return this;
    }
//
//    public VelocityCommand setSub(List<VelocityCommand> sub) {
//        this.sub = sub;
//        return this;
//    }
//
//    public void addSub(VelocityCommand sub) {
//        this.sub.add(sub);
//    }

    public VelocityCommand setAliases(List<String> aliases) {
        this.aliases = aliases;
        return this;
    }

    public VelocityCommand setPermission(String permission) {
        this.permission = permission;
        return this;
    }

    public String getName() {
        return name;
    }

//    public List<VelocityCommand> getSub() {
//        return sub;
//    }

    public List<String> getAliases() {
        return aliases;
    }

    public String getPermission() {
        return permission;
    }
}
