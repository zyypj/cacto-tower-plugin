package com.github.zyypj.torrecacto.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomStack {

    public static ItemStack get(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("O material fornecido é nulo ou vazio.");
        }

        String[] splittedName = name.split(":");
        if (splittedName.length == 0) {
            throw new IllegalArgumentException("Formato inválido para o material: " + name);
        }

        if (name.length() > 40) {
            return CustomSkullStack.getSkull(splittedName[0]);
        }

        Material material = Material.matchMaterial(splittedName[0]);
        if (material == null) {
            throw new IllegalArgumentException("Material inválido: " + splittedName[0]);
        }

        ItemStack stack = new ItemStack(material);
        if (splittedName.length > 1) {
            try {
                stack.setDurability(Short.parseShort(splittedName[1]));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Formato inválido para o data value: " + splittedName[1], e);
            }
        }

        ItemMeta meta = stack.getItemMeta();
        meta.addItemFlags(org.bukkit.inventory.ItemFlag.values());
        stack.setItemMeta(meta);

        return stack;
    }
}