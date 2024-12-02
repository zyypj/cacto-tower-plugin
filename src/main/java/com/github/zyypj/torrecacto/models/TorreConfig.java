package com.github.zyypj.torrecacto.models;

import com.github.zyypj.torrecacto.utils.CustomStack;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
public class TorreConfig {

    private final ItemStack item;
    private final int layers;
    private final String itemName;
    private final List<String> itemLore;
    private final ConfigurationSection config;

    public TorreConfig(ConfigurationSection section) {
        if (section == null) {
            throw new IllegalArgumentException("Configuração da torre está nula.");
        }

        String materialString = section.getString("item.material");
        if (materialString == null || materialString.isEmpty()) {
            throw new IllegalArgumentException("Material da torre não especificado em " + section.getName());
        }

        this.item = CustomStack.get(materialString);
        this.layers = section.getInt("item.layers", 1);
        this.itemName = section.getString("item.name", "§fTorre");
        this.itemLore = section.getStringList("item.lore");
        this.config = section;
    }
}