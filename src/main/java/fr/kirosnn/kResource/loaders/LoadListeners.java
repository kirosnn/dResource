package fr.kirosnn.kResource.loaders;

import fr.kirosnn.kResource.KResource;
import fr.kirosnn.kResource.listeners.ResourcePackStatus;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class LoadListeners {

    private final @NotNull KResource plugin;

    public LoadListeners(@NotNull KResource plugin) {
        this.plugin = plugin;
    }

    public void registerListener(@NotNull Listener listener, ResourcePackStatus resourcePackStatus) {
        Bukkit.getPluginManager().registerEvents(listener, plugin);
    }
}
