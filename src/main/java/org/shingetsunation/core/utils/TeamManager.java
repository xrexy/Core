package org.shingetsunation.core.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import static org.shingetsunation.core.utils.Utils.colorize;

public class TeamManager {
    //TODO add more teams :)
    private static final Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
    private static Team gibbous;
    private static Team crescent;
    private static Team neutral;
    private static Team staff;

    public static void init() {
        staff = scoreboard.registerNewTeam("a");
        staff.setPrefix("&f&l");

        crescent = scoreboard.registerNewTeam("b");
        crescent.setPrefix("&6");

        gibbous = scoreboard.registerNewTeam("c");
        gibbous.setPrefix("&3");

        neutral = scoreboard.registerNewTeam("d");
        neutral.setPrefix("&2");
    }

    public static void updateScoreboard(Player p, String side) {
        p.setScoreboard(scoreboard);
        if (p.hasPermission("core.scoreboard.staff")) {
            staff.addEntry(p.getName());
            p.setPlayerListName(colorize(staff.getPrefix() + " " + p.getName()));
            Bukkit.broadcastMessage(staff.getEntries() + "");
            return;
        }
        switch (side.substring(2).toLowerCase()) {
            case "gibbous":
                gibbous.addEntry(p.getName());
                p.setPlayerListName(colorize(gibbous.getPrefix() + " " + p.getName()));
                Bukkit.broadcastMessage(gibbous.getEntries() + "");
                break;
            case "crescent":
                crescent.addEntry(p.getName());
                p.setPlayerListName(colorize(crescent.getPrefix() + " " + p.getName()));
                Bukkit.broadcastMessage(crescent.getEntries() + "");
                break;
            case "neutral":
                neutral.addEntry(p.getName());
                p.setPlayerListName(colorize(neutral.getPrefix() + " " + p.getName()));
                Bukkit.broadcastMessage(neutral.getEntries() + "");
                break;
        }
    }
}
