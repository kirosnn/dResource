package fr.kirosnn.kResource.managers;

import fr.kirosnn.kResource.utils.ResourcePackUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ResourcePackManager {

    private final @NotNull Plugin plugin;
    private String resourcePackUrl;
    private byte[] resourcePackHash;

    private final Map<String, AutoPackData> autoPacks = new HashMap<>();

    public ResourcePackManager(@NotNull Plugin plugin) {
        this.plugin = plugin;
        loadResourcePackConfiguration();
        loadAutoPackConfiguration();
    }

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
                plugin.getLogger().severe("Échec de la génération du hash du resource pack !");
                return;
            }

            hashBase64 = ResourcePackUtils.encodeToBase64(resourcePackHash);
            plugin.getConfig().set("resourcePack.hash", hashBase64);
            plugin.saveConfig();
            plugin.getLogger().info("Hash du resource pack généré et sauvegardé !");
        } else {
            resourcePackHash = Base64.getDecoder().decode(hashBase64);
            if (resourcePackHash.length != 20) {
                plugin.getLogger().severe("Le hash du resource pack est invalide (doit être 20 octets).");
                resourcePackHash = new byte[0];
            }
        }
    }

    public void loadAutoPackConfiguration() {
        ConfigurationSection autoPackSection = plugin.getConfig().getConfigurationSection("resourcePack.auto-pack");

        if (autoPackSection != null) {
            for (String pluginName : autoPackSection.getKeys(false)) {
                String path = autoPackSection.getString(pluginName + ".path");
                boolean absolutePath = autoPackSection.getBoolean(pluginName + ".absolutePath", false);
                List<String> skipFiles = autoPackSection.getStringList(pluginName + ".skipFiles");

                if (path == null || path.isEmpty()) {
                    plugin.getLogger().warning("Le chemin du pack pour " + pluginName + " est manquant ou vide !");
                    continue;
                }

                File resourcePackFile = absolutePath ? new File(path) : new File(plugin.getDataFolder(), path);

                if (!resourcePackFile.exists()) {
                    plugin.getLogger().warning("Le fichier ou dossier pour " + pluginName + " n'existe pas : " + resourcePackFile.getPath());
                    continue;
                }

                addAutoPack(pluginName, resourcePackFile, skipFiles);
            }
        }
    }

    public void addAutoPack(String name, File file, List<String> skipFiles) {
        autoPacks.put(name, new AutoPackData(file, skipFiles));
    }

    public Map<String, AutoPackData> getAutoPacks() {
        return autoPacks;
    }

    public File mergeResourcePacks() {
        try {
            File mergedPack = new File(plugin.getDataFolder(), "merged_resource_pack.zip");
            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(mergedPack));

            for (AutoPackData autoPack : autoPacks.values()) {
                ZipInputStream zis = new ZipInputStream(new FileInputStream(autoPack.getFile()));
                ZipEntry entry;

                while ((entry = zis.getNextEntry()) != null) {
                    String entryName = entry.getName();

                    if (autoPack.getSkipFiles().stream().anyMatch(entryName::startsWith)) {
                        continue;
                    }

                    zos.putNextEntry(new ZipEntry(entryName));
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = zis.read(buffer)) > 0) {
                        zos.write(buffer, 0, length);
                    }
                    zos.closeEntry();
                }
                zis.close();
            }

            zos.close();
            return mergedPack;
        } catch (Exception e) {
            plugin.getLogger().severe("Erreur lors de la fusion des resource packs : " + e.getMessage());
            return null;
        }
    }

    public String getResourcePackUrl() {
        return resourcePackUrl;
    }

    public byte[] getResourcePackHash() {
        return resourcePackHash;
    }
}
