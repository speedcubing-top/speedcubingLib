package top.speedcubing.lib.utils.SQL;


import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLPrepare {
    private final Connection connection;
    private PreparedStatement statement;
    private boolean prepared;
    private final SQLBuilder builder;

    String toSQL() {
        return builder.toSQL();
    }

    SQLPrepare(Connection connection, SQLBuilder builder) {
        this.connection = connection;
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
            throw new SQLRuntimeException(builder.toSQL(), e);
        }
        return this;
    }

    public SQLPrepare setInt(int index, int data) {
        try {
            prepare();
            statement.setInt(index, data);
        } catch (SQLException e) {
            throw new SQLRuntimeException(toSQL(), e);
        }
        return this;
    }

    public SQLPrepare setDouble(int index, double data) {
        try {
            prepare();
            statement.setDouble(index, data);
        } catch (SQLException e) {
            throw new SQLRuntimeException(toSQL(), e);
        }
        return this;
    }

    public SQLPrepare setFloat(int index, float data) {
        try {
            prepare();
            statement.setFloat(index, data);
        } catch (SQLException e) {
            throw new SQLRuntimeException(toSQL(), e);
        }
        return this;
    }

    public SQLPrepare setLong(int index, long data) {
        try {
            prepare();
            statement.setLong(index, data);
        } catch (SQLException e) {
            throw new SQLRuntimeException(toSQL(), e);
        }
        return this;
    }


    public SQLPrepare setByte(int index, byte data) {
        try {
            prepare();
            statement.setByte(index, data);
        } catch (SQLException e) {
            throw new SQLRuntimeException(toSQL(), e);
        }
        return this;
    }

    public SQLPrepare setBoolean(int index, boolean data) {
        try {
            prepare();
            statement.setBoolean(index, data);
        } catch (SQLException e) {
            throw new SQLRuntimeException(toSQL(), e);
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
            int i = statement.executeUpdate();
            statement.close();
            return i;
        } catch (SQLException e) {
            throw new SQLRuntimeException(toSQL(), e);
        }
    }

    public ResultSet executeQuery() {
        try {
            prepare();
            return statement.executeQuery();
        } catch (SQLException e) {
            throw new SQLRuntimeException(toSQL(), e);
        }
    }

    public void execute() {
        try {
            prepare();
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            throw new SQLRuntimeException(toSQL(), e);
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
            throw new SQLRuntimeException(toSQL(), e);
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
            throw new SQLRuntimeException(toSQL(), e);
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
            throw new SQLRuntimeException(toSQL(), e);
        }
    }

    public Blob getBlob() {
        try (ResultSet resultSet = executeQuery()) {
            return resultSet.next() ? resultSet.getBlob(1) : null;
        } catch (SQLException e) {
            throw new SQLRuntimeException(toSQL(), e);
        }
    }

    public byte[] getBytes() {
        try (ResultSet resultSet = executeQuery()) {
            return resultSet.next() ? resultSet.getBytes(1) : null;
        } catch (SQLException e) {
            throw new SQLRuntimeException(toSQL(), e);
        }
    }

    public String getString() {
        try (ResultSet resultSet = executeQuery()) {
            return resultSet.next() ? resultSet.getString(1) : null;
        } catch (SQLException e) {
            throw new SQLRuntimeException(toSQL(), e);
        }
    }

    public Integer getInt() {
        try (ResultSet resultSet = executeQuery()) {
            return resultSet.next() ? resultSet.getInt(1) : null;
        } catch (SQLException e) {
            throw new SQLRuntimeException(toSQL(), e);
        }
    }

    public Long getLong() {
        try (ResultSet resultSet = executeQuery()) {
            return resultSet.next() ? resultSet.getLong(1) : null;
        } catch (SQLException e) {
            throw new SQLRuntimeException(toSQL(), e);
        }
    }


    public Boolean getBoolean() {
        try (ResultSet resultSet = executeQuery()) {
            return resultSet.next() ? resultSet.getBoolean(1) : null;
        } catch (SQLException e) {
            throw new SQLRuntimeException(toSQL(), e);
        }
    }
}