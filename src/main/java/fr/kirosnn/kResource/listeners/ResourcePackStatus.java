package fr.kirosnn.kResource.listeners;

import fr.kirosnn.kResource.KResource;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.jetbrains.annotations.NotNull;

public class ResourcePackStatus implements Listener {

    private final @NotNull KResource plugin;

    public ResourcePackStatus(@NotNull KResource plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onResourcePackStatus(@NotNull PlayerResourcePackStatusEvent event) {
        Player player = event.getPlayer();
        PlayerResourcePackStatusEvent.Status status = event.getStatus();

        switch (status) {
            case SUCCESSFULLY_LOADED:
                Component successMessage = plugin.getLangManager().getFormattedMessage("resourcepack.success");
                plugin.adventure().player(player).sendMessage(successMessage);
                break;

            case DECLINED:
                if (plugin.getConfig().getBoolean("resourcePack.mandatory", false)) {
                    Bukkit.getScheduler().runTask(plugin, () ->
                            player.kickPlayer(plugin.getLangManager().getMessage("resourcepack.kick"))
                    );
                } else {
                    Component declinedMessage = plugin.getLangManager().getFormattedMessage("resourcepack.declined");
                    plugin.adventure().player(player).sendMessage(declinedMessage);
                }
                break;

            case FAILED_DOWNLOAD:
                Component failedMessage = plugin.getLangManager().getFormattedMessage("resourcepack.failed");
                plugin.adventure().player(player).sendMessage(failedMessage);
                break;

            case ACCEPTED:
                plugin.getLogger().info(player.getName() + " a accepté de télécharger le resource pack.");
                break;
        }
    }
}
