package fr.kirosnn.kResource.utils;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Base64;

public class ResourcePackUtils {

    public static byte[] generateHashFromUrl(@NotNull Plugin plugin, @NotNull String url) {
        try {
            plugin.getLogger().info("Téléchargement du resource pack depuis l'URL : " + url);
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.connect();

            if (connection.getResponseCode() != 200) {
                throw new RuntimeException("Échec du téléchargement du resource pack : HTTP " + connection.getResponseCode());
            }

            InputStream inputStream = connection.getInputStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            inputStream.close();

            byte[] fileBytes = outputStream.toByteArray();
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] hash = digest.digest(fileBytes);

            return hash;
        } catch (Exception e) {
            plugin.getLogger().severe("Erreur lors de la génération du hash pour le resource pack : " + e.getMessage());
            return new byte[0];
        }
    }

    public static String encodeToBase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }
}
