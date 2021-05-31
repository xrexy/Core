package org.shingetsunation.core.database;

public enum Query {
    //TODO don't forget to do the "?" idiot
    CREATE_TABLE("CREATE TABLE IF NOT EXISTS `sides` (\n" +
            "`uuid` VARCHAR(36) not null,\n" +
            "`player_name` VARCHAR(16) not null,\n" +
            "`side` TEXT not null,\n" +
            "primary key (`uuid`));"),
    NEW_PLAYER("INSERT INTO sides (player_name, uuid, side)\n" +
            "SELECT * FROM (SELECT ?, ?, \"&aNeutral\") AS tmp\n" +
            "WHERE NOT EXISTS (SELECT uuid FROM sides WHERE uuid = ?) limit 1;"),
    SELECT_PLAYER("SELECT s.side \n" +
            "FROM sides s\n" +
            "WHERE s.uuid = ? limit 1"),
    SELECT_SIDE("select * \n" +
            "from sides s \n" +
            "where s.side = ?"),
    CHANGE_SIDE("UPDATE sides\n" +
            "SET side = ?\n" +
            "WHERE uuid = ?;");

    private String query;

    Query(String query) {
        setQuery(query);
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
