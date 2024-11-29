package fr.kirosnn.dResource.loaders;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class LoadMessages {

    private final @NotNull Plugin plugin;

    public LoadMessages(@NotNull Plugin plugin) {
        this.plugin = plugin;
    }

    public void logEnableMessage(boolean hasError, String warningMessage, String errorMessage) {
        String asciiColor = hasError ? ChatColor.RED.toString() : (warningMessage != null ? ChatColor.YELLOW.toString() : ChatColor.GREEN.toString());
        Bukkit.getConsoleSender().sendMessage(asciiColor + ".--------------------------------------------------------------------------.");
        Bukkit.getConsoleSender().sendMessage(asciiColor + "|██╗  ██╗██████╗ ███████╗███████╗ ██████╗ ██╗   ██╗██████╗  ██████╗███████╗|");
        Bukkit.getConsoleSender().sendMessage(asciiColor + "|██║ ██╔╝██╔══██╗██╔════╝██╔════╝██╔═══██╗██║   ██║██╔══██╗██╔════╝██╔════╝|");
        Bukkit.getConsoleSender().sendMessage(asciiColor + "|█████╔╝ ██████╔╝█████╗  ███████╗██║   ██║██║   ██║██████╔╝██║     █████╗  |");
        Bukkit.getConsoleSender().sendMessage(asciiColor + "|██╔═██╗ ██╔══██╗██╔══╝  ╚════██║██║   ██║██║   ██║██╔══██╗██║     ██╔══╝  |");
        Bukkit.getConsoleSender().sendMessage(asciiColor + "|██║  ██╗██║  ██║███████╗███████║╚██████╔╝╚██████╔╝██║  ██║╚██████╗███████╗|");
        Bukkit.getConsoleSender().sendMessage(asciiColor + "|╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝╚══════╝ ╚═════╝  ╚═════╝ ╚═╝  ╚═╝ ╚═════╝╚══════╝|");
        Bukkit.getConsoleSender().sendMessage(asciiColor + "'--------------------------------------------------------------------------'");
        if (warningMessage != null) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[!] Avertissement : " + warningMessage);
        }
        if (hasError) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[!] Erreur critique : " + errorMessage);
        }
    }

    public void logDisableMessage(boolean hasError, String warningMessage, String errorMessage) {
        String asciiColor = hasError ? ChatColor.RED.toString() : (warningMessage != null ? ChatColor.YELLOW.toString() : ChatColor.GREEN.toString());
        Bukkit.getConsoleSender().sendMessage(asciiColor + ".--------------------------------------------------------------------------.");
        Bukkit.getConsoleSender().sendMessage(asciiColor + "|██╗  ██╗██████╗ ███████╗███████╗ ██████╗ ██╗   ██╗██████╗  ██████╗███████╗|");
        Bukkit.getConsoleSender().sendMessage(asciiColor + "|██║ ██╔╝██╔══██╗██╔════╝██╔════╝██╔═══██╗██║   ██║██╔══██╗██╔════╝██╔════╝|");
        Bukkit.getConsoleSender().sendMessage(asciiColor + "|█████╔╝ ██████╔╝█████╗  ███████╗██║   ██║██║   ██║██████╔╝██║     █████╗  |");
        Bukkit.getConsoleSender().sendMessage(asciiColor + "|██╔═██╗ ██╔══██╗██╔══╝  ╚════██║██║   ██║██║   ██║██╔══██╗██║     ██╔══╝  |");
        Bukkit.getConsoleSender().sendMessage(asciiColor + "|██║  ██╗██║  ██║███████╗███████║╚██████╔╝╚██████╔╝██║  ██║╚██████╗███████╗|");
        Bukkit.getConsoleSender().sendMessage(asciiColor + "|╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝╚══════╝ ╚═════╝  ╚═════╝ ╚═╝  ╚═╝ ╚═════╝╚══════╝|");
        Bukkit.getConsoleSender().sendMessage(asciiColor + "'--------------------------------------------------------------------------'");
        if (warningMessage != null) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[!] Avertissement : " + warningMessage);
        }
        if (hasError) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[!] Erreur critique : " + errorMessage);
        }
    }
}
