package speedcubing.lib.utils.SQL;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SQLUtils {

    public static String[] getStringArray(ResultSet resultSet) {
        try {
            List<String> a = new ArrayList<>();
            while (resultSet.next()) {
                int size = resultSet.getMetaData().getColumnCount();
                for (int i = 0; i < size; i++) {
                    a.add(resultSet.getString(i + 1));
                }
            }
            return a.toArray(new String[]{});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Integer[] getIntArray(ResultSet resultSet) {
        try {
            List<Integer> list = new ArrayList<>();
            while (resultSet.next()) {
                int size = resultSet.getMetaData().getColumnCount();
                for (int i = 0; i < size; i++) {
                    list.add(resultSet.getInt(i + 1));
                }
            }
            return list.toArray(new Integer[]{});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Boolean[] getBooleanArray(ResultSet resultSet) {
        try {
            List<Boolean> list = new ArrayList<>();
            while (resultSet.next()) {
                int size = resultSet.getMetaData().getColumnCount();
                for (int i = 0; i < size; i++) {
                    list.add(resultSet.getBoolean(i + 1));
                }
            }
            return list.toArray(new Boolean[]{});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getString(ResultSet resultSet) {
        try {
            return resultSet.next() ? resultSet.getString(1) : null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getDouble(ResultSet resultSet) {
        try {
            return resultSet.next() ? resultSet.getString(1) : null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Integer getInt(ResultSet resultSet) {
        try {
            return resultSet.next() ? resultSet.getInt(1) : null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Boolean getBoolean(ResultSet resultSet) {
        try {
            return resultSet.next() ? resultSet.getBoolean(1) : null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}