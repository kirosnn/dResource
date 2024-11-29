package fr.kirosnn.dResource.listeners;

import fr.kirosnn.dResource.dResource;
import fr.kirosnn.dResource.utils.Text;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class PlayerJoin implements Listener {

    private final @NotNull dResource plugin;

    public PlayerJoin(@NotNull dResource plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(@NotNull PlayerJoinEvent event) {
        Player player = event.getPlayer();

        File mergedPack = plugin.getResourcePackManager().mergeResourcePacks();

        if (mergedPack == null) {
            plugin.getLogger().severe("Impossible de générer le pack combiné !");
            plugin.adventure().player(player).sendMessage(Text.translate(plugin.getLangManager().getMessage("resourcepack.error")));
            return;
        }

        String mergedUrl = "http://monserveur.com/resourcepacks/merged_pack.zip";
        byte[] hash = plugin.getResourcePackManager().getResourcePackHash();

        try {
            player.setResourcePack(mergedUrl, hash, "Pack combiné !", true);
        } catch (Exception e) {
            plugin.getLogger().severe("Erreur lors de l'envoi du resource pack à " + player.getName() + " : " + e.getMessage());
        }
    }
}
