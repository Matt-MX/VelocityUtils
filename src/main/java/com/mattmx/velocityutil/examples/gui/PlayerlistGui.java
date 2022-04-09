package com.mattmx.velocityutil.examples.gui;

import com.mattmx.velocityutil.VelocityUtil;
import com.mattmx.velocityutil.api.commands.VelocityCommand;
import com.mattmx.velocityutil.api.general.VelocityChat;
import com.mattmx.velocityutil.api.gui.InventoryBuilder;
import com.mattmx.velocityutil.api.item.ItemBuilder;
import com.velocitypowered.api.proxy.ConnectionRequestBuilder;
import com.velocitypowered.api.proxy.Player;
import dev.simplix.protocolize.api.inventory.InventoryClick;
import dev.simplix.protocolize.data.ItemType;
import dev.simplix.protocolize.data.inventory.InventoryType;
import net.kyori.adventure.text.Component;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class PlayerlistGui extends InventoryBuilder {
    int page = 1;
    List<Player> players;
    ExampleGui parent;

    public void update() {
        clear();
        define(getPlayer());
        //updateItems();
        open();
    }

    public void define(Player p, ExampleGui parent) {
        super.define(p);
        this.parent = parent;
        this.setTitle(VelocityChat.color("&#36a2fbV&#4494fbe&#5385fbl&#6177fco&#6f69fcc&#7d5afci&#8c4cfct&#9a3dfcy&#a82ffcU&#b621fdt&#c512fdi&#d304fdl &7» &fPlayers"));
        this.type(InventoryType.GENERIC_9X6);
        for (int i = 45; i < 54; i++) {
            setItem(i, new ItemBuilder(ItemType.WHITE_STAINED_GLASS_PANE).name(Component.text("")).build());
        }
        if (canNext()) {
            setItem(53, new ItemBuilder(ItemType.ARROW).name(VelocityChat.color("&9&lNext Page")).build());
        }
        if (canPrev()) {
            setItem(45, new ItemBuilder(ItemType.ARROW).name(VelocityChat.color("&9&lPrevious Page")).build());
        }
        setItem(49, new ItemBuilder(ItemType.BARRIER).name(VelocityChat.color("&c&lReturn to Menu")).build());
        players = new ArrayList<>(VelocityUtil.get().getServer().getAllPlayers());
        for (int i = (page - 1) * 45; i < Math.min((page * 45), players.size()); i++) {
            Player player = players.get(i);
            setItem(i - ((page - 1) * 45),
                    new ItemBuilder(ItemType.PLAYER_HEAD)
                    .name(VelocityChat.color("&6%username%", player))
                    .lore(Component.text(""))
                    .lore(VelocityChat.color("&#36a2fbS&#5582fbe&#7563fcr&#9443fcv&#b424fde&#d304fdr &7» &f%server%", player))
                    .lore(VelocityChat.color("&#36a2fbC&#5582fbl&#7563fci&#9443fce&#b424fdn&#d304fdt &7» &f%client%", player))
                    .lore(VelocityChat.color("&#36a2fbL&#5088fba&#6a6dfct&#8553fce&#9f39fcn&#b91efdc&#d304fdy &7» &f%ping%", player))
                    .lore(Component.text(""))
                    .lore(VelocityChat.color("&bLeft click &7to move to %player%'s server (&f%server%&7)", player))
                    .lore(VelocityChat.color("&bRight click &7to move %player% to your server", player))
                    .lore(VelocityChat.color("&bDrop &7to kick this player")).build());
        }
    }

    @Override
    public void onClick(InventoryClick e) {
        if (e.slot() == 45 && e.clickedItem().itemType() == ItemType.ARROW) {
            page--;
            update();
        } else if (e.slot() == 53 && e.clickedItem().itemType() == ItemType.ARROW) {
            page++;
            update();
        } else if (e.slot() == 49) {
            parent.open();
        } else if (e.clickedItem() != null) {
            if (e.clickedItem().itemType() == ItemType.PLAYER_HEAD) {
                int index = e.slot() + ((page - 1) * 44);
                Player player = players.get(index);
                if (player != null) {
                    VelocityUtil.get().getServer().getScheduler().buildTask(VelocityUtil.get(), () -> {
                        switch (e.clickType()) {
                            case LEFT_CLICK -> {
                                player.getCurrentServer().ifPresent(s -> {
                                    CompletableFuture<ConnectionRequestBuilder.Result> result = getPlayer().createConnectionRequest(s.getServer()).connect();
                                    try {
                                        if (!result.get().isSuccessful()) {
                                            getPlayer().sendMessage(VelocityChat.color("&#36a2fbV&#4494fbe&#5385fbl&#6177fco&#6f69fcc&#7d5afci&#8c4cfct&#9a3dfcy&#a82ffcU&#b621fdt&#c512fdi&#d304fdl &7» &fSending you to %server%", player));
                                        } else {
                                            getPlayer().sendMessage(VelocityChat.color("&#36a2fbV&#4494fbe&#5385fbl&#6177fco&#6f69fcc&#7d5afci&#8c4cfct&#9a3dfcy&#a82ffcU&#b621fdt&#c512fdi&#d304fdl &7» &fFailed to send you to %player%'s server", player));
                                        }
                                    } catch (InterruptedException | ExecutionException exception) {
                                        getPlayer().sendMessage(VelocityChat.color("&#36a2fbV&#4494fbe&#5385fbl&#6177fco&#6f69fcc&#7d5afci&#8c4cfct&#9a3dfcy&#a82ffcU&#b621fdt&#c512fdi&#d304fdl &7» &fFailed to send you to %player%'s server", player));
                                    }
                                });
                            }
                            case RIGHT_CLICK -> {
                                getPlayer().getCurrentServer().ifPresent(s -> {
                                    CompletableFuture<ConnectionRequestBuilder.Result> result = player.createConnectionRequest(s.getServer()).connect();
                                    try {
                                        if (!result.get().isSuccessful()) {
                                            getPlayer().sendMessage(VelocityChat.color("&#36a2fbV&#4494fbe&#5385fbl&#6177fco&#6f69fcc&#7d5afci&#8c4cfct&#9a3dfcy&#a82ffcU&#b621fdt&#c512fdi&#d304fdl &7» &fSent %player% to your server", player));
                                        } else {
                                            getPlayer().sendMessage(VelocityChat.color("&#36a2fbV&#4494fbe&#5385fbl&#6177fco&#6f69fcc&#7d5afci&#8c4cfct&#9a3dfcy&#a82ffcU&#b621fdt&#c512fdi&#d304fdl &7» &fFailed to move %player% to your server", player));
                                        }
                                    } catch (InterruptedException | ExecutionException exception) {
                                        getPlayer().sendMessage(VelocityChat.color("&#36a2fbV&#4494fbe&#5385fbl&#6177fco&#6f69fcc&#7d5afci&#8c4cfct&#9a3dfcy&#a82ffcU&#b621fdt&#c512fdi&#d304fdl &7» &fFailed to move %player% to your server", player));
                                    }
                                });
                            }
                            case DROP -> {
                                player.disconnect(VelocityChat.color("&b&lYou were kicked by %player%", getPlayer()));
                            }
                        }
                    }).schedule();
                }
            }
        }
    }

    private boolean canNext() {
        return VelocityUtil.get().getServer().getAllPlayers().size() > (44 * page);
    }

    private boolean canPrev() {
        return page - 1 > 0;
    }
}
