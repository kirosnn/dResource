package fr.kirosnn.kResource.listeners;

import fr.kirosnn.kResource.KResource;
import fr.kirosnn.kResource.utils.Text;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerJoin implements Listener {

    private final @NotNull KResource plugin;

    public PlayerJoin(@NotNull KResource plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(@NotNull PlayerJoinEvent event) {
        Player player = event.getPlayer();

        String url = plugin.getResourcePackManager().getResourcePackUrl();
        byte[] hash = plugin.getResourcePackManager().getResourcePackHash();
        boolean isMandatory = plugin.getConfig().getBoolean("resourcePack.mandatory", false);

        if (url == null || url.isEmpty() || hash == null || hash.length != 20) {
            plugin.getLogger().severe("Le resource pack ou son hash sont mal configurés !");
            plugin.adventure().player(player).sendMessage(Text.translate(plugin.getLangManager().getMessage("resourcepack.invalid")));
            return;
        }

        String prompt = Text.translateToPrimitive(plugin.getLangManager().getFormattedMessage("resourcepack.prompt"));
        player.setResourcePack(url, hash, prompt, isMandatory);
    }
}
