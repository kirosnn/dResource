package fr.kirosnn.kResource.managers;

import fr.kirosnn.kResource.utils.ResourcePackUtils;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Base64;

public class ResourcePackManager {

    private final @NotNull Plugin plugin;
    private String resourcePackUrl;
    private byte[] resourcePackHash;

    public ResourcePackManager(@NotNull Plugin plugin) {
        this.plugin = plugin;
        loadResourcePackConfiguration();
    }

    /**
     * Charge la configuration et génère le hash si nécessaire.
     */
    private void loadResourcePackConfiguration() {
        this.resourcePackUrl = plugin.getConfig().getString("resourcePack.url");
        String hashBase64 = plugin.getConfig().getString("resourcePack.hash");

        if (resourcePackUrl == null || resourcePackUrl.isEmpty()) {
            plugin.getLogger().severe("URL du resource pack manquante dans la configuration !");
            return;
        }

        if (hashBase64 == null || hashBase64.isEmpty()) {
            plugin.getLogger().info("Hash du resource pack manquant. Génération en cours...");
            resourcePackHash = ResourcePackUtils.generateHashFromUrl(plugin, resourcePackUrl);

            if (resourcePackHash.length == 0) {
                plugin.getLogger().severe("Échec de la génération du hash du resource pack !");
                return;
            }

            hashBase64 = ResourcePackUtils.encodeToBase64(resourcePackHash);
            plugin.getConfig().set("resourcePack.hash", hashBase64);
            plugin.saveConfig();
            plugin.getLogger().info("Hash du resource pack généré et sauvegardé !");
        } else {
            resourcePackHash = Base64.getDecoder().decode(hashBase64);
            if (resourcePackHash.length != 20) {
                plugin.getLogger().severe("Le hash du resource pack est invalide (doit être 20 octets).");
                resourcePackHash = new byte[0];
            }
        }
    }

    /**
     * Retourne l'URL du resource pack.
     *
     * @return L'URL du resource pack.
     */
    public String getResourcePackUrl() {
        return resourcePackUrl;
    }

    /**
     * Retourne le hash du resource pack.
     *
     * @return Le hash en tableau de bytes.
     */
    public byte[] getResourcePackHash() {
        return resourcePackHash;
    }
}
