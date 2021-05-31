package org.shingetsunation.core.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.shingetsunation.core.utils.Utils;

import java.sql.Connection;
import java.sql.SQLException;

public class Database {
    private static final HikariConfig dbConfig;

    static {
        dbConfig = new HikariConfig();
        dbConfig.setJdbcUrl("jdbc:mysql://" +
                Utils.getString("sql.host") + ":" +
                Utils.getString("sql.port") + "/" +
                Utils.getString("sql.database"));
        dbConfig.setUsername(Utils.getString("sql.username"));
        dbConfig.setPassword(Utils.getString("password"));


        dbConfig.setDriverClassName("com.mysql.jdbc.Driver");
        dbConfig.addDataSourceProperty("cachePrepStmts", "true");
        dbConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        dbConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
    }

    private static final HikariDataSource ds = new HikariDataSource(dbConfig);

    public static <T> void execute(ConnectionCallback<T> callback) {
        try (Connection conn = ds.getConnection()) {
            callback.doInConnection(conn);
        } catch (SQLException e) {
            throw new IllegalStateException("Error during execution.", e);
        }
    }

    public interface ConnectionCallback<T> {
        void doInConnection(Connection conn) throws SQLException;
    }
}