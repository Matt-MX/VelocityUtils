package com.mattmx.velocityutil.examples.gui;

import com.mattmx.velocityutil.api.general.VelocityChat;
import com.mattmx.velocityutil.api.gui.InventoryBuilder;
import com.mattmx.velocityutil.api.item.Enchantments;
import com.mattmx.velocityutil.api.item.ItemBuilder;
import com.velocitypowered.api.proxy.Player;
import dev.simplix.protocolize.api.inventory.InventoryClick;
import dev.simplix.protocolize.data.ItemType;
import dev.simplix.protocolize.data.inventory.InventoryType;
import net.kyori.adventure.text.Component;

public class ExampleGui extends InventoryBuilder {

    @Override
    public void define(Player p) {
        super.define(p);
        this.setTitle(VelocityChat.color("&#36a2fbV&#4494fbe&#5385fbl&#6177fco&#6f69fcc&#7d5afci&#8c4cfct&#9a3dfcy&#a82ffcU&#b621fdt&#c512fdi&#d304fdl &7» &fExample GUI"));
        this.type(InventoryType.GENERIC_9X6);
        setItem(13, new ItemBuilder(ItemType.DIAMOND).name(VelocityChat.color("&#36a2fbV&#4494fbe&#5385fbl&#6177fco&#6f69fcc&#7d5afci&#8c4cfct&#9a3dfcy&#a82ffcU&#b621fdt&#c512fdi&#d304fdl"))
                .lore(VelocityChat.color("&7You are viewing the &fExample GUI")).enchantment(Enchantments.MENDING, 1).hideEnchantments(true).build());
        for (int i = 45; i < 54; i++) {
            setItem(i, new ItemBuilder(ItemType.WHITE_STAINED_GLASS_PANE).name(Component.text("")).build());
        }
        setItem(53, new ItemBuilder(ItemType.BARRIER).name(VelocityChat.color("&c&lClose")).lore(VelocityChat.color("&7Click to close the GUI")).build());
        setItem(29, new ItemBuilder(ItemType.GREEN_STAINED_GLASS_PANE).name(VelocityChat.color("&b&lButton")).lore(VelocityChat.color("&7Click to do something!")).build());
        setItem(33, new ItemBuilder(ItemType.PLAYER_HEAD).name(VelocityChat.color("&b&lPlayers")).lore(VelocityChat.color("&7Click to view players!")).build());
    }

    @Override
    public void onClick(InventoryClick e) {
        if (e.slot() == 53) {
            this.close();
        } else if (e.slot() == 29) {
            getPlayer().sendMessage(VelocityChat.color("&#36a2fbV&#4494fbe&#5385fbl&#6177fco&#6f69fcc&#7d5afci&#8c4cfct&#9a3dfcy&#a82ffcU&#b621fdt&#c512fdi&#d304fdl &7» &fHello!"));
        } else if (e.slot() == 33) {
            PlayerlistGui playerlist = new PlayerlistGui();
            playerlist.define(getPlayer(), this);
            playerlist.open();
        }
    }
}
