package fr.kirosnn.kResource.files;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class LangManager {

    private final @NotNull Plugin plugin;
    private final @NotNull File langFile;
    private final @NotNull FileConfiguration langConfig;
    private final @NotNull MiniMessage miniMessage;

    private static final String DEFAULT_MESSAGE = "Erreur, message non défini dans le lang.yml";

    public LangManager(@NotNull Plugin plugin) {
        this.plugin = plugin;

        this.langFile = new File(plugin.getDataFolder(), "lang.yml");
        if (!langFile.exists()) {
            plugin.saveResource("lang.yml", false);
        }

        this.langConfig = YamlConfiguration.loadConfiguration(langFile);

        this.miniMessage = MiniMessage.miniMessage();
    }

    public @NotNull String getMessage(@NotNull String key) {
        return langConfig.getString(key, DEFAULT_MESSAGE);
    }

    public @NotNull Component getFormattedMessage(@NotNull String key) {
        String rawMessage = getMessage(key);
        return miniMessage.deserialize(rawMessage);
    }

    public void reload() {
        try {
            langConfig.load(langFile);
        } catch (IOException | org.bukkit.configuration.InvalidConfigurationException e) {
            plugin.getLogger().severe("Erreur lors du rechargement du fichier lang.yml: " + e.getMessage());
        }
    }
}
