package org.shingetsunation.core.utils;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.shingetsunation.core.Core;

public class Utils {
    private final static Core core = Core.getInstance();
    private final static FileConfiguration config = core.getConfig();

    public static String getString(String path) throws NullPointerException {
        return config.getString(path);
    }

    public static void sendMessage(Player p, String path) {
        p.sendMessage(colorize(getString(path)));
    }

    public static void sendMessageText(Player p, String message) {
        p.sendMessage(colorize(message));
    }

    public static void sendMultilineMessage(Player p, String path) {
        for (String s : config.getStringList(path))
            p.sendMessage(colorize(s));
    }
    
    public static String colorize(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
