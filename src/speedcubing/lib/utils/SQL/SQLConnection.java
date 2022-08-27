package speedcubing.lib.utils.SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class SQLConnection {

    public Connection connection;

    public SQLConnection(String url, String user, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet select(String table, String field, String where) {
        return executeQuery("SELECT " + field + " FROM `" + table + "` WHERE " + where);
    }

    public ResultSet select(String table, String field, String where, String orderfield, String order) {
        return executeQuery("SELECT " + field + " FROM `" + table + "` WHERE " + where + " ORDER BY " + orderfield + " " + order);
    }

    public ResultSet selectTop(String table, String field, String orderfield, String sort, String value, int index, int count) {
        return select(table, field, value + " ORDER BY " + orderfield + " " + sort + " LIMIT " + index + ", " + count);
    }

    public ResultSet selectCount(String table, String field, String where) {
        return select(table, "count(" + field + ")", where);
    }

    public void delete(String table, String where) {
        try {
            execute("DELETE  FROM `" + table + "` WHERE " + where);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(String table, String field, String where) {
        try {
            execute("UPDATE `" + table + "` SET " + field + " WHERE " + where);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insert(String table, String field, String value) {
        try {
            execute("INSERT INTO `" + table + "` (" + field + ") VALUES (" + value + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Boolean isStringExist(String table, String where) {
        try {
            return executeQuery("SELECT * FROM `" + table + "` WHERE " + where).next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet executeQuery(String sql) {
        try {
            return connection.prepareStatement(sql).executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void execute(String sql) {
        try {
            connection.prepareStatement(sql).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
