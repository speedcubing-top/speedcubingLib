package top.speedcubing.lib.utils.SQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLConnection {
    public class SQLBuilder {
        String sql;

        public SQLBuilder(String sql) {
            this.sql = sql;
        }

        public SQLBuilder from(String table) {
            sql += " FROM `" + table + "`";
            return this;
        }

        public SQLBuilder where(String where) {
            sql += " WHERE " + where;
            return this;
        }

        public SQLBuilder orderBy(String orderfield, String sort) {
            sql += " ORDER BY " + orderfield + " " + sort;
            return this;
        }

        public SQLBuilder limit(int index, int count) {
            sql += " LIMIT " + index + "," + count;
            return this;
        }

        public SQLBuilder append(String s) {
            sql += s;
            return this;
        }


        public ResultSet executeQuery() {
            try {
                return connection.prepareStatement(sql).executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }

        public void execute() {
            try {
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
            } catch (Exception e) {
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
            } catch (Exception e) {
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
            } catch (Exception e) {
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
            } catch (Exception e) {
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
            } catch (Exception e) {
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
            } catch (Exception e) {
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
            } catch (Exception e) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SQLBuilder select(String field) {
        return new SQLBuilder("SELECT " + field);
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
            return select("*").from(table).where(where).executeQuery().next();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
