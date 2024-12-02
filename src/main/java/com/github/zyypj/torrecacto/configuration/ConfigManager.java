package com.github.zyypj.torrecacto.configuration;

import com.github.zyypj.torrecacto.Main;
import com.github.zyypj.torrecacto.models.TowerConfig;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class ConfigManager {

    private final Main plugin;
    @Getter
    private final Map<String, TowerConfig> towers;

    public ConfigManager(Main plugin) {
        this.plugin = plugin;

        towers = new HashMap<>();

        ConfigurationSection towerSection = plugin.getConfig().getConfigurationSection("");
        if (towerSection != null) {
            for (String key : towerSection.getKeys(false)) {
                towers.put(key, new TowerConfig(towerSection.getConfigurationSection(key)));
            }
        }
    }

    public String getMessage(String key) {

        if (!key.startsWith("messages.")) {
            return plugin.getConfig().getString("messages." + key).replace("&", "§");
        }

        return plugin.getConfig().getString(key).replace("&", "§");
    }

    public TowerConfig getTowerByLayers(int layers) {
        for (TowerConfig config : towers.values()) {
            if (config.getLayers() == layers) {
                return config;
            }
        }
        return null;
    }

    public FileConfiguration getConfig() {
        return plugin.getConfig();
    }

    public void reloadConfig() {
        plugin.reloadConfig();
    }

    public TowerConfig getTowerConfig(String key) {
        return towers.get(key);
    }
}