package com.github.zyypj.torrecacto.models;

import de.tr7zw.nbtapi.NBTItem;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@Getter
public class TorreModel {
    private final String key;
    private final ItemStack item;
    private final int layers;

    public TorreModel(final String key, final ItemStack item, final int layers) {
        this.key = key;
        this.layers = layers;

        final NBTItem nbtItem = new NBTItem(item);
        nbtItem.setString("torre-key", key);
        this.item = nbtItem.getItem();
    }
}