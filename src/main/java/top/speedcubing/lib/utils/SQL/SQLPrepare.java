package top.speedcubing.lib.utils.SQL;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLPrepare {
    private final SQLConnection connection;
    private PreparedStatement statement;
    private boolean prepared = false;
    private final SQLBuilder builder;
    private int easyParamIndex = 1;

    String toSQL() {
        return builder.toSQL();
    }

    SQLPrepare(SQLConnection connection, SQLBuilder builder) {
        this.connection = connection;
        this.builder = builder;
    }

    // sql
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

    //parameters by simple
    public SQLPrepare setString(String data) {
        return setString(easyParamIndex++, data);
    }

    //parameters
    public SQLPrepare setString(int index, String data) {
        try {
            prepare();
            statement.setString(index, data);
        } catch (SQLException e) {
            throw new SQLRuntimeException(builder.toSQL(), e);
        }
        return this;
    }

    //parameters by simple
    public SQLPrepare setInt(int data) {
        return setInt(easyParamIndex++, data);
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

    public SQLPrepare setDouble(double data) {
        return setDouble(easyParamIndex++, data);
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

    public SQLPrepare setFloat(float data) {
        return setFloat(easyParamIndex++, data);
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

    public SQLPrepare setLong(long data) {
        return setLong(easyParamIndex++, data);
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

    public SQLPrepare setByte(byte data) {
        return setByte(easyParamIndex++, data);
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

    public SQLPrepare setBoolean(boolean data) {
        return setBoolean(easyParamIndex++, data);
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
        prepare(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
    }

    void prepare(int type,int concur) throws SQLException {
        if (prepared)
            return;
        statement = connection.getConnection().prepareStatement(builder.toSQL(), type, concur);
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
}