package com.github.zyypj.torrecacto.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomStack {

    public static ItemStack get(String name) {
        final String[] splitted_name = name.split(":");
        ItemStack stack;
        if(name.length() > 40) {
            stack = CustomSkullStack.getSkull(splitted_name[0]);
            final ItemMeta meta =  stack.getItemMeta();
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
            stack.setItemMeta(meta);
            return stack;
        }
        final Material material = Material.matchMaterial(splitted_name[0]);
        if(material == null) return null;
        stack = new ItemStack(material, 1, Short.parseShort(splitted_name[1]));
        final ItemMeta meta = stack.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
        stack.setItemMeta(meta);
        return stack;
    }
}