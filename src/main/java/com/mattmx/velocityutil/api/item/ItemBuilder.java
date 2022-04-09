package com.mattmx.velocityutil.api.item;

import com.mattmx.velocityutil.api.item.Enchantment;
import com.mattmx.velocityutil.api.item.Enchantments;
import dev.simplix.protocolize.api.item.ItemStack;
import dev.simplix.protocolize.data.ItemType;
import net.kyori.adventure.text.Component;
import net.querz.nbt.tag.CompoundTag;
import net.querz.nbt.tag.IntTag;
import net.querz.nbt.tag.ListTag;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ItemBuilder {
    private ItemStack item;
    private List<Enchantment> enchantments = new ArrayList<>();
    private boolean hideEnchants;
    private List<CompoundTag> tags = new ArrayList<>();
    private String skullOwner;

    public ItemBuilder(ItemType material) {
        item = new ItemStack(material);
    }

    public ItemBuilder type(ItemType material) {
        item.itemType(material);
        return this;
    }

    public ItemBuilder amount(int amount) {
        item.amount((byte) amount);
        return this;
    }

    public ItemBuilder name(Component name) {
        item.displayName(name);
        return this;
    }

    public ItemBuilder lore(Component lore) {
        item.addToLore(lore);
        return this;
    }

    public ItemBuilder lore(List<Component> lores) {
        lores.forEach(this::lore);
        return this;
    }

    public ItemBuilder lores(Component[] lores) {
        Arrays.stream(lores).collect(Collectors.toList()).forEach(this::lore);
        return this;
    }

    public ItemBuilder durability(short durability) {
        item.durability(durability);
        return this;
    }

    public ItemBuilder enchantment(Enchantment enchantment) {
        enchantments.add(enchantment);
        return this;
    }

    public ItemBuilder enchantments(Enchantment[] enchantments) {
        this.enchantments.clear();
        this.enchantments.addAll(Arrays.asList(enchantments));
        return this;
    }

    public ItemBuilder enchantments(Enchantments[] enchantment, int level) {
        this.enchantments.clear();
        Arrays.asList(enchantment).stream().forEach(e -> enchantment(e, level));
        return this;
    }

    public ItemBuilder enchantment(Enchantments enchantment, int level) {
        this.enchantments.add(new Enchantment(enchantment, level));
        return this;
    }

    public ItemBuilder clearEnchantment(Enchantments enchantments) {
        List<Enchantment> temp = new ArrayList<>(this.enchantments);
        temp.forEach(en -> {
            if (en.getEnchantment() == enchantments) this.enchantments.remove(en);
        });
        return this;
    }

    public ItemBuilder clearEnchantments() {
        this.enchantments.clear();
        return this;
    }

    public ItemBuilder clearLore(Component c) {
        if (item.lore().contains(c)) {
            item.lore().remove(c);
        }
        return this;
    }

    public ItemBuilder clearLores() {
        item.lore(null, false);
        return this;
    }

    public ItemBuilder clearLores(int i) {
        List<Component> newList = item.lore();
        newList.subList(i, newList.size()).clear();
        item.lore(newList, true);
        return this;
    }

    public ItemBuilder clearLores(int i1, int i2) {
        List<Component> newList = item.lore();
        newList.subList(i1, i2).clear();
        item.lore(newList, true);
        return this;
    }

    public ItemBuilder skullOwner(String name) {
        this.skullOwner = name;
        return this;
    }

    public ItemBuilder hideEnchantments(boolean hide) {
        hideEnchants = hide;
        return this;
    }

    public ItemBuilder addTag(CompoundTag tag) {
        tags.add(tag);
        return this;
    }

    public ItemBuilder setTags(List<CompoundTag> tags) {
        this.tags = tags;
        return this;
    }

    public ItemBuilder clearTags() {
        tags.clear();
        return this;
    }

    public List<Enchantment> getEnchantments() {
        return enchantments;
    }

    public static ItemBuilder of(ItemBuilder builder) {
        ItemStack item = builder.item;
        return new ItemBuilder(item.itemType())
                .lore(item.lore().stream().map(e -> (Component) e).collect(Collectors.toList()))
                .enchantments(builder.enchantments.toArray(new Enchantment[0]))
                .name(item.displayName())
                .amount(item.amount());
    }

    public static ItemBuilder from(ItemStack item) {
        ItemBuilder builder = new ItemBuilder(item.itemType());
        builder.name(item.displayName());
        builder.lore(item.lore());
        if (item.nbtData().get("Enchantments") != null) {
            ListTag<CompoundTag> enchants = (ListTag<CompoundTag>) item.nbtData().getListTag("Enchantments");
            for (CompoundTag tag : enchants) {
                builder.enchantment(Enchantments.valueOf(tag.getString("id").replace("minecraft:", "").toUpperCase()),
                        tag.getInt("lvl"));
            }
        }
        return builder;
    }

    public ItemStack build() {
        CompoundTag nbt = item.nbtData();
        if (!enchantments.isEmpty()) {
            ListTag<CompoundTag> enchantments = (ListTag<CompoundTag>) ListTag.createUnchecked(CompoundTag.class);
            for (int i = 0; i < this.enchantments.size(); i++) {
                Enchantment enchantment = this.enchantments.get(i);
                CompoundTag enchant = new CompoundTag();
                enchant.putString("id", enchantment.getEnchantment().getString());
                enchant.putInt("lvl", enchantment.getLevel());
                enchantments.add(enchant);
            }
            nbt.put("Enchantments", enchantments);
            if (hideEnchants) nbt.put("HideFlags", new IntTag(99));
            if (tags.size() > 0) {
                ListTag<CompoundTag> customTags = (ListTag<CompoundTag>) ListTag.createUnchecked(CompoundTag.class);
                customTags.addAll(tags);
                nbt.put("custom", customTags);
            }
        }
        item.nbtData(nbt);
//        if (item.itemType() == ItemType.PLAYER_HEAD && skullOwner != null) {
//            item
//        }
        return item;
    }

    private String convertEnchants() {
        String s = "";
        for (Enchantment ench : enchantments) {
            s = s + ench.toString();
            if (ench != enchantments.get(enchantments.size() - 1)) {
                s = s + ", ";
            }
        }
        return s;
    }

    private static final class Reflection {

        private static Class<?> getClass(String forName) {
            try {
                return Class.forName(forName);
            } catch (ClassNotFoundException e) {
                return null;
            }
        }

        private static <T> Constructor<T> getDeclaredConstructor(Class<T> clazz, Class<?>... params) {
            try {
                return clazz.getDeclaredConstructor(params);
            } catch (NoSuchMethodException e) {
                return null;
            }
        }

        private static <T> T newInstance(Constructor<T> constructor, Object... params) {
            try {
                return constructor.newInstance(params);
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                return null;
            }
        }

        private static Method getDeclaredMethod(Class<?> clazz, String name, Class<?>... params) {
            try {
                return clazz.getDeclaredMethod(name, params);
            } catch (NoSuchMethodException e) {
                return null;
            }
        }

        private static Object invoke(Method method, Object object, Object... params) {
            method.setAccessible(true);
            try {
                return method.invoke(object, params);
            } catch (InvocationTargetException | IllegalAccessException e) {
                return null;
            }
        }

        private static void setField(String name, Object instance, Object value) {
            Field field = getDeclaredField(instance.getClass(), name);
            field.setAccessible(true);
            try {
                field.set(instance, value);
            } catch (IllegalAccessException ignored) {}
        }

        private static Field getDeclaredField(Class<?> clazz, String name) {
            try {
                return clazz.getDeclaredField(name);
            } catch (NoSuchFieldException e) {
                return null;
            }
        }

    }
}