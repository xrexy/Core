package org.shingetsunation.core.utils;

import org.bukkit.OfflinePlayer;

import java.util.HashMap;

public class CPlayer {
    private final static HashMap<OfflinePlayer, CPlayer> map = new HashMap<>();
    private String side;
    private OfflinePlayer player;

    public CPlayer(OfflinePlayer p, String side) {
        PlayerSide.addPlayer(p, side);
        setPlayer(p);
        setSide(side);
        map.put(p, this);
    }

    // re-adds (removes and then adds again) from 'list', also from list in enum class PlayerSide
    public static void replaceCPlayer(OfflinePlayer player, String side) {
        map.remove(player);
        map.put(player, new CPlayer(player, side));
    }

    public static CPlayer getCPlayer(OfflinePlayer p) {
        return map.get(p);
    }

    public static HashMap<OfflinePlayer, CPlayer> getMap() {
        return map;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public OfflinePlayer getPlayer() {
        return player;
    }

    public void setPlayer(OfflinePlayer player) {
        this.player = player;
    }
}
