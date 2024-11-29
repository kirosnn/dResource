package fr.kirosnn.dResource.loaders;

import fr.kirosnn.dResource.dResource;
import fr.kirosnn.dResource.listeners.ResourcePackStatus;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class LoadListeners {

    private final @NotNull dResource plugin;

    public LoadListeners(@NotNull dResource plugin) {
        this.plugin = plugin;
    }

    public void registerListener(@NotNull Listener listener, ResourcePackStatus resourcePackStatus) {
        Bukkit.getPluginManager().registerEvents(listener, plugin);
    }
}
