package org.shingetsunation.core.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.shingetsunation.core.utils.CPlayer;

import static org.shingetsunation.core.utils.Utils.colorize;

public class PlayerChat implements Listener {
    @EventHandler
    void chat(AsyncPlayerChatEvent e) {
        String side = CPlayer.getCPlayer(e.getPlayer()).getSide();
        e.setFormat(e.getFormat().replace("{side}", colorize(side)));
    }
}
