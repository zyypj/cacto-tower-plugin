package com.github.zyypj.torrecacto.models;

import com.github.zyypj.torrecacto.utils.CustomStack;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
public class TowerConfig {

    private final ItemStack item;
    private final int layers;
    private final String itemName;
    private final List<String> itemLore;

    public TowerConfig(ConfigurationSection section) {
        this.item = CustomStack.get(section.getString("item.material"));
        this.layers = section.getInt("layers", 1);
        this.itemName = section.getString("item.name", "§fTorre");
        this.itemLore = section.getStringList("item.lore");
    }
}