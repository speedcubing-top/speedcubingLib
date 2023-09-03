package top.speedcubing.lib.utils.SQL;

public class SQLBuilder {
    private String sql = "";

    public String toSQL() {
        return sql;
    }

    public SQLBuilder append(String s) {
        sql += s;
        return this;
    }

    public SQLBuilder() {
    }

    public SQLBuilder select(String field) {
        return append("SELECT " + field);
    }

    public SQLBuilder delete(String table) {
        return append("DELETE FROM `" + table + "`");
    }

    public SQLBuilder insert(String table, String field) {
        return append("INSERT INTO `" + table + "` (" + field + ")");
    }

    public SQLBuilder update(String table) {
        return append("UPDATE `" + table + "`");
    }

    public SQLBuilder from(String table) {
        return append(" FROM `" + table + "`");
    }

    public SQLBuilder from(String database, String table) {
        return from(database + "`.`" + table);
    }

    public SQLBuilder where(String where) {
        return append(" WHERE " + where);
    }

    public SQLBuilder orderBy(String orders) {
        return append(" ORDER BY " + orders);
    }

    public SQLBuilder limit(int index, int count) {
        return append(" LIMIT " + index + "," + count);
    }

    public SQLBuilder set(String set) {
        return append(" SET " + set);
    }

    public SQLBuilder values(String values) {
        return append(" VALUES (" + values + ")");
    }
}
