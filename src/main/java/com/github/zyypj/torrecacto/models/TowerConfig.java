package com.github.zyypj.torrecacto.models;

import com.github.zyypj.torrecacto.utils.CustomStack;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

@Getter
public class TowerConfig {
    private final ItemStack item;
    private final int layers;
    private final ItemStack firstLayerMaterial;
    private final ItemStack secondLayerMaterial;
    private final ItemStack thirdLayerMaterial;
    private final ItemStack fourthLayerMaterial;

    public TowerConfig(ConfigurationSection section) {
        this.item = CustomStack.get(section.getString("item.material"));
        this.layers = section.getInt("layers", 1);

        this.firstLayerMaterial = CustomStack.get(section.getParent().getString("first-layer"));
        this.secondLayerMaterial = CustomStack.get(section.getParent().getString("second-layer"));
        this.thirdLayerMaterial = CustomStack.get(section.getParent().getString("third-layer"));
        this.fourthLayerMaterial = CustomStack.get(section.getParent().getString("fourth-layer"));
    }
}