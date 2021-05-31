package org.shingetsunation.core;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.shingetsunation.core.commands.CoreSidesCommand;
import org.shingetsunation.core.database.Database;
import org.shingetsunation.core.database.Query;
import org.shingetsunation.core.listeners.PlayerChat;
import org.shingetsunation.core.listeners.PlayerJoin;
import org.shingetsunation.core.utils.CPlayer;
import org.shingetsunation.core.utils.PlayerSide;
import org.shingetsunation.core.utils.TabCompletion;
import org.shingetsunation.core.utils.TeamManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

public final class Core extends JavaPlugin {
    private static Core instance;

    @Override
    public void onEnable() {
        setInstance(this);
        this.saveDefaultConfig();
        this.getConfig().options().copyDefaults(true);

        // loading all players from database for each side
        Bukkit.getScheduler().runTaskAsynchronously(this, () -> Database.execute(conn -> {
            String side;
            OfflinePlayer p;

            PreparedStatement initTable = conn.prepareStatement(Query.CREATE_TABLE.getQuery());
            initTable.execute();
            initTable.close();

            // neutral
            PreparedStatement neutralStatement = conn.prepareStatement(Query.SELECT_SIDE.getQuery());
            neutralStatement.setString(1, PlayerSide.NEUTRAL.get());

            ResultSet neutralSet = neutralStatement.executeQuery();

            while (neutralSet.next()) {
                side = neutralSet.getString("side");
                p = Bukkit.getOfflinePlayer(UUID.fromString(neutralSet.getString("uuid")));

                new CPlayer(p, side);
            }
            neutralStatement.close();
            neutralSet.close();

            // crescent
            PreparedStatement crescentStatement = conn.prepareStatement(Query.SELECT_SIDE.getQuery());
            crescentStatement.setString(1, PlayerSide.CRESCENT.get());

            ResultSet crescentSet = crescentStatement.executeQuery();

            while (crescentSet.next()) {
                side = crescentSet.getString("side");
                p = Bukkit.getOfflinePlayer(UUID.fromString(crescentSet.getString("uuid")));

                new CPlayer(p, side);
            }
            crescentStatement.close();
            crescentSet.close();

            //gibbous
            PreparedStatement gibbousStatement = conn.prepareStatement(Query.SELECT_SIDE.getQuery());
            gibbousStatement.setString(1, PlayerSide.GIBBOUS.get());

            ResultSet gibbousSet = gibbousStatement.executeQuery();

            while (gibbousSet.next()) {
                side = gibbousSet.getString("side");
                p = Bukkit.getOfflinePlayer(UUID.fromString(gibbousSet.getString("uuid")));

                new CPlayer(p, side);
            }
            gibbousStatement.close();
            gibbousSet.close();
        }));
        getCommand("coresides").setExecutor(new CoreSidesCommand());
        getCommand("coresides").setTabCompleter(new TabCompletion());
        TeamManager.init(); //  initializing all teams

        registerEvents();
    }

    @Override
    public void onDisable() {
        // lol disable
    }

    private void registerEvents() {
        PluginManager pluginManager = this.getServer().getPluginManager();

        pluginManager.registerEvents(new PlayerJoin(), this);
        pluginManager.registerEvents(new PlayerChat(), this);
    }

    public static Core getInstance() {
        return instance;
    }

    public void setInstance(Core instance) {
        Core.instance = instance;
    }
}
