package top.speedcubing.lib.utils.SQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLConnection {

    public class SQLPrepare extends SQLBuilder {
        PreparedStatement statement;
        boolean prepared;

        public SQLPrepare() {
        }


        public SQLPrepare append(String s) {
            super.append(s);
            return this;
        }

        public SQLPrepare select(String field) {
            super.select(field);
            return this;
        }

        public SQLPrepare delete(String table) {
            super.delete(table);
            return this;
        }

        public SQLPrepare insert(String table, String field) {
            super.insert(table, field);
            return this;
        }

        public SQLPrepare update(String table) {
            super.update(table);
            return this;
        }

        public SQLPrepare from(String table) {
            super.from(table);
            return this;
        }

        public SQLPrepare from(String database, String table) {
            return from("`" + database + "`.`" + table + "`");
        }

        public SQLPrepare where(String where) {
            super.where(where);
            return this;
        }

        public SQLPrepare orderBy(String orders) {
            super.orderBy(orders);
            return this;
        }

        public SQLPrepare limit(int index, int count) {
            super.limit(index, count);
            return this;
        }

        public SQLPrepare set(String set) {
            super.set(set);
            return this;
        }

        public SQLPrepare values(String values) {
            super.values(values);
            return this;
        }

        void prepare() {
            if (!prepared) {
                prepared = true;
                try {
                    statement = connection.prepareStatement(sql);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        public SQLPrepare setString(int index, String data) {
            prepare();
            try {
                statement.setString(index, data);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return this;
        }

        public SQLPrepare setInt(int index, int data) {
            prepare();
            try {
                statement.setInt(index, data);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return this;
        }

        public SQLPrepare setDouble(int index, double data) {
            prepare();
            try {
                statement.setDouble(index, data);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return this;
        }

        public SQLPrepare setFloat(int index, float data) {
            prepare();
            try {
                statement.setFloat(index, data);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return this;
        }

        public SQLPrepare setLong(int index, long data) {
            prepare();
            try {
                statement.setLong(index, data);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return this;
        }


        public SQLPrepare setByte(int index, byte data) {
            prepare();
            try {
                statement.setByte(index, data);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return this;
        }

        public SQLPrepare setBoolean(int index, boolean data) {
            prepare();
            try {
                statement.setBoolean(index, data);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return this;
        }

        public ResultSet executeQuery() {
            try {
                return statement != null ? statement.executeQuery() : connection.prepareStatement(sql).executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }

        public void execute() {
            try {
                if (statement != null)
                    statement.execute();
                else
                    connection.prepareStatement(sql).execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public String[] getStringArray() {
            try {
                ResultSet resultSet = executeQuery();
                List<String> a = new ArrayList<>();
                while (resultSet.next()) {
                    int size = resultSet.getMetaData().getColumnCount();
                    for (int i = 0; i < size; i++) {
                        a.add(resultSet.getString(i + 1));
                    }
                }
                resultSet.getStatement().close();
                return a.toArray(new String[]{});
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        public Integer[] getIntArray() {
            try {
                ResultSet resultSet = executeQuery();
                List<Integer> list = new ArrayList<>();
                while (resultSet.next()) {
                    int size = resultSet.getMetaData().getColumnCount();
                    for (int i = 0; i < size; i++) {
                        list.add(resultSet.getInt(i + 1));
                    }
                }
                resultSet.getStatement().close();
                return list.toArray(new Integer[]{});
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        public Boolean[] getBooleanArray() {
            try {
                ResultSet resultSet = executeQuery();
                List<Boolean> list = new ArrayList<>();
                while (resultSet.next()) {
                    int size = resultSet.getMetaData().getColumnCount();
                    for (int i = 0; i < size; i++) {
                        list.add(resultSet.getBoolean(i + 1));
                    }
                }
                resultSet.getStatement().close();
                return list.toArray(new Boolean[]{});
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        public Blob getBlob() {
            try {
                ResultSet resultSet = executeQuery();
                Blob b = resultSet.next() ? resultSet.getBlob(1) : null;
                resultSet.getStatement().close();
                return b;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        public String getString() {
            try {
                ResultSet resultSet = executeQuery();
                String s = resultSet.next() ? resultSet.getString(1) : null;
                resultSet.getStatement().close();
                return s;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        public Integer getInt() {
            try {
                ResultSet resultSet = executeQuery();
                Integer i = resultSet.next() ? resultSet.getInt(1) : null;
                resultSet.getStatement().close();
                return i;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        public Long getLong() {
            try {
                ResultSet resultSet = executeQuery();
                Long i = resultSet.next() ? resultSet.getLong(1) : null;
                resultSet.getStatement().close();
                return i;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }


        public Boolean getBoolean() {
            try {
                ResultSet resultSet = executeQuery();
                Boolean b = resultSet.next() ? resultSet.getBoolean(1) : null;
                resultSet.getStatement().close();
                return b;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public Connection connection;

    public SQLConnection(String url, String user, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public SQLPrepare select(String field) {
        return new SQLPrepare().select(field);
    }

    public SQLPrepare delete(String table) {
        return new SQLPrepare().delete(table);
    }

    public SQLPrepare insert(String table, String field) {
        return new SQLPrepare().insert(table, field);
    }

    public SQLPrepare update(String table) {
        return new SQLPrepare().update(table);
    }


    public void delete(String table, String where) {
        execute("DELETE FROM `" + table + "` WHERE " + where);
    }

    public void update(String table, String field, String where) {
        execute("UPDATE `" + table + "` SET " + field + " WHERE " + where);
    }

    public void insert(String table, String field, String value) {
        execute("INSERT INTO `" + table + "` (" + field + ") VALUES (" + value + ")");
    }

    public Boolean isStringExist(String table, String where) {
        try {
            return select("*").from(table).where(where).executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet executeQuery(String sql) {
        try {
            return connection.prepareStatement(sql).executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void execute(String... sqls) {
        try {
            if (sqls.length == 1) {
                PreparedStatement statement = connection.prepareStatement(sqls[0]);
                statement.execute();
                statement.close();
            } else {
                Statement statement = connection.createStatement();
                for (String sql : sqls) {
                    statement.addBatch(sql);
                }
                statement.executeBatch();
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
