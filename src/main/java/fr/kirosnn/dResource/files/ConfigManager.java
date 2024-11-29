package fr.kirosnn.dResource.files;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class ConfigManager {

    private final @NotNull Plugin plugin;
    private final @NotNull FileConfiguration config;

    public ConfigManager(@NotNull Plugin plugin) {
        this.plugin = plugin;

        plugin.saveDefaultConfig();
        this.config = plugin.getConfig();
    }

    public @NotNull String getString(@NotNull String path, String playerName) {
        String value = config.getString(path, "");
        if (value == null) {
            return "";
        }

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI") && playerName != null) {
            value = PlaceholderAPI.setPlaceholders(Bukkit.getPlayer(playerName), value);
        }
        return value;
    }

    public @NotNull String getRawString(@NotNull String path) {
        return config.getString(path, "");
    }

    public @NotNull byte[] getHashAsBytes(@NotNull String path) {
        String hash = config.getString(path, "");
        if (hash == null || hash.isEmpty()) {
            return new byte[0];
        }

        int length = hash.length();
        byte[] bytes = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            bytes[i / 2] = (byte) ((Character.digit(hash.charAt(i), 16) << 4)
                    + Character.digit(hash.charAt(i + 1), 16));
        }
        return bytes;
    }
}
