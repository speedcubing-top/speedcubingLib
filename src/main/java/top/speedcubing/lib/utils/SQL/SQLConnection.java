package top.speedcubing.lib.utils.SQL;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class SQLConnection {

    public Connection connection;

    public SQLConnection(String url, String user, String password) {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void startTransaction() {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public SQLPrepare select(String field) {
        return new SQLPrepare(new SQLBuilder().select(field));
    }

    public SQLPrepare insert(String table, String field) {
        return new SQLPrepare(new SQLBuilder().insert(table, field));
    }

    public SQLPrepare update(String table) {
        return new SQLPrepare(new SQLBuilder().update(table));
    }

    public SQLPrepare delete(String table) {
        return new SQLPrepare(new SQLBuilder().delete(table));
    }

    public ResultSet select(String table, String field, String where) {
        return executeQuery("SELECT " + field + " FROM " + table + " WHERE " + where);
    }

    public int insert(String table, String field, String value) {
        return executeUpdate("INSERT INTO `" + table + "` (" + field + ") VALUES (" + value + ")");
    }

    public int update(String table, String field, String where) {
        return executeUpdate("UPDATE `" + table + "` SET " + field + " WHERE " + where);
    }

    public int delete(String table, String where) {
        return executeUpdate("DELETE FROM `" + table + "` WHERE " + where);
    }

    public SQLPrepare prepare(String sql) {
        return new SQLPrepare(new SQLBuilder().append(sql));
    }

    public Boolean exist(String table, String where) {
        try {
            ResultSet r = select("*").from(table).where(where).executeQuery();
            boolean exist = r.next();
            r.getStatement().close();
            return exist;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void execute(String sql) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet executeQuery(String sql) {
        try {
            PreparedStatement statement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            return statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int executeUpdate(String sql) {
        try (PreparedStatement statement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public SQLResult executeResult(String sql) {
        return new SQLResult(executeQuery(sql));
    }


    public void execute(String... sqls) {
        execute(Arrays.asList(sqls));
    }

    public void execute(Collection<String> sqls) {
        try (Statement statement = connection.createStatement()) {
            for (String sql : sqls)
                statement.addBatch(sql);
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public class SQLPrepare {
        PreparedStatement statement;
        boolean prepared;
        SQLBuilder builder;

        public SQLPrepare(SQLBuilder builder) {
            this.builder = builder;
        }

        public SQLPrepare append(String s) {
            builder.append(s);
            return this;
        }

        public SQLPrepare select(String field) {
            builder.select(field);
            return this;
        }

        public SQLPrepare delete(String table) {
            builder.delete(table);
            return this;
        }

        public SQLPrepare insert(String table, String field) {
            builder.insert(table, field);
            return this;
        }

        public SQLPrepare update(String table) {
            builder.update(table);
            return this;
        }

        public SQLPrepare from(String table) {
            builder.from(table);
            return this;
        }

        public SQLPrepare from(String database, String table) {
            builder.from(database, table);
            return this;
        }

        public SQLPrepare where(String where) {
            builder.where(where);
            return this;
        }

        public SQLPrepare orderBy(String orders) {
            builder.orderBy(orders);
            return this;
        }

        public SQLPrepare limit(int index, int count) {
            builder.limit(index, count);
            return this;
        }

        public SQLPrepare set(String set) {
            builder.set(set);
            return this;
        }

        public SQLPrepare values(String values) {
            builder.values(values);
            return this;
        }

        public SQLPrepare setString(int index, String data) {
            try {
                prepare();
                statement.setString(index, data);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return this;
        }

        public SQLPrepare setInt(int index, int data) {
            try {
                prepare();
                statement.setInt(index, data);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return this;
        }

        public SQLPrepare setDouble(int index, double data) {
            try {
                prepare();
                statement.setDouble(index, data);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return this;
        }

        public SQLPrepare setFloat(int index, float data) {
            try {
                prepare();
                statement.setFloat(index, data);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return this;
        }

        public SQLPrepare setLong(int index, long data) {
            try {
                prepare();
                statement.setLong(index, data);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return this;
        }


        public SQLPrepare setByte(int index, byte data) {
            try {
                prepare();
                statement.setByte(index, data);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return this;
        }

        public SQLPrepare setBoolean(int index, boolean data) {
            try {
                prepare();
                statement.setBoolean(index, data);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return this;
        }

        void prepare() throws SQLException {
            if (prepared)
                return;
            statement = connection.prepareStatement(builder.toSQL(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            prepared = true;
        }

        public int executeUpdate() {
            try {
                prepare();
                return statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return -1;
            }
        }

        public ResultSet executeQuery() {
            try {
                prepare();
                return statement.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }

        public void execute() {
            try {
                prepare();
                statement.execute();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public SQLResult executeResult() {
            return new SQLResult(executeQuery());
        }

        public String[] getStringArray() {
            try (ResultSet resultSet = executeQuery()) {
                List<String> a = new ArrayList<>();
                while (resultSet.next()) {
                    int size = resultSet.getMetaData().getColumnCount();
                    for (int i = 0; i < size; i++) {
                        a.add(resultSet.getString(i + 1));
                    }
                }
                return a.toArray(new String[]{});
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }

        public Integer[] getIntArray() {
            try (ResultSet resultSet = executeQuery()) {
                List<Integer> list = new ArrayList<>();
                while (resultSet.next()) {
                    int size = resultSet.getMetaData().getColumnCount();
                    for (int i = 0; i < size; i++) {
                        list.add(resultSet.getInt(i + 1));
                    }
                }
                return list.toArray(new Integer[]{});
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }

        public Boolean[] getBooleanArray() {
            try (ResultSet resultSet = executeQuery()) {
                List<Boolean> list = new ArrayList<>();
                while (resultSet.next()) {
                    int size = resultSet.getMetaData().getColumnCount();
                    for (int i = 0; i < size; i++) {
                        list.add(resultSet.getBoolean(i + 1));
                    }
                }
                return list.toArray(new Boolean[]{});
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }

        public Blob getBlob() {
            try (ResultSet resultSet = executeQuery()) {
                return resultSet.next() ? resultSet.getBlob(1) : null;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }

        public byte[] getBytes() {
            try (ResultSet resultSet = executeQuery()) {
                return resultSet.next() ? resultSet.getBytes(1) : null;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }

        public String getString() {
            try (ResultSet resultSet = executeQuery()) {
                return resultSet.next() ? resultSet.getString(1) : null;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }

        public Integer getInt() {
            try (ResultSet resultSet = executeQuery()) {
                return resultSet.next() ? resultSet.getInt(1) : null;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }

        public Long getLong() {
            try (ResultSet resultSet = executeQuery()) {
                return resultSet.next() ? resultSet.getLong(1) : null;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }


        public Boolean getBoolean() {
            try (ResultSet resultSet = executeQuery()) {
                return resultSet.next() ? resultSet.getBoolean(1) : null;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
