package org.shingetsunation.core.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.shingetsunation.core.Core;
import org.shingetsunation.core.database.Database;
import org.shingetsunation.core.database.Query;
import org.shingetsunation.core.utils.CPlayer;
import org.shingetsunation.core.utils.PlayerSide;
import org.shingetsunation.core.utils.TeamManager;

import java.sql.PreparedStatement;

public class PlayerJoin implements Listener {
    @EventHandler
    void join(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        // TODO player is always set to neutral when joins
        // please for god sake don't add more queries.. (maybe modify current one)
        CPlayer cp = CPlayer.getCPlayer(p);
        if (cp != null) {// player is setup
            TeamManager.updateScoreboard(p, cp.getSide());
            Bukkit.getLogger().warning(cp.getSide().substring(2));
            return;
        }

        // player is not setup
        Bukkit.getScheduler().runTaskAsynchronously(Core.getInstance(), () -> Database.execute(conn -> {
            // adding player to database
            PreparedStatement statement = conn.prepareStatement(Query.NEW_PLAYER.getQuery());
            statement.setString(1, p.getName());
            statement.setString(2, p.getUniqueId() + "");
            statement.setString(3, p.getUniqueId() + "");
            statement.execute();
            statement.close();
        }));

        String neutral = PlayerSide.NEUTRAL.get();
        CPlayer.replaceCPlayer(p, neutral);
        TeamManager.updateScoreboard(p, neutral);
    }
}
