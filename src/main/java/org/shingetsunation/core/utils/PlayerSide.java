package org.shingetsunation.core.utils;

import org.bukkit.OfflinePlayer;

import java.util.ArrayList;

public enum PlayerSide {
    NEUTRAL,
    GIBBOUS,
    CRESCENT;

    private final PlayerSide side;

    PlayerSide() {
        side = this;
    }

    private static final ArrayList<OfflinePlayer> neutralList = new ArrayList<>();
    private static final ArrayList<OfflinePlayer> gibbousList = new ArrayList<>();
    private static final ArrayList<OfflinePlayer> crescentList = new ArrayList<>();

    public ArrayList<OfflinePlayer> getList() {
        if (side == CRESCENT)
            return crescentList;
        if (side == GIBBOUS)
            return gibbousList;
        return neutralList;
    }

    public String get() {
        if (side == CRESCENT)
            return "&6Crescent";
        if (side == GIBBOUS)
            return "&3Gibbous";
        return "&2Neutral";
    }

    public static void addPlayer(OfflinePlayer p, String side) {
        switch (side.toLowerCase()) {
            case "&6crescent":
                crescentList.remove(p); // if list doesn't contain player - stays unchanged
                crescentList.add(p);
                break;
            case "&3gibbous":
                gibbousList.remove(p); // if list doesn't contain player - stays unchanged
                gibbousList.add(p);
                break;
            case "&2neutral":
                neutralList.remove(p); // if list doesn't contain player - stays unchanged
                neutralList.add(p);
                break;
        }
    }
}
